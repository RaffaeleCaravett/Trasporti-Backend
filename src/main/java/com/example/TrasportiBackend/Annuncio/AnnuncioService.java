package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.exceptions.NotOwnerException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
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
            annuncioRepository.delete(annuncio);
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

public Page<Annuncio> findByRetribuzione(long retribuzione1, long retribuzione2, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return annuncioRepository.findByRetribuzioneBetween(retribuzione1,retribuzione2,pageable);
}


    public Page<Annuncio> findByData(int anno1,int mese1,int giorno1,int anno2,int mese2,int giorno2, int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        LocalDate date1= LocalDate.of(anno1,mese1,giorno1);
        LocalDate date2 = LocalDate.of(anno2,mese2,giorno2);
        return annuncioRepository.findBydataPubblicazioneBetween(date1,date2,pageable);
    }


}
