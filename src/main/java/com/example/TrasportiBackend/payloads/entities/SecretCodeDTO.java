package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SecretCodeDTO(
        @NotEmpty(message = "Secret code necessario")
        String secretCode,
        @NotEmpty(message = "User email necessaria")
        String email
) {
}
