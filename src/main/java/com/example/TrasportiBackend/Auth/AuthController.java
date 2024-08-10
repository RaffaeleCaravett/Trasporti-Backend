package com.example.TrasportiBackend.Auth;

import com.example.TrasportiBackend.Email.EmailService;
import com.example.TrasportiBackend.SecretCode.SecretCode;
import com.example.TrasportiBackend.SecretCode.SecretCodeService;
import com.example.TrasportiBackend.User.*;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.ImpossibleChangePassword;
import com.example.TrasportiBackend.exceptions.PasswordMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.*;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    EmailService emailService;
    @Autowired
    SecretCodeService secretCodeService;
    @Autowired
    UserRepository userRepository;

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

    @PostMapping("/resetPassword/{id}")
    public boolean resetPassword(@RequestBody @Validated ChangePasswordRequest changePasswordRequest, BindingResult bindingResult,@PathVariable long id){
        if(bindingResult.hasErrors()){
            throw new ImpossibleChangePassword(bindingResult.getAllErrors());
        }
        try {
            SecretCode secretCode = secretCodeService.save(new SecretCodeDTO("",id));
            emailService.sendEmail(changePasswordRequest.to(), "Informazioni per resettare la password", "Ciao! Per resettare la tua password inserisci il codice qui sotto " + "\n" +
                     "\n" +
                     "\n" +
                     "\n" +
                    secretCode.getSecretCode() + " e la tua mail nel form che vedi sulla schermata di Trasporti e premi invio."
            );
        return true;
        }catch (Exception e){
            return false;
        }
        }


        @PostMapping("/testSecretCode")
    public boolean testSecretCode(@RequestBody @Validated SecretCodeDTO secretCodeDTO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return secretCodeService.test(secretCodeDTO);
        }
        @PostMapping("/changePassBySecretCode/{newPassword}/{email}")
        public User changePassBySecret(@PathVariable String newPassword,@PathVariable String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            user.get().setPassword(newPassword);
        }else{
            throw new UserNotFoundException("User con email " + email + " non trovato in db");
        }
        return userRepository.save(user.get());
        }
@GetMapping("/settori")
    public List<String> getSettori(){
        return authService.getSettori();
}
@GetMapping("/citta")
    public List<String> getCities(){
        return authService.getCitta();
}
@GetMapping("/regione/{city}")
@ResponseBody
    public String getRegioneByCity(@PathVariable String city, HttpServletResponse response) {
    response.setContentType("text/plain");
        return authService.getRegioneByCity(city);
}
}
