package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Pdf.PdfJasperService;
import com.example.TrasportiBackend.Recensioni.RecensioneT;
import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.IdsMismatchException;
import com.example.TrasportiBackend.exceptions.NotificaNotFoundException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NotificaService {
    @Autowired
    NotificaRepository notificaRepository;

    @Autowired
    AziendaRepository aziendaRepository;
    @Autowired
    SpedizioneRepository spedizioneRepository;
    @Autowired
    TrasporatoreRepository trasporatoreRepository;
    @Autowired
    PdfJasperService pdfJasperService;

    public Notifica save(NotificaDTO notificaDTO) {
        Notifica notifica = new Notifica();
        Azienda azienda = aziendaRepository.findById(notificaDTO.aziendaId()).orElseThrow(() -> new UserNotFoundException("Azienda con id " + notificaDTO.aziendaId() + " non trovata in db."));
        Spedizione spedizione = spedizioneRepository.findById(notificaDTO.spedizioneId()).orElseThrow(() -> new UserNotFoundException("Spedizione con id " + notificaDTO.spedizioneId() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(notificaDTO.trasportatoreId()).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + notificaDTO.aziendaId() + " non trovata in db."));

        notifica.setAzienda(azienda);
        notifica.setSpedizione(spedizione);
        notifica.setTrasportatore(trasportatore);
        notifica.setStatoNotifica(StatoNotifica.Emessa);
        notifica.setDateTime(LocalDate.now());
        notifica.setInviataDa(notificaDTO.inviataDa());
        notifica.setTesto(notificaDTO.testo());
        return notificaRepository.save(notifica);
    }

    public byte[] accetta(long id, long aziendaId) {
        Notifica notifica = notificaRepository.findById(id).orElseThrow(() -> new NotificaNotFoundException("Notifica con id " + id + " non trovata in db."));
        if (notifica.getAzienda().getId() != aziendaId) {
            throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
        }
        notifica.setStatoNotifica(StatoNotifica.Accettata);
        Notifica notification = new Notifica();
        notification.setStatoNotifica(StatoNotifica.Emessa);
        notification.setTesto(notifica.getAzienda().getNomeAzienda() + " ha accettato la tua richiesta. Clicca qui, scarica il documento e mettiti in contatto" +
                " con l'azienda.");
        notification.setInviataDa("az");
        notification.setAzienda(notifica.getAzienda());
        notification.setTrasportatore(notifica.getTrasportatore());
        notification.setDateTime(LocalDate.now());
        notification.setSpedizione(notifica.getSpedizione());
        List<Notifica> notificas = new ArrayList<>();
        notificas.add(notifica);
        notificas.add(notification);
        try {
            byte[] file = pdfJasperService.richiedi(notifica.getSpedizione().getId(), notifica.getTrasportatore().getId(), "copia");
            notificaRepository.saveAll(notificas);
            return file;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public boolean respingi(long id, long aziendaId) {
        Notifica notifica = notificaRepository.findById(id).orElseThrow(() -> new NotificaNotFoundException("Notifica con id " + id + " non trovata in db."));
        if (notifica.getAzienda().getId() != aziendaId) {
            throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
        }
        notifica.setStatoNotifica(StatoNotifica.Respinta);
        try {
            notificaRepository.save(notifica);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Notifica> leggi(List<Notifica> notificas, long aziendaId) {
        notificas.forEach(
                notifica -> {
                    if (notifica.getAzienda().getId() != aziendaId) {
                        throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
                    } else {
                        notifica.setStatoNotifica(StatoNotifica.Letta);
                    }
                }
        );

        return notificas.stream().map(n -> notificaRepository.save(n)).toList();
    }

    public boolean delete(long id) {
        try {
            notificaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Page<Notifica> findByAzienda_IdAndStatoNotificaAndSender(long id, String statoNotifica, String sender, int page, int size, String orderBy) {
        StatoNotifica statoNotifica1 = StatoNotifica.valueOf(statoNotifica);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Page<Notifica> notificas = notificaRepository.findByAzienda_IdAndStatoNotificaAndInviataDa(id, statoNotifica1, sender, pageable);
        notificas.getContent().stream().sorted(Comparator.comparing(Notifica::getDateTime)).toList();
        return notificas;
    }

    public Page<Notifica> findByTrasportatore_IdAndStatoNotificaAndSender(long id, String statoNotifica, String sender, int page, int size, String orderBy) {
        StatoNotifica statoNotifica1 = StatoNotifica.valueOf(statoNotifica);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        Page<Notifica> notificas = notificaRepository.findByTrasportatore_IdAndStatoNotificaAndInviataDa(id, statoNotifica1, sender, pageable);
        notificas.getContent().stream().sorted(Comparator.comparing(Notifica::getDateTime)).toList();
        return notificas;
    }
public Optional<Notifica> getByTIdAndSpedizioneIdAndInviataDa(long trasportatoreId,long spedizioneId,String inviataDa){
        return notificaRepository.findByTrasportatore_IdAndSpedizione_IdAndInviataDa(trasportatoreId,spedizioneId,inviataDa);
}
}
