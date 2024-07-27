package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AziendaDTO (
    @NotEmpty(message = "Nome azienda necessario.")
    String nomeAzienda,
    @NotEmpty(message = "Partita Iva necessario.")
    String partitaIva,
    @NotEmpty(message = "Fatturato medio necessario.")
    String fatturatoMedio,
    @NotNull(message = "Numero dipenddenti necessario.")
    Long numeroDipendenti,
    @NotEmpty(message = "Settore necessario.")
    String settore,
    @NotNull(message = "Cap necessario")
    long cap,
    @NotEmpty(message = "Email necessaria")
    String email,
    @NotEmpty(message = "Città necessaria")
    String citta,
    @NotEmpty(message = "Regione necessaria")
    String regione,
    @NotEmpty(message = "Indirizzo necessario")
    String indirizzo
    ){}
