package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PatchAziendaDTO (
                              String nomeAzienda,
                              String partitaIva,
                              String fatturatoMedio,
                              Long numeroDipendenti,
                              String settore,
                              long cap,
                              String email,
                              String citta,
                              String regione,
                              String indirizzo,
                              String password
){}