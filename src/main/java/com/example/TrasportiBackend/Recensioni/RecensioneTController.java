package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.RecensioneAzDTO;
import com.example.TrasportiBackend.payloads.entities.RecensioneTDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore/recensione")
public class RecensioneTController {
    @Autowired
    RecensioneService recensioneService;

    @PostMapping("")
    public RecensioneT save(@RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.saveRT(recensioneAzDTO);
    }
    @PutMapping("/{id}/{recensioneAzDTOId}")
    public RecensioneT putById(@PathVariable long id, @PathVariable long recensioneAzDTOId, @RequestBody @Validated RecensioneTDTO recensioneAzDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return recensioneService.putTbyId(id,recensioneAzDTOId,recensioneAzDTO);
    }
}
