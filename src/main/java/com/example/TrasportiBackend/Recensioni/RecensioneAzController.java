package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/azienda/recensione")
public class RecensioneAzController {

@Autowired
    RecensioneService recensioneService;


@PostMapping("")
public RecensioneAz save(@RequestBody @Validated RecensioneAzDTO recensioneAzDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        throw new BadRequestException(bindingResult.getAllErrors());
    }
    return recensioneService.saveRAz(recensioneAzDTO);
}
}
