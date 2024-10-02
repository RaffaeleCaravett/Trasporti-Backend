package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.chat.Chat;
import com.example.TrasportiBackend.chat.ChatRepository;
import com.example.TrasportiBackend.enums.SenderType;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.TypeMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.TypeMismatchNamingException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    public Messaggi save(MessaggioDTO messaggioDTO) {
        Chat chat = chatRepository.findById(messaggioDTO.chat_id()).orElseThrow(() -> new UserNotFoundException("Chat non trovata in db."));
        Azienda azienda = new Azienda();
        Trasportatore trasportatore = new Trasportatore();
        boolean isAziendaReceiver = false;
        if (SenderType.valueOf(messaggioDTO.senderType()).equals(SenderType.Azienda)) {
            azienda = aziendaRepository.findById(messaggioDTO.sender_id()).orElseThrow(() -> new UserNotFoundException("Azienda con id " + messaggioDTO.sender_id() + " non trovata in db."));
            trasportatore = trasporatoreRepository.findById(messaggioDTO.receiver_id()).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + messaggioDTO.receiver_id() + " non trovato in db."));
        } else if (SenderType.valueOf(messaggioDTO.senderType()).equals(SenderType.Trasportatore)) {
            trasportatore = trasporatoreRepository.findById(messaggioDTO.sender_id()).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + messaggioDTO.sender_id() + " non trovato in db."));
            azienda = aziendaRepository.findById(messaggioDTO.receiver_id()).orElseThrow(() -> new UserNotFoundException("Azienda con id " + messaggioDTO.receiver_id() + " non trovata in db."));
            isAziendaReceiver = true;
        } else {
            throw new BadRequestException("Non stai specificando bene il sender_type");
        }
        Messaggi messaggi = new Messaggi();
        messaggi.setChat(chat);
        messaggi.setTesto(messaggi.getTesto());
        if (isAziendaReceiver) {
            messaggi.setAzienda_as_receiver(azienda);
            messaggi.setTrasportatore_as_sender(trasportatore);
        } else {
            messaggi.setAzienda_as_sender(azienda);
            messaggi.setTrasportatore_as_receiver(trasportatore);
        }
        messaggi.setCreatedAt(LocalDate.now());
        return messaggiRepository.save(messaggi);
    }

    public Messaggi putById(long messaggioId , long sender_id, String sender_type, MessaggioDTO messaggioDTO){
        SenderType senderType = SenderType.valueOf(sender_type);
        Azienda azienda = new Azienda();
        Trasportatore trasportatore = new Trasportatore();

        if(SenderType.Azienda.equals(senderType)){
         azienda= aziendaRepository.findById(sender_id).orElseThrow(()-> new UserNotFoundException("Azienda con id " + sender_id + " non trovata in database"));
        }else if(SenderType.Trasportatore.equals(senderType)){
            trasportatore= trasporatoreRepository.findById(sender_id).orElseThrow(()-> new UserNotFoundException("Trasportatore con id " + sender_id + " non trovato in database"));
        }else{
            throw new TypeMismatchException("Il tipo " + sender_type + " non è identificabile");
        }
        Messaggi messaggi = messaggiRepository.findById(messaggioId).orElseThrow(()-> new BadRequestException("Messaggio non trovato in db"));
        messaggi.setTesto(messaggioDTO.testo());
        return messaggiRepository.save(messaggi);git add 
    }
}
