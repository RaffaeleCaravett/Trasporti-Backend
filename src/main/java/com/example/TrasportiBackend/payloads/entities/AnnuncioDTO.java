package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AnnuncioDTO(
        @NotNull(message = "Anno necessario")
        int dataPubblicazioneAnno,
        @NotNull(message = "Mese necessario")
        int dataPubblicazioneMese,
        @NotNull(message = "Giorno necessario")
        int dataPubblicazioneGiorno,
        @NotNull(message = "Retribuzione necessaria")
        long retribuzione,
        @NotNull(message = "Azienda id necessario")
        long aziendaId,
        @NotNull(message = "Spedizione id necessario")
        long spedizioneId
) {
}
