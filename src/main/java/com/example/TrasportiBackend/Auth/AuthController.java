package com.example.TrasportiBackend.Auth;

import com.example.TrasportiBackend.Email.EmailService;
import com.example.TrasportiBackend.User.Admin;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.ImpossibleChangePassword;
import com.example.TrasportiBackend.exceptions.PasswordMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.*;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    EmailService emailService;


    @PostMapping("/TLogin")
    public TrasportatoreLoginSuccess TLogin(@RequestBody @Validated UserLogin trasportatoreDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.trasportatoreLogin(trasportatoreDTO);
    }
    @PostMapping("/AzLogin")
    public AziendaLoginSuccess AzLogin(@RequestBody @Validated UserLogin aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.aziendaLogin(aziendaDTO);
    }
    @PostMapping("/ALogin")
    public AdminLoginSuccess ALogin(@RequestBody @Validated UserLogin adminDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.adminLogin(adminDTO);
    }
    @GetMapping("/AccessTToken/{token}")
    public Trasportatore verifyTrasportatoreToken(@PathVariable String token){
        return authService.verifyTrasportatoreAccessToken(token);
    }
    @GetMapping("/AccessAzToken/{token}")
    public Azienda verifyAziendaToken(@PathVariable String token){
        return authService.verifyAziendaAccessToken(token);
    }
    @GetMapping("/AccessAToken/{token}")
    public Admin verifyAdminToken(@PathVariable String token){
        return authService.verifyAdminAccessToken(token);
    }
    @GetMapping("/RefreshTToken/{token}")
    public Tokens verifyTrasportatoreRefreshToken(@PathVariable String token){
        return authService.verifyTrasportatoreRefreshToken(token);
    }
    @GetMapping("/RefreshAzToken/{token}")
    public Tokens verifyAziendaRefreshToken(@PathVariable String token){
        return authService.verifyAziendaRefreshToken(token);
    }
    @GetMapping("/RefreshAToken/{token}")
    public Tokens verifyTAdminRefreshToken(@PathVariable String token){
        return authService.verifyAdminRefreshToken(token);
    }
    @PostMapping("/trasportatore")
    public Trasportatore save(@RequestBody @Validated TrasportatoreDTO trasportatoreDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.registerTrasportatore(trasportatoreDTO);
    }
    @PostMapping("/azienda")
    public Azienda save(@RequestBody @Validated AziendaDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.registerAzienda(aziendaDTO);
    }
    @PostMapping("/admin")
    public Admin save(@RequestBody @Validated AdminDTO aziendaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return authService.registerAdmin(aziendaDTO);
    }

    @PostMapping("/resetPassword")
    public boolean resetPassword(@RequestBody @Validated ChangePasswordRequest changePasswordRequest, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ImpossibleChangePassword(bindingResult.getAllErrors());
        }

        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        try {
            emailService.sendEmail(changePasswordRequest.to(), "Informazioni per resettare la password", "Ciao! Per resettare la tua password inserisci il codice qui sotto " + "\n" +
                     "\n" +
                     "\n" +
                     "\n" +
                    generatedString + " e la tua mail nel form che vedi sulla schermata di Trasporti e premi invio."
            );
        return true;
        }catch (Exception e){
            return false;
        }
        }
}
