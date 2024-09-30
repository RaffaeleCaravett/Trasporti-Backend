package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.chat.ChatRepository;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessaggiService {
    @Autowired
    MessaggiRepository messaggiRepository;
@Autowired
    ChatRepository chatRepository;
@Autowired
    AziendaRepository aziendaRepository;
@Autowired
    TrasporatoreRepository trasporatoreRepository;
    public Messaggi save(MessaggioDTO messaggioDTO){

    }
}
