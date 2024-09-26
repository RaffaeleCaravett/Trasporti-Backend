package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
@Autowired
    AziendaRepository aziendaRepository;

    public Chat save(ChatDTO chatDTO){
        Chat chat = new Chat();
        chat.
    }
}
