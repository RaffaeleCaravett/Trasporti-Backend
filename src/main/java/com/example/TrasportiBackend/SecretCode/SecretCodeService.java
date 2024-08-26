package com.example.TrasportiBackend.SecretCode;

import com.example.TrasportiBackend.User.User;
import com.example.TrasportiBackend.User.UserRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.SecretCodeDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

@Service
public class SecretCodeService {
    @Autowired
    SecretCodeRepository secretCodeRepository;
    @Autowired
    UserRepository userRepository;
    public String save(SecretCodeDTO secretCodeDTO){
        Optional<SecretCode> secretCodeOptional = secretCodeRepository.findByUser_Email(secretCodeDTO.email());
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        SecretCode secretCode= new SecretCode();
        if(secretCodeOptional.isPresent()){
           secretCode = secretCodeOptional.get();
           secretCode.setSecretCode(saltStr);
        }else{
            secretCode.setSecretCode(saltStr);
            User user = userRepository.findByEmail(secretCodeDTO.email()).orElseThrow(()->new UserNotFoundException("User con email " + secretCodeDTO.email() + " non trovato in db."));
            secretCode.setUser(user);
        }
       secretCodeRepository.save(secretCode);
       return saltStr;
    }
    public boolean test(SecretCodeDTO secretCodeDTO){
        return secretCodeRepository.findBySecretCodeAndUser_Email(secretCodeDTO.secretCode(), secretCodeDTO.email()).isPresent();
    }

    public SecretCode findBySecretCodeAndEmail(String secretCode,String email){
       SecretCode secretCode1= secretCodeRepository.findBySecretCodeAndUser_Email(secretCode,email).orElseThrow(()->new com.example.TrasportiBackend.exceptions.BadRequestException("Non è associato nessun codice segreto con questi parametri nel database."));
    return secretCode1;
    }
}
