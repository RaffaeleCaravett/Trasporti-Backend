package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserLogin(
        @NotEmpty(message = "Email necessaria")
        String email,
        @NotEmpty(message = "Password necessaria")
        String password
) {
}
