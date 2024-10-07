package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.payloads.entities.MessaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/azienda/messaggi")
public class MessaggiAzController {

    @Autowired
    private MessaggiService messaggiService;



    @PostMapping("")
    public Messaggi save(@RequestBody @Validated MessaggioDTO messaggioDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            
        }
    }
}
