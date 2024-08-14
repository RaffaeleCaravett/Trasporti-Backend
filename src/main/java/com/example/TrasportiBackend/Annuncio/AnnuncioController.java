package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@DeleteMapping("/byAzienda/{id}/{annuncioId}")
@PreAuthorize("hasAuthority('Azienda')")
    public boolean delete(@PathVariable long id,@PathVariable long annuncioId){
   return annuncioService.deleteByAzienda(id,annuncioId);
}
    @DeleteMapping("/byAdmin/{annuncioId}")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByAdmin(@PathVariable long annuncioId){
        return annuncioService.deleteByAdmin(annuncioId);
    }

    @PutMapping("/{aziendaId}/{annuncioId}")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Annuncio putById(@RequestBody @Validated AnnuncioDTO annuncioDTO,BindingResult bindingResult, @PathVariable long aziendaId, @PathVariable long annuncioId){
    if(bindingResult.hasErrors()){
        throw new BadRequestException(bindingResult.getAllErrors());
    }
    return annuncioService.putById(annuncioDTO,aziendaId,annuncioId);
    }

}
