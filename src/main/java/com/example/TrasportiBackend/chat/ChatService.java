package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.messaggi.Messaggi;
import com.example.TrasportiBackend.payloads.entities.ChatDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

        if(getByAziendaIdAndTrasportatoreId(chat.getAzienda().getId(),chat.getTrasportatore().getId()).isPresent()){
            return getByAziendaIdAndTrasportatoreId(chat.getAzienda().getId(),chat.getTrasportatore().getId()).get();
        }else{
            return chatRepository.save(chat);
        }
    }


    public List<Chat> getByAziendaId(long id){
        List<Chat> chats = chatRepository.findByAzienda_Id(id);
        return chats.stream().peek(m->m.getMessaggiList().sort(Comparator.comparing(Messaggi::getCreatedAt))).toList();

    }
    public List<Chat> getByTrasportatoreId(long id){
        List<Chat> chats = chatRepository.findByTrasportatore_Id(id);
        return chats.stream().peek(m->m.getMessaggiList().sort(Comparator.comparing(Messaggi::getCreatedAt))).toList();
    }
    public Optional<Chat> getByAziendaIdAndTrasportatoreId(long aziendaId, long trasportatoreId){
        return chatRepository.findByAzienda_IdAndTrasportatore_Id(aziendaId,trasportatoreId);
    }
}
