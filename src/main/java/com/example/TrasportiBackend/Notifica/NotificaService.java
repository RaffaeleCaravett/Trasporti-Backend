package com.example.TrasportiBackend.Notifica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificaService {
    @Autowired
    NotificaRepository notificaRepository;


    public Notifica save(NotificaDTO notificaDTO){
        
    }
}
