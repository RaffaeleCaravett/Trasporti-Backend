package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RecensioneAzDTO(
        String message,
        @NotNull(message = "Az_id necessario")
        long azienda_id,
        @NotNull(message = "T_id necessario")
        long trasportatore_id,
        @NotEmpty(message = "Polo necessario")
        String polo
) {
}
