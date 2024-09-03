package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;

public record RecensioneAzDTO(
        String message,
        long azienda_id,
        long trasportatore_id
) {
}
