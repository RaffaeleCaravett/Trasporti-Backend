package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private RecensioneT saveRT(RecensioneTDTO recensioneTDTO){

        Azienda azienda = aziendaRepository.findById(recensioneTDTO.azienda_id()).orElseThrow(()->new UserNotFoundException("Azienda con id " + recensioneTDTO.azienda_id() + " non trovata in db."));
        Trasportatore trasportatore = trasporatoreRepository.findById(recensioneTDTO.trasportatore_id()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + recensioneTDTO.trasportatore_id() + " non trovato in db."));

        RecensioneT recensioneT = new RecensioneT();

        recensioneT.setCommento(recensioneTDTO.message());
        recensioneT.setDa();
    }
}
