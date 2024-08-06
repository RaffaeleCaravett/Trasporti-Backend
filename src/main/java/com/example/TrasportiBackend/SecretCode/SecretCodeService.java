package com.example.TrasportiBackend.SecretCode;

import com.example.TrasportiBackend.User.UserRepository;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.SecretCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Optional;
import java.util.Random;

@Service
public class SecretCodeService {
    @Autowired
    SecretCodeRepository secretCodeRepository;
    @Autowired
    UserRepository userRepository;
    public SecretCode save(SecretCodeDTO secretCodeDTO){
        Optional<SecretCode> secretCodeOptional = secretCodeRepository.findByUser_Id(secretCodeDTO.user_id());
        byte[] array = new byte[7];
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        SecretCode secretCode= new SecretCode();
        if(secretCodeOptional.isPresent()){
           secretCode = secretCodeOptional.get();
           secretCode.setSecretCode(generatedString);
        }else{
            secretCode.setSecretCode(generatedString);
            secretCode.setUser(userRepository.findById(secretCodeDTO.user_id()).orElseThrow(()->new UserNotFoundException("User con id " + secretCodeDTO.user_id() + " non trovato in db.")));
        }
        return secretCodeRepository.save(secretCode);
    }
}
