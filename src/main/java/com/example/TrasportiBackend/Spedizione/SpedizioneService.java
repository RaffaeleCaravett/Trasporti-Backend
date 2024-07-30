package com.example.TrasportiBackend.Spedizione;


import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.enums.Stato;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.SpedizioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SpedizioneService {

@Autowired
    SpedizioneRepository spedizioneRepository;
@Autowired
    AnnuncioRepository annuncioRepository;
@Autowired
    AziendaRepository aziendaRepository;

public Spedizione save(SpedizioneDTO spedizioneDTO){
    Spedizione spedizione = new Spedizione();
    spedizione.setA(spedizioneDTO.a());
    spedizione.setDa(spedizioneDTO.da());
    spedizione.setDaSpedire(LocalDate.of(spedizioneDTO.daSpedireAnno(),spedizioneDTO.daSpedireMese(),spedizioneDTO.daSpedireGiorno()));
    spedizione.setNumeroPedane(spedizioneDTO.numeroPedane());
    spedizione.setDescrizioneMerce(spedizioneDTO.descrizione());
    spedizione.setAzienda(aziendaRepository.findById(spedizioneDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id "+ spedizioneDTO.azienda_id() + " non trovato in db.")));
    spedizione.setStato(Stato.Pubblicata);
    return spedizioneRepository.save(spedizione);
}
}
