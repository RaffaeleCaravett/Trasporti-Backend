package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.chat.Chat;
import com.example.TrasportiBackend.chat.ChatRepository;
import com.example.TrasportiBackend.enums.SenderType;
import com.example.TrasportiBackend.enums.StatoMessaggio;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.TypeMismatchException;
import com.example.TrasportiBackend.exceptions.UnauthorizedException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.TypeMismatchNamingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
        Azienda azienda;
        Trasportatore trasportatore;
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
        messaggi.setTesto(messaggioDTO.testo());
        messaggi.setRoom(messaggioDTO.room());
        if (isAziendaReceiver) {
            messaggi.setAziendaAsReceiver(azienda);
            messaggi.setTrasportatoreAsSender(trasportatore);
        } else {
            messaggi.setAziendaAsSender(azienda);
            messaggi.setTrasportatoreAsReceiver(trasportatore);
        }
        messaggi.setStatoMessaggio(StatoMessaggio.Consegnato);
        messaggi.setCreatedAt(LocalDate.now());
        return messaggiRepository.save(messaggi);
    }

    public Messaggi putById(long messaggioId, long sender_id, String sender_type, MessaggioDTO messaggioDTO) {
        SenderType senderType = SenderType.valueOf(sender_type);

        if (SenderType.Azienda.equals(senderType)) {
            aziendaRepository.findById(sender_id).orElseThrow(() -> new UserNotFoundException("Azienda con id " + sender_id + " non trovata in database"));
        } else if (SenderType.Trasportatore.equals(senderType)) {
            trasporatoreRepository.findById(sender_id).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + sender_id + " non trovato in database"));
        } else {
            throw new TypeMismatchException("Il tipo " + sender_type + " non è identificabile");
        }
        Messaggi messaggi = messaggiRepository.findById(messaggioId).orElseThrow(() -> new BadRequestException("Messaggio non trovato in db"));
        messaggi.setTesto(messaggioDTO.testo());
        return messaggiRepository.save(messaggi);
    }

    public Messaggi deleteById(long message_id, long sender_id, String sender_type) {
        SenderType senderType = SenderType.valueOf(sender_type);
        Messaggi messaggi = messaggiRepository.findById(message_id).orElseThrow(() -> new BadRequestException("Messaggio non trovato in db"));
        if ((SenderType.Azienda.equals(senderType) && messaggi.getAziendaAsSender().getId() == sender_id) || (SenderType.Trasportatore.equals(senderType) && messaggi.getTrasportatoreAsSender().getId() == sender_id)) {
         messaggi.setTesto("Questo messaggio è stato eliminato.");
         messaggi.setCreatedAt(LocalDate.now());
         return messaggiRepository.save(messaggi);
        } else {
            throw new UnauthorizedException("Non si hanno i diritti di eliminare questo messaggio.");
        }

    }
    public List<Messaggi> getByChatId(long chatId){
        return Collections.sort(messaggiRepository.findByChat_Id(chatId),(s1,s2)->{
            return s1.getCreatedAt().isBefore(s2.getCreatedAt());
        });
    }

    @Transactional
    public List<Messaggi> leggi (long chatId, long uId, String senderType){
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new UserNotFoundException("Chat non trovata in db."));

        SenderType senderType1 = SenderType.valueOf(senderType);
        List<Messaggi> messaggis= new ArrayList<>();
        if(senderType1.equals(SenderType.Azienda)&&chat.getTrasportatore().getId()==uId){
            messaggis = messaggiRepository.findByTrasportatoreAsReceiver_IdAndStatoMessaggio(uId,StatoMessaggio.Consegnato);
        }else if(senderType1.equals(SenderType.Trasportatore)&&chat.getAzienda().getId()==uId){
            messaggis = messaggiRepository.findByAziendaAsReceiver_IdAndStatoMessaggio(uId,StatoMessaggio.Consegnato);
        }else {
            throw new BadRequestException("Sembra che qualcosa sia andato storto nell'elaborazione della richiesta");
        }
      return messaggis.stream().peek(m -> m.setStatoMessaggio(StatoMessaggio.Letto)).toList();
    }


    List<Messaggi> findAllByRoom(String room){
        return messaggiRepository.findAllByRoom(room);
    }
}
