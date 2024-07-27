package com.example.TrasportiBackend.Auth;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.exceptions.PasswordMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AziendaLoginSuccess;
import com.example.TrasportiBackend.payloads.entities.Tokens;
import com.example.TrasportiBackend.payloads.entities.TrasportatoreLoginSuccess;
import com.example.TrasportiBackend.payloads.entities.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    PasswordEncoder bcrypt;

    @Autowired
    TrasporatoreRepository trasporatoreRepository;
    @Autowired
    AziendaRepository aziendaRepository;
    public TrasportatoreLoginSuccess trasportatoreLogin(UserLogin userLogin){
        Optional<Trasportatore> optionalTrasportatore =trasporatoreRepository.findByEmail(userLogin.email());
        if(!optionalTrasportatore.isPresent()){
            throw new UserNotFoundException("Trasportatore con email " + userLogin.email() + " non trovato in db");
        }
        if(!bcrypt.matches(userLogin.password(),optionalTrasportatore.get().getPassword())){
            throw new PasswordMismatchException("La password che hai inserito non coincide con la password che abbiamo in database");
        }

        TrasportatoreLoginSuccess trasportatoreLoginSuccess = new TrasportatoreLoginSuccess();
        Tokens tokens = new Tokens();
        tokens.setAccessToken(generateAccessToken());
        tokens.setRefreshToken(generateRefreshToken());
        trasportatoreLoginSuccess.setTokens(tokens);
        Trasportatore trasportatore = optionalTrasportatore.get();
        trasportatoreLoginSuccess.setTrasportatore(trasportatore);
        return trasportatoreLoginSuccess;
    }

    public AziendaLoginSuccess aziendaLogin(UserLogin userLogin){
        Optional<Azienda> optionalAzienda aziendaRepository.findByEmail(userLogin.email());
        if(!optionalAzienda.isPresent()){
            throw new UserNotFoundException("Azienda con email " + userLogin.email() + " non trovato in db");
        }
        if(!bcrypt.matches(userLogin.password(),optionalAzienda.get().getPassword())){
            throw new PasswordMismatchException("La password che hai inserito non coincide con la password che abbiamo in database");
        }

        AziendaLoginSuccess aziendaLoginSuccess = new AziendaLoginSuccess();
        Tokens tokens = new Tokens();
        tokens.setAccessToken(generateAccessToken());
        tokens.setRefreshToken(generateRefreshToken());
        aziendaLoginSuccess.setTokens(tokens);
        Azienda azienda = optionalAzienda.get();
        aziendaLoginSuccess.setAzienda(azienda);
        return aziendaLoginSuccess;
    }

    public String generateAccessToken(){
        return "";
    }
    public String generateRefreshToken(){
        return "";
    }
}
