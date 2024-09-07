package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/{recensioneAzDTOId}")
    public RecensioneAz putById(@PathVariable long id, @PathVariable long recensioneAzDTOId, @RequestBody @Validated RecensioneAzDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.putAzbyId(id,recensioneAzDTOId,recensioneAzDTO);
    }
}
