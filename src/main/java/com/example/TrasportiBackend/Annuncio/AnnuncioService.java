package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        
    }


}
