package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AdminDTO(
        @NotNull(message = "Cap necessario")
        long cap,
        @NotEmpty(message = "Email necessaria")
        String email,
        @NotEmpty(message = "Città necessaria")
        String citta,
        @NotEmpty(message = "Regione necessaria")
        String regione,
        @NotEmpty(message = "Indirizzo necessario")
        String indirizzo,
        @NotEmpty(message = "Password necessaria")
        String password
) {
}
