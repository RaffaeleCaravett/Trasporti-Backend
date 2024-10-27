package com.example.TrasportiBackend.payloads.entities;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.User.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

public record MessaggioDTO(
        @NotNull(message = "Chat_id necessario")
        @JsonProperty("chat_id")
        long chat_id,
        @NotNull(message = "Sender_id necessario")
        @JsonProperty("sender_id")
        long sender_id,
        @NotNull(message = "Receiver_id necessario")
        @JsonProperty("receiver_id")
        long receiver_id,
        @NotEmpty(message = "receiver_type necessario")
        @JsonProperty("receiverType")
        String receiverType,
        @NotEmpty(message = "sender_type necessario")
        @JsonProperty("senderType")
        String senderType,
        @NotEmpty(message = "Testo vuoto")
        @JsonProperty("testo")
        String testo,
        @NotEmpty(message = "Room obbligatoria.")
        @JsonProperty("room")
        String room
) {
@Autowired
        private static UserService userService;

        public Azienda getAziendaAsSender(){
                if(senderType.equals("Azienda")){
                        return userService.getAziendaById(sender_id);
                }
                return null;
        }
        public Trasportatore getTrasportatoreAsSender(){
                if(senderType.equals("Trasportatore")){
                        return userService.getTrasportatoreById(sender_id);
                }
                return null;
        }
        public Azienda getAziendaAsReceiver(){
                if(receiverType.equals("Azienda")){
                        return userService.getAziendaById(receiver_id);
                }
                return null;
        }
        public Trasportatore getTrasportatoreAsReceiver(){
                if(senderType.equals("Trasportatore")){
                        return userService.getTrasportatoreById(receiver_id);
                }
                return null;
        }
}
