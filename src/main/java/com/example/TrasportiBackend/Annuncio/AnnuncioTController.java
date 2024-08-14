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
@RequestMapping("/trasportatore/annuncio")
public class AnnuncioTController {
    @Autowired
    AnnuncioService annuncioService;


    @GetMapping("/byAziendaId/{id}")
    public Page<Annuncio> getByAziendaId(@PathVariable long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.getByAziendaId(id,page,size,orderBy);
    }
    @GetMapping("/byRetribuzioneId/{retribuzione1}/{retribuzione2}")
    public Page<Annuncio> getByRetribuzione(@PathVariable long retribuzione1,@PathVariable long retribuzione2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.findByRetribuzione(retribuzione1,retribuzione2,page,size,orderBy);
    }
    @GetMapping("/byData/{anno1}/{mese1}/{giorno1}/{anno2}/{mese2}/{giorno2}")
    public Page<Annuncio> getByData(@PathVariable int anno1,@PathVariable int mese1,@PathVariable int giorno1,@PathVariable int anno2,@PathVariable int mese2,@PathVariable int giorno2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return annuncioService.findByData(anno1,mese1,giorno1,anno2,mese2,giorno2,page,size,orderBy);
    }
}
