package com.example.TrasportiBackend.Recensioni;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecensioneService {
    @Autowired
    RecensioneTRepository recensioneTRepository;
    @Autowired
    RecensioneAzRepository recensioneAzRepository;


    private RecensioneT saveRT(){
        throw new RuntimeException();
    }
}
