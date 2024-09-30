package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MessaggioDTO(
        @NotNull(message = "Chat_id necessario")
        long chat_id,
        @NotNull(message = "Sender_id necessario")
        long sender_id,
        @NotNull(message = "Receiver_id necessario")
        long receiver_id,
        @NotEmpty(message = "receiver_type necessario")
        String receiverType,
        @NotEmpty(message = "sender_type necessario")
        String senderType,
        @NotEmpty(message = "Testo vuoto")
        String testo
) {
}
