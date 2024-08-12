package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.IdsMismatchException;
import com.example.TrasportiBackend.exceptions.NotificaNotFoundException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public Notifica save(NotificaDTO notificaDTO){
        Notifica notifica= new Notifica();
        Azienda azienda = aziendaRepository.findById(notificaDTO.aziendaId()).orElseThrow(()->new UserNotFoundException("Azienda con id " + notificaDTO.aziendaId() + " non trovata in db."));
        Spedizione spedizione = spedizioneRepository.findById(notificaDTO.spedizioneId()).orElseThrow(()->new UserNotFoundException("Spedizione con id " + notificaDTO.spedizioneId() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(notificaDTO.trasportatoreId()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + notificaDTO.aziendaId() + " non trovata in db."));

        notifica.setAzienda(azienda);
        notifica.setSpedizione(spedizione);
        notifica.setTrasportatore(trasportatore);
        notifica.setStatoNotifica(StatoNotifica.Emessa);
        notifica.setDateTime(LocalDate.now());
        return notificaRepository.save(notifica);
    }

    public Notifica accetta(long id, long aziendaId){
        Notifica notifica= notificaRepository.findById(id).orElseThrow(()-> new NotificaNotFoundException("Notifica con id " + id + " non trovata in db."));
        if(notifica.getAzienda().getId()!=aziendaId){
            throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
        }
        notifica.setStatoNotifica(StatoNotifica.Accettata);
        return notificaRepository.save(notifica);
    }
    public Notifica respingi(long id, long aziendaId){
        Notifica notifica= notificaRepository.findById(id).orElseThrow(()-> new NotificaNotFoundException("Notifica con id " + id + " non trovata in db."));
        if(notifica.getAzienda().getId()!=aziendaId){
            throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
        }
        notifica.setStatoNotifica(StatoNotifica.Respinta);
        return notificaRepository.save(notifica);
    }
    public Notifica leggi(long id, long aziendaId){
        Notifica notifica= notificaRepository.findById(id).orElseThrow(()-> new NotificaNotFoundException("Notifica con id " + id + " non trovata in db."));
        if(notifica.getAzienda().getId()!=aziendaId){
            throw new IdsMismatchException("L'id dell'azienda notificata è diverso dall'id " + aziendaId);
        }
        notifica.setStatoNotifica(StatoNotifica.Letta);
        return notificaRepository.save(notifica);
    }
    public boolean delete(long id){
        try {
            notificaRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Page<Notifica> findByAzienda_IdAndStatoNotifica(long id,String statoNotifica,int page,int size,String orderBy){
        StatoNotifica statoNotifica1 = StatoNotifica.valueOf(statoNotifica);
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return notificaRepository.findByAzienda_IdAndStatoNotifica(id,statoNotifica1,pageable);
    }
    public Page<Notifica> findByTrasportatore_IdAndStatoNotifica(long id,String statoNotifica,int page,int size,String orderBy){
        StatoNotifica statoNotifica1 = StatoNotifica.valueOf(statoNotifica);
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return notificaRepository.findByAzienda_IdAndStatoNotifica(id,statoNotifica1,pageable);
    }
}
