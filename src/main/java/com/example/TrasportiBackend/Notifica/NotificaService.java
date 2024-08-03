package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Spedizione.SpedizioneRepository;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Spedizione spedizione = spedizioneRepository.findById(notificaDTO.spedizioneId()).orElseThrow(()->new UserNotFoundException("Spedizione con id " + notificaDTO.spedizioneId() + " non trovata in db."))
        Trasportatore trasportatore = trasporatoreRepository.findById(notificaDTO.trasportatoreId()).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + notificaDTO.aziendaId() + " non trovata in db."))

        notifica.setAzienda(azienda);
        notifica.setSpedizione(spedizione);
        notifica.setTrasportatore(trasportatore);
        notifica.setStatoNotifica(StatoNotifica.Emessa);
        return notificaRepository.save(notifica);
    }
}
