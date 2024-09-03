package com.example.TrasportiBackend.payloads.entities;

public record RecensioneTDTO(
        String message,
        long azienda_id,
        long trasportatore_id
) {
}
