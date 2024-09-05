package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.Notifica.NotificaRecensione;
import com.example.TrasportiBackend.Notifica.NotificaRecensioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RecensioneService {
    @Autowired
    RecensioneTRepository recensioneTRepository;
    @Autowired
    RecensioneAzRepository recensioneAzRepository;
  @Autowired
    AziendaRepository aziendaRepository;
  @Autowired
    TrasporatoreRepository trasporatoreRepository;
  @Autowired
    NotificaRecensioneRepository notificaRecensioneRepository;

    private RecensioneT saveRT(RecensioneTDTO recensioneTDTO){

        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));

        RecensioneT recensioneT = new RecensioneT();

        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setDa(azienda);
        recensioneT.setA(trasportatore);
        recensioneT.setLocalDate(LocalDate.now());

        NotificaRecensione notificaRecensione = new NotificaRecensione();

        notificaRecensione.setStatoNotifica(StatoNotifica.Emessa);
        notificaRecensione.setDa(azienda);
        notificaRecensione.setA(trasportatore);
        notificaRecensione.setDateTime(recensioneT.getLocalDate());
        notificaRecensione.setTesto("L'azienda " + azienda.getNomeAzienda() + " ti ha lasciato una recensione.");
        notificaRecensioneRepository.save(notificaRecensione);

        return recensioneTRepository.save(recensioneT);
    }
    private RecensioneAz saveRAz(RecensioneAzDTO recensioneTDTO){

        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));

        RecensioneAz recensioneT = new RecensioneAz();

        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setA(azienda);
        recensioneT.setDa(trasportatore);
        recensioneT.setLocalDate(LocalDate.now());

        return recensioneAzRepository.save(recensioneT);
    }

    private Page<RecensioneT> getAllPaginatedT(int page,int size,String orderBy,long tId,String stato){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
        StatoNotifica statoNotifica = StatoNotifica.valueOf(stato);

        return recensioneTRepository.findByA_IdAndStatoNotifica(trasportatore.getId(),statoNotifica,pageable);
    }

    private Page<RecensioneAz> getAllPaginatedAz(int page,int size,String orderBy,long azId,String stato){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        Azienda azienda = aziendaRepository.findById(azId).orElseThrow(()->new UserNotFoundException("Azienda con id " + azId + " non trovata in db."));
        StatoNotifica statoNotifica = StatoNotifica.valueOf(stato);

        return recensioneAzRepository.findByA_IdAndStatoNotifica(azienda.getId(),statoNotifica,pageable);
    }

    private boolean deleteT(long da,long rece_id){
        RecensioneT recensioneT = recensioneTRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));

 if(recensioneT.getDa().getId()==da){
     recensioneTRepository.delete(recensioneT);
 return true;
 }else {
     return false;
 }
    }

    private boolean deleteAz(long da,long rece_id){
        RecensioneT recensioneT = recensioneTRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));

        if(recensioneT.getDa().getId()==da){
            recensioneTRepository.delete(recensioneT);
            return true;
        }else {
            return false;
        }
    }
}
