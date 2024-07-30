package com.example.TrasportiBackend.Spedizione;


import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.enums.Stato;
import com.example.TrasportiBackend.exceptions.IdsMismatchException;
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
    @Autowired
    TrasporatoreRepository trasportatoreRepository;

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
public Spedizione assegna(long id, long trasportatoreId, long aziendaId){
    Azienda azienda = aziendaRepository.findById(aziendaId).orElseThrow(()-> new UserNotFoundException("Azienda con id "+ aziendaId + " non trovata in db."));
    Spedizione spedizione = spedizioneRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Spedizione con id "+ id + " non trovata in db."));
    if(azienda.getId()!=spedizione.getAzienda().getId()){
        throw new IdsMismatchException("Gli id della tua azienda e l'id dell'azienda che ha creato la spedizione non coincidono");
    }
    spedizione.setTrasportatore(trasportatoreRepository.findById(trasportatoreId).orElseThrow(()->new UserNotFoundException("Trasportatore con id "+ trasportatoreId + " non trovato in db.")));
    spedizione.setStato(Stato.Presa_in_carico);

    return spedizioneRepository.save(spedizione);
}
}
