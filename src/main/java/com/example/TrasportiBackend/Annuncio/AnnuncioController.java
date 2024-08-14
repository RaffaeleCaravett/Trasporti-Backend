package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/annuncio")
public class AnnuncioController {

@Autowired
    AnnuncioService annuncioService;

@PostMapping("")
@PreAuthorize("hasAnyAuthority('Admin','Azienda')")
public Annuncio save(@RequestBody @Validated AnnuncioDTO annuncioDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        throw new BadRequestException(bindingResult.getAllErrors());
    }
    return annuncioService.save(annuncioDTO);
}

}
