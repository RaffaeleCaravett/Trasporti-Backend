package com.example.TrasportiBackend.payloads.entities;

import jakarta.validation.constraints.NotEmpty;

public record ChangePasswordRequest(
        @NotEmpty(message = "Email a cui inviare necessaria")
        String to
) {
}
