package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AnnuncioDTO(
        @NotNull(message = "Retribuzione necessaria")
        long retribuzione,
        @NotNull(message = "Azienda id necessario")
        long aziendaId,
        @NotNull(message = "Spedizione id necessario")
        long spedizioneId
) {
}
