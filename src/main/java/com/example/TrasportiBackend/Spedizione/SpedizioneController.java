package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.exceptions.SpedizioneHasErrorsException;
import com.example.TrasportiBackend.payloads.SpedizioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spedizione")
public class SpedizioneController {
    @Autowired
    SpedizioneService spedizioneService;



    @PostMapping("")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione save(@RequestBody @Validated SpedizioneDTO spedizioneDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SpedizioneHasErrorsException(bindingResult.getAllErrors());
        }
    }
}
