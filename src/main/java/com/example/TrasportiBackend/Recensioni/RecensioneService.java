package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.Notifica.NotificaRecensione;
import com.example.TrasportiBackend.Notifica.NotificaRecensioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.PoloRecensione;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.IdsMismatchException;
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

    public RecensioneT saveRT(RecensioneTDTO recensioneTDTO){

        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));
        PoloRecensione poloRecensione= PoloRecensione.valueOf(recensioneTDTO.polo());

        if(recensioneTRepository.findByA_IdAndDa_Id(trasportatore.getId(),azienda.getId()).isPresent()){
            throw new 
        }

        RecensioneT recensioneT = new RecensioneT();

        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setDa(azienda);
        recensioneT.setA(trasportatore);
        recensioneT.setLocalDate(LocalDate.now());
recensioneT.setPoloRecensione(poloRecensione);

        NotificaRecensione notificaRecensione = new NotificaRecensione();

        notificaRecensione.setStatoNotifica(StatoNotifica.Emessa);
        notificaRecensione.setDa(azienda);
        notificaRecensione.setA(trasportatore);
        notificaRecensione.setDateTime(recensioneT.getLocalDate());
        notificaRecensione.setTesto("L'azienda " + azienda.getNomeAzienda() + " ti ha lasciato una recensione.");
        notificaRecensioneRepository.save(notificaRecensione);

        return recensioneTRepository.save(recensioneT);
    }
    public RecensioneAz saveRAz(RecensioneAzDTO recensioneTDTO){

        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));
PoloRecensione poloRecensione= PoloRecensione.valueOf(recensioneTDTO.polo());

        RecensioneAz recensioneT = new RecensioneAz();

        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setA(azienda);
        recensioneT.setDa(trasportatore);
        recensioneT.setLocalDate(LocalDate.now());
recensioneT.setPoloRecensione(poloRecensione);

        return recensioneAzRepository.save(recensioneT);
    }

    public Page<RecensioneT> getAllPaginatedT(int page,int size,String orderBy,long tId,String stato){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
        PoloRecensione statoNotifica = PoloRecensione.valueOf(stato);

        return recensioneTRepository.findByA_IdAndPoloRecensione(trasportatore.getId(),statoNotifica,pageable);
    }

    public Page<RecensioneAz> getAllPaginatedAz(int page,int size,String orderBy,long azId,String stato){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        Azienda azienda = aziendaRepository.findById(azId).orElseThrow(()->new UserNotFoundException("Azienda con id " + azId + " non trovata in db."));
        PoloRecensione statoNotifica = PoloRecensione.valueOf(stato);

        return recensioneAzRepository.findByA_IdAndPoloRecensione(azienda.getId(),statoNotifica,pageable);
    }

    public boolean deleteT(long da,long rece_id){
        RecensioneT recensioneT = recensioneTRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));

 if(recensioneT.getDa().getId()==da){
     recensioneTRepository.delete(recensioneT);
 return true;
 }else {
     throw new IdsMismatchException("Non hai i diritti per cancellare questa recensione");
 }
    }

    public boolean deleteAz(long da,long rece_id){
        RecensioneAz recensioneAz = recensioneAzRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));

        if(recensioneAz.getDa().getId()==da){
            recensioneAzRepository.delete(recensioneAz);
            return true;
        }else {
            throw new IdsMismatchException("Non hai i diritti per cancellare questa recensione");
        }
    }

    public RecensioneT putTbyId(long da,long rece_id,RecensioneTDTO recensioneTDTO){
        Azienda azienda = aziendaRepository.findById(da).orElseThrow(()->new UserNotFoundException("Azienda con id " + da + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));
        PoloRecensione poloRecensione= PoloRecensione.valueOf(recensioneTDTO.polo());

        RecensioneT recensioneT = recensioneTRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));


        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setLocalDate(LocalDate.now());
        recensioneT.setPoloRecensione(poloRecensione);

        NotificaRecensione notificaRecensione = new NotificaRecensione();

        notificaRecensione.setStatoNotifica(StatoNotifica.Emessa);
        notificaRecensione.setDa(azienda);
        notificaRecensione.setA(trasportatore);
        notificaRecensione.setDateTime(recensioneT.getLocalDate());
        notificaRecensione.setTesto("L'azienda " + azienda.getNomeAzienda() + " ha modificato una recensione esistente.");
        notificaRecensioneRepository.save(notificaRecensione);

        return recensioneTRepository.save(recensioneT);
    }
    public RecensioneAz putAzbyId(long da,long rece_id,RecensioneAzDTO recensioneTDTO){
        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(da).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + da + " non trovato in db."));
        PoloRecensione poloRecensione= PoloRecensione.valueOf(recensioneTDTO.polo());

        RecensioneAz recensioneT = recensioneAzRepository.findById(rece_id).orElseThrow(()->new BadRequestException("Recensione con id " + rece_id + " non trovata in db."));


        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setLocalDate(LocalDate.now());
        recensioneT.setPoloRecensione(poloRecensione);

        return recensioneAzRepository.save(recensioneT);
    }
}
