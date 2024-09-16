package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.AnnuncioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/trasportatore/annuncio")
public class AnnuncioTController {
    @Autowired
    AnnuncioService annuncioService;


    @GetMapping("/byAziendaId/{id}")
    public Page<Annuncio> getByAziendaId(@PathVariable long id, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction){
        return annuncioService.getByAziendaId(id,page,size,orderBy);
    }
    @GetMapping("/byRetribuzioneId/{retribuzione1}/{retribuzione2}")
    public Page<Annuncio> getByRetribuzione(@PathVariable int retribuzione1,@PathVariable int retribuzione2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction){
        return annuncioService.findByRetribuzione(retribuzione1,retribuzione2,page,size,orderBy);
    }
    @GetMapping("/byData/{anno1}/{mese1}/{giorno1}/{anno2}/{mese2}/{giorno2}")
    public Page<Annuncio> getByData(@PathVariable int anno1,@PathVariable int mese1,@PathVariable int giorno1,@PathVariable int anno2,@PathVariable int mese2,@PathVariable int giorno2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction){
        return annuncioService.findByData(anno1,mese1,giorno1,anno2,mese2,giorno2,page,size,orderBy);
    }
    @GetMapping("/byDataSpedizione/{data1}/{data2}")
    public Page<Annuncio> getByData(@PathVariable int data1,@PathVariable int data2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction){
        return annuncioService.findByDaSpedire(data1,data2,page,size,orderBy,direction);
    }
    @GetMapping("/byNomeAzienda/{nomeAzienda}")
    public Page<Annuncio> getByNomeAzienda(@PathVariable String nomeAzienda,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction){
        return annuncioService.getByNomeAzienda(nomeAzienda,page,size,orderBy,direction);
    }
    @GetMapping("/byNumeroPedane/{numeroPedane}")
    public Page<Annuncio> getByNumeroPedane(@PathVariable long numeroPedane,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy, @RequestParam(defaultValue = "DESC") String direction) {
    return annuncioService.getByNumeroPedane(numeroPedane,page,size,orderBy,direction);
    }
}
