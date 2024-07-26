package com.example.TrasportiBackend.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TrasportatoreDTO(
        @NotEmpty(message = "Nome necessario")
        String nome,
        @NotEmpty(message = "Cognome necessario")
        String cognome,
        @NotNull(message = "Età necessaria")
        int eta,
        @NotEmpty(message = "Codice Fiscale necessario")
        String codiceFiscale,
        @NotEmpty(message = "Partita Iva necessaria")
        String partitaIva,
        @NotNull(message = "Flotta mezzi necessario")
        long flottaMezzi
) {
}
