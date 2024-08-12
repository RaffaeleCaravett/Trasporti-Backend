package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NotificaDTO(
        @NotNull(message = "Spedizione id necessario")
        long spedizioneId,
        @NotNull(message = "Azienda id necessario")
        long aziendaId,
        @NotNull(message = "Trasportatore id necessario")
        long trasportatoreId,
        String statoNotifica,
        String inviataDa
) {
}
