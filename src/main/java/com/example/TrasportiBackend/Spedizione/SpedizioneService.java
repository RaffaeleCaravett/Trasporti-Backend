package com.example.TrasportiBackend.Spedizione;


import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.Notifica.NotificaRepository;
import com.example.TrasportiBackend.Pdf.PdfJasperService;
import com.example.TrasportiBackend.User.*;
import com.example.TrasportiBackend.enums.Stato;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.IdsMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.SpedizioneDTO;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SpedizioneService {

    @Autowired
    SpedizioneRepository spedizioneRepository;
    @Autowired
    UserService userService;
    @Autowired
    NotificaRepository notificaRepository;
    @Autowired
    PdfJasperService pdfJasperService;
    public Spedizione save(SpedizioneDTO spedizioneDTO) {
        Spedizione spedizione = new Spedizione();
        spedizione.setA(spedizioneDTO.a());
        spedizione.setDa(spedizioneDTO.da());
        spedizione.setDaSpedire(LocalDate.of(spedizioneDTO.daSpedireAnno(), spedizioneDTO.daSpedireMese(), spedizioneDTO.daSpedireGiorno()));
        spedizione.setNumeroPedane(spedizioneDTO.numeroPedane());
        spedizione.setDescrizioneMerce(spedizioneDTO.descrizione());
        spedizione.setAzienda(userService.getAziendaById(spedizioneDTO.azienda_id()));
        spedizione.setStato(Stato.Pubblicata);
        return spedizioneRepository.save(spedizione);
    }

    public Spedizione assegna(long id, long trasportatoreId, long aziendaId) {
        Azienda azienda = userService.getAziendaById(aziendaId);
        Spedizione spedizione = findById(id);
        if (azienda.getId() != spedizione.getAzienda().getId()) {
            throw new IdsMismatchException("Gli id della tua azienda e l'id dell'azienda che ha creato la spedizione non coincidono");
        }
        spedizione.setTrasportatore(userService.getTrasportatoreById(trasportatoreId));
        spedizione.setStato(Stato.Presa_in_carico);

        return spedizioneRepository.save(spedizione);
    }

    public Spedizione completa(long id, long trasportatoreId, long aziendaId) {
        Azienda azienda = userService.getAziendaById(aziendaId);
        Spedizione spedizione = findById(id);
        if (azienda.getId() != spedizione.getAzienda().getId()) {
            throw new IdsMismatchException("Gli id della tua azienda e dell'azienda che ha creato la spedizione non coincidono");
        }
        if (trasportatoreId != spedizione.getTrasportatore().getId()) {
            throw new IdsMismatchException("Gli id del trasportatore e del trasportatore che ha effettuato la spedizione non coincidono");
        }
        spedizione.setStato(Stato.A_termine);
        return spedizioneRepository.save(spedizione);
    }

    public Spedizione stoppa(long id, long trasportatoreId, long aziendaId) {
        Azienda azienda = userService.getAziendaById(aziendaId);
        Spedizione spedizione = findById(id);
        if (azienda.getId() != spedizione.getAzienda().getId()) {
            throw new IdsMismatchException("Gli id della tua azienda e dell'azienda che ha creato la spedizione non coincidono");
        }
        if (trasportatoreId != spedizione.getTrasportatore().getId()) {
            throw new IdsMismatchException("Gli id del trasportatore e del trasportatore che ha effettuato la spedizione non coincidono");
        }
        spedizione.setStato(Stato.Stoppata);
        return spedizioneRepository.save(spedizione);
    }

    public Spedizione guasto(long id, long trasportatoreId, long aziendaId) {
        Azienda azienda = userService.getAziendaById(aziendaId);
        Spedizione spedizione = findById(id);
        if (azienda.getId() != spedizione.getAzienda().getId()) {
            throw new IdsMismatchException("Gli id della tua azienda e dell'azienda che ha creato la spedizione non coincidono");
        }
        if (trasportatoreId != spedizione.getTrasportatore().getId()) {
            throw new IdsMismatchException("Gli id del trasportatore e del trasportatore che ha effettuato la spedizione non coincidono");
        }
        spedizione.setStato(Stato.Guasto);
        return spedizioneRepository.save(spedizione);
    }

    public boolean delete(long spedizioneId, long aziendaId) {
        Spedizione spedizione = spedizioneRepository.findById(spedizioneId).orElseThrow(() -> new UserNotFoundException("Spedizione con id " + spedizioneId + " non trovata in db."));
        if (aziendaId != spedizione.getAzienda().getId()) {
            throw new IdsMismatchException("Gli id della tua azienda e dell'azienda che ha creato la spedizione non coincidono");
        }
        try {
            spedizioneRepository.delete(spedizione);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteByAdmin(long spedizioneId) {
        Spedizione spedizione = findById(spedizioneId);
        try {
            spedizioneRepository.delete(spedizione);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Spedizione putById(long id, SpedizioneDTO spedizioneDTO) {
        Spedizione spedizione = findById(id);

        spedizione.setA(spedizioneDTO.a());
        spedizione.setDa(spedizioneDTO.da());
        spedizione.setDaSpedire(LocalDate.of(spedizioneDTO.daSpedireAnno(), spedizioneDTO.daSpedireMese(), spedizioneDTO.daSpedireGiorno()));
        spedizione.setNumeroPedane(spedizioneDTO.numeroPedane());
        spedizione.setDescrizioneMerce(spedizioneDTO.descrizione());
        spedizione.setAzienda(userService.getAziendaById(spedizioneDTO.azienda_id()));
        spedizione.setStato(spedizione.getStato());
        return spedizioneRepository.save(spedizione);
    }

    public Page<Spedizione> getAllByAziendaId(long id, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return spedizioneRepository.findByAzienda_Id(id, pageable);
    }

    public Page<Spedizione> findByDateBetween(int year1, int month1, int day1, int year2, int month2, int day2, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        LocalDate date1 = LocalDate.of(year1, month1, day1);
        LocalDate date2 = LocalDate.of(year2, month2, day2);
        return spedizioneRepository.findBydaSpedireBetween(date1, date2, pageable);
    }

    public Page<Spedizione> findByDa(String da, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return spedizioneRepository.findByDa(da, pageable);
    }

    public Page<Spedizione> findByA(String a, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return spedizioneRepository.findByA(a, pageable);
    }

    public Page<Spedizione> findByDaAndA(String da, String a, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return spedizioneRepository.findByDaContainingAndAContaining(da, a, pageable);
    }


    public Notifica richiedi(long tId, long spedizioneId) {
        Trasportatore trasportatore = userService.getTrasportatoreById(tId);
        Spedizione spedizione = findById(spedizioneId);

        Notifica notifica = new Notifica();
        notifica.setSpedizione(spedizione);
        notifica.setStatoNotifica(StatoNotifica.Emessa);
        notifica.setTrasportatore(trasportatore);
        notifica.setAzienda(spedizione.getAzienda());
        notifica.setInviataDa("tr");
        notifica.setDateTime(LocalDate.now());
        notifica.setTesto("Un trasportatore chiede di effettuare la spedizione con id " + notifica.getSpedizione().getId() + " in partenza da " + notifica.getSpedizione().getDa() + " e in arrivo a " + notifica.getSpedizione().getA());

        Optional<Notifica> notificas = notificaRepository.findByTesto(notifica.getTesto());
        if(notificaRepository.findByTesto(notifica.getTesto()).isEmpty()){
                return notificaRepository.save(notifica);
            }else{
            throw new BadRequestException("Hai già richiesto questa spedizione.");
        }
    }
    public Spedizione findById(long id){
        return spedizioneRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Spedizione con id " + id + " non trovata in db."));
    }

    public Page<Spedizione> getAllByTrasportatoreId(long tId,String stato,int page,int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));

        if(!stato.isEmpty()){
            Stato stato1= Stato.valueOf(stato);
            return spedizioneRepository.findByTrasportatore_IdAndStato(tId,stato1,pageable);
        }
        return spedizioneRepository.findByTrasportatore_Id(tId,pageable);
    }
    public Page<Spedizione> getAllByAziendaIdAndStato(long azId,String stato,int page,int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));

        Stato stato1 = Stato.valueOf(stato);
        return spedizioneRepository.findByAzienda_IdAndStato(azId,stato1,pageable);
    }
}
