package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ChatDTO (
    @NotNull(message = "Azienda id vuoto")
    long azienda_id,
    @NotNull(message = "Trasportatore id vuoto")
    long trasportatore_id){
}
