package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SecretCodeDTO(
        @NotEmpty(message = "Secret code necessario")
        String secretCode,
        @NotNull(message = "User id necessario")
        long user_id
) {
}
