package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/azienda/annuncio")
public class AnnuncioAziendaController {

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

    @GetMapping("/byAziendaId/{id}")
    public Page<Annuncio> getByAziendaId(@PathVariable long id,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
    return annuncioService.getByAziendaId(id,page,size,orderBy);
    }
    @GetMapping("/byAziendaIdAndStato/{id}/{stato}")
    public Page<Annuncio> getByAziendaIdAndStato(@PathVariable long id,@PathVariable String stato,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.getByAziendaIdAndStato(id,stato,page,size,orderBy);
    }
    @GetMapping("/byAziendaIdAndStatoPubblicata/{id}")
    public long getByAziendaIdAndStato(@PathVariable long id,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.getByAziendaIdAndStatoPubblicata(id,"Pubblicata",page,size,orderBy);
    }
    @GetMapping("/byRetribuzione/{retribuzione1}/{retribuzione2}")
    public Page<Annuncio> getByRetribuzione(@PathVariable int retribuzione1,@PathVariable int retribuzione2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.findByRetribuzione(retribuzione1,retribuzione2,page,size,orderBy);
    }
    @GetMapping("/byData/{anno1}/{mese1}/{giorno1}/{anno2}/{mese2}/{giorno2}")
    public Page<Annuncio> getByData(@PathVariable int anno1,@PathVariable int mese1,@PathVariable int giorno1,@PathVariable int anno2,@PathVariable int mese2,@PathVariable int giorno2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.findByData(anno1,mese1,giorno1,anno2,mese2,giorno2,page,size,orderBy);
    }

}
