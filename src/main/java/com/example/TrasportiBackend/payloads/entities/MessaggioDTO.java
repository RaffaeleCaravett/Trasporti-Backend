package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record MessaggioDTO(
        @NotNull(message = "Chat_id necessario")
        long chat_id,
        @NotNull(message = "Sender_id necessario")
        long sender_id,
        @NotNull(message = "Receiver_id necessario")
        long receiver_id
) {
}
