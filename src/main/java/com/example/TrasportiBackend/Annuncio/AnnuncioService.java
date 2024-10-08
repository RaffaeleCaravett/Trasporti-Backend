package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.Spedizione.SpedizioneService;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.enums.Stato;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.NotOwnerException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AnnuncioService {

    @Autowired
    AnnuncioRepository annuncioRepository;

    @Autowired
    AziendaRepository aziendaRepository;
    @Autowired
    SpedizioneRepository spedizioneRepository;

    @Autowired
    SpedizioneService spedizioneService;
    @PersistenceContext
    private EntityManager entityManager;

    public Annuncio save (AnnuncioDTO annuncioDTO){
        Annuncio annuncio = new Annuncio();
        Azienda azienda = aziendaRepository.findById(annuncioDTO.aziendaId()).orElseThrow(()->new UserNotFoundException("Azienda con id " + annuncioDTO.aziendaId() + " non trovato in db"));
        Spedizione spedizione = spedizioneRepository.findById(annuncioDTO.spedizioneId()).orElseThrow(()->new UserNotFoundException("Spedizione con id " + annuncioDTO.spedizioneId() + " non trovato in db." ));
        annuncio.setAzienda(azienda);
        annuncio.setSpedizione(spedizione);
        annuncio.setDataPubblicazione(LocalDate.now());
        annuncio.setRetribuzione(annuncioDTO.retribuzione());
        return annuncioRepository.save(annuncio);
    }
    public boolean deleteByAzienda(long aziendaId , long annuncioId){
        Annuncio annuncio = annuncioRepository.findById(annuncioId).orElseThrow(()-> new UserNotFoundException("Annuncio con id " + annuncioId + " non trovato in db"));
        if(annuncio.getAzienda().getId()!=aziendaId){
            throw new NotOwnerException("L'annuncio non è stato cancellato. Sembra che tu non sia il proprietario dell'annuncio.");
        }
        try {
                Spedizione spedizione = annuncio.getSpedizione();
                annuncioRepository.delete(annuncio);
                deleteSpedizione(spedizione);
                return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean deleteByAdmin(long annuncioId){
        Annuncio annuncio = annuncioRepository.findById(annuncioId).orElseThrow(()-> new UserNotFoundException("Annuncio con id " + annuncioId + " non trovato in db"));
        try {
            annuncioRepository.delete(annuncio);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public Annuncio putById (AnnuncioDTO annuncioDTO,long aziendaId, long annuncioId ) {
        Annuncio annuncio = annuncioRepository.findById(annuncioId).orElseThrow(() -> new UserNotFoundException("Annuncio con id " + annuncioId + " non trovato in db"));
        Azienda azienda = aziendaRepository.findById(annuncioDTO.aziendaId()).orElseThrow(() -> new UserNotFoundException("Azienda con id " + annuncioDTO.aziendaId() + " non trovato in db"));
        if (aziendaId != 0) {
            if (azienda.getId() != aziendaId) {
                throw new NotOwnerException("L'annuncio non può essere modificato. Sembra che tu non sia il proprietario dell'annuncio.");
            }
        }
        if (aziendaId == 0 || azienda.getId() == aziendaId) {
            Spedizione spedizione = spedizioneRepository.findById(annuncioDTO.spedizioneId()).orElseThrow(() -> new UserNotFoundException("Spedizione con id " + annuncioDTO.spedizioneId() + " non trovato in db."));
            annuncio.setAzienda(azienda);
            annuncio.setSpedizione(spedizione);
            annuncio.setDataPubblicazione(annuncio.getDataPubblicazione());
            annuncio.setRetribuzione(annuncioDTO.retribuzione());
            return annuncioRepository.save(annuncio);
        }else {
            throw new NotOwnerException("L'annuncio non può essere modificato. Sembra che tu non sia il proprietario dell'annuncio e neanche un Admin.");
        }
    }
public Page<Annuncio> getByAziendaId(long aziendaId,int page,int size,String orderBy){
    Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

    return annuncioRepository.findByAzienda_Id(aziendaId,pageable);
}
    public Page<Annuncio> getByAziendaIdAndStato(long aziendaId,String stato,int page,int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        Stato state = Stato.valueOf(stato);
        return annuncioRepository.findByAzienda_IdAndSpedizione_Stato(aziendaId,state,pageable);
    }
    public long getByAziendaIdAndStatoPubblicata(long aziendaId,String stato,int page,int size,String orderBy){
        Stato state = Stato.valueOf(stato);
       return annuncioRepository.findByAzienda_IdAndSpedizione_Stato(aziendaId,state).size();
    }
public Page<Annuncio> findByRetribuzione(int retribuzione1, int retribuzione2,long azId, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return annuncioRepository.findByRetribuzioneBetweenAndAzienda_Id(retribuzione1, retribuzione2, azId,pageable);
}


    public Page<Annuncio> findByData(int anno1,int mese1,int giorno1,int anno2,int mese2,int giorno2,long azId, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        LocalDate date1= LocalDate.of(anno1,mese1,giorno1);
        LocalDate date2 = LocalDate.of(anno2,mese2,giorno2);
        return annuncioRepository.findBydataPubblicazioneBetweenAndAzienda_Id(date1,date2,azId,pageable);
    }
    public Page<Annuncio> findByRetribuzione(int retribuzione1, int retribuzione2, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return annuncioRepository.findByRetribuzioneBetween(retribuzione1, retribuzione2,pageable);
    }


    public Page<Annuncio> findByData(int anno1,int mese1,int giorno1,int anno2,int mese2,int giorno2, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        LocalDate date1= LocalDate.of(anno1,mese1,giorno1);
        LocalDate date2 = LocalDate.of(anno2,mese2,giorno2);
        return annuncioRepository.findBydataPubblicazioneBetween(date1,date2,pageable);
    }

    public boolean deleteSpedizione(Spedizione spedizione) {
return spedizioneService.delete(spedizione.getId(),spedizione.getAzienda().getId());
    }
public Page<Annuncio> findByDaSpedire(String da, String a,int page,int size, String orderBy,String direction){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
        LocalDate da1 = LocalDate.parse(da);
        LocalDate a1 =LocalDate.parse(a);
        return annuncioRepository.findBySpedizione_DaSpedireBetween(da1,a1,pageable);
}
    public Page<Annuncio> getByNomeAzienda(String nomeAzienda,int page,int size, String orderBy,String direction){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
        return annuncioRepository.findByAzienda_NomeAzienda(nomeAzienda,pageable);

    }
        public Page<Annuncio>getByNumeroPedane(long numeroPedane,int page,int size, String orderBy,String direction){
            Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
return annuncioRepository.findBySpedizione_NumeroPedane(numeroPedane,pageable);
        }

        public Page<Annuncio> findByDa(String da,int page,int size, String orderBy,String direction){
                    Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
return annuncioRepository.findBySpedizione_DaContainingIgnoreCase(da,pageable);
        }
        public Page<Annuncio> findByA(String a,int page,int size, String orderBy,String direction){
                    Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
return annuncioRepository.findBySpedizione_AContainingIgnoreCase(a,pageable);
        }
public Page<Annuncio> findByDaAndA(String da,String a,int page,int size, String orderBy,String direction){
                    Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
return annuncioRepository.findBySpedizione_DaContainingIgnoreCaseAndSpedizione_AContainingIgnoreCase(da,a,pageable);
}
public Page<Annuncio> getAllPaginated(int page, int size, String orderBy, String direction){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(direction),orderBy));
        return annuncioRepository.findAll(pageable);
}
}
