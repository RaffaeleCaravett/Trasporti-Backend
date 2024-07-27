package com.example.TrasportiBackend.payloads.entities;

import com.example.TrasportiBackend.User.Trasportatore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrasportatoreLoginSuccess {

    private Tokens tokens;
    private Trasportatore trasportatore;
}
