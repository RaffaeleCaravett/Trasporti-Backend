package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
        annuncio.setDataPubblicazione(LocalDate.of(annuncioDTO.dataPubblicazioneAnno(),annuncioDTO.dataPubblicazioneMese(),annuncioDTO.dataPubblicazioneGiorno()));
        annuncio.setRetribuzione(annuncioDTO.retribuzione());
        return annuncioRepository.save(annuncio);
    }
    public boolean deleteByAzienda(long aziendaId , long annuncioId){
        Annuncio annuncio = annuncioRepository.findById(annuncioId).orElseThrow(()-> new UserNotFoundException("Annuncio con id " + annuncioId + " non trovato in db"));
        if(annuncio.getAzienda().getId()!=aziendaId){
            throw new NotOwnerException()
        }
    }

}
