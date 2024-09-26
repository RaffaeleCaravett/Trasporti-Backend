package com.example.TrasportiBackend.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;


    public Chat save(ChatDTO chatDTO){
        Chat chat = new Chat();
    }
}
