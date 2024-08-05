package com.example.TrasportiBackend.payloads.entities;

import com.example.TrasportiBackend.User.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginSuccess {

    private Admin admin;
    private Tokens tokens;
}
