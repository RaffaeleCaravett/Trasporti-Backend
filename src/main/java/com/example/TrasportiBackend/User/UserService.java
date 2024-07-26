package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TrasporatoreRepository trasporatoreRepository;
    @Autowired
    AziendaRepository aziendaRepository;

    public Trasportatore getTrasportatoreById(long id){
        return trasporatoreRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Trasportatore con id " + id + " non trovato in db"));
    }
    public Azienda getAziendaById(long id){
        return aziendaRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Azienda con id " + id + " non trovato in db"));
    }

    public Trasportatore putById(long id ,Trasportatore){
        Trasportatore trasportatore = trasporatoreRepository.findById(id).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + id + " non trovato in db"));



    }
}
