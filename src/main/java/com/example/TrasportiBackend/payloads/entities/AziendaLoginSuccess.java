package com.example.TrasportiBackend.payloads.entities;

import com.example.TrasportiBackend.User.Azienda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AziendaLoginSuccess {
    private Tokens tokens;
    private Azienda azienda;
}
