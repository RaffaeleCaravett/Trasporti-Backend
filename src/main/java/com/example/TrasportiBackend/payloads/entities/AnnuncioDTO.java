package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AnnuncioDTO(
        @NotEmpty(message = "Data pubblicazione necessaria")
        String dataPubblicazione,
        @NotNull(message = "Azienda id necessario")
        long aziendaId,
        @NotNull(message = "Spedizione id necessario")
        long spedizioneId
) {
}
