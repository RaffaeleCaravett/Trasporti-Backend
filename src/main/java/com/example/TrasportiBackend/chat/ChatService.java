package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
@Autowired
    AziendaRepository aziendaRepository;
@Autowired
    TrasporatoreRepository trasporatoreRepository;
    public Chat save(ChatDTO chatDTO){
        Chat chat = new Chat();
        chat.setAzienda(aziendaRepository.findById(chatDTO.azienda_id()).orElseThrow(()-> new UserNotFoundException("Azienda con id "+ chatDTO.azienda_id() + " non trovata in db.")));
        chat.setTrasportatore(trasporatoreRepository.findById(chatDTO.trasportatore_id()).orElseThrow(()-> new UserNotFoundException("Trasportatore con id "+ chatDTO.trasportatore_id() + " non trovato in db.")));

        return chatRepository.save(chat);
    }


    public List<Chat> getByAziendaId(long id){
        return chatRepository.findByAzienda_Id(id);
    }
    public List<Chat> getByTrasportatoreId(long id){
        return chatRepository.findByTrasportatore_Id(id);
    }
}
