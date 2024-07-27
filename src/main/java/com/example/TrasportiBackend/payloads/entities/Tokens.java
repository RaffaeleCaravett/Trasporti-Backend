package com.example.TrasportiBackend.payloads.entities;

import com.example.TrasportiBackend.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tokens {
private String accessToken;
private String refreshToken;
}
