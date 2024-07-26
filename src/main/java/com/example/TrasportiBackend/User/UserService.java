package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;


    public User getTrasportatoreById(long id){
        return userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User con id " + id + " non trovato in db"));
    }
}
