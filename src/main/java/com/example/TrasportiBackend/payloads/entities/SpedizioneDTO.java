package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SpedizioneDTO(
    @NotEmpty(message = "Da necessario")
    String da,
    @NotEmpty(message = "A necessario")
    String a,
    @NotNull(message = "Anno da spedire necessaria")
    int daSpedireAnno,
    @NotNull(message = "Mese da spedire necessaria")
    int daSpedireMese,
    @NotNull(message = "Giorno da spedire necessaria")
    int daSpedireGiorno,
    @NotEmpty(message = "Descrizione necessario")
    String descrizione,
    @NotNull(message = "Numero Pedane necessario")
    long numeroPedane,
    @NotNull(message = "Azienda_id necessario")
    long azienda_id
) {
}
