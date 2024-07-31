package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.exceptions.SpedizioneHasErrorsException;
import com.example.TrasportiBackend.payloads.SpedizioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return spedizioneService.save(spedizioneDTO);
    }
    @GetMapping("/assegna")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione assegna(@RequestParam(defaultValue = "0") long id,@RequestParam(defaultValue = "0") long trasportatoreId,@RequestParam(defaultValue = "0") long aziendaId){
        return spedizioneService.assegna(id,trasportatoreId,aziendaId);
    }
    @GetMapping("/completa")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione completa(@RequestParam(defaultValue = "0") long id,@RequestParam(defaultValue = "0") long trasportatoreId,@RequestParam(defaultValue = "0") long aziendaId){
        return spedizioneService.completa(id,trasportatoreId,aziendaId);
    }
    @GetMapping("/stoppa")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione stoppa(@RequestParam(defaultValue = "0") long id,@RequestParam(defaultValue = "0") long trasportatoreId,@RequestParam(defaultValue = "0") long aziendaId){
        return spedizioneService.stoppa(id,trasportatoreId,aziendaId);
    }
    @GetMapping("/guasto")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione guasto(@RequestParam(defaultValue = "0") long id,@RequestParam(defaultValue = "0") long trasportatoreId,@RequestParam(defaultValue = "0") long aziendaId){
        return spedizioneService.guasto(id,trasportatoreId,aziendaId);
    }
    @DeleteMapping("")
    @PreAuthorize("hasAuthority('Azienda')")
    public boolean delete(@RequestParam(defaultValue = "0") long spedizioneId,@RequestParam(defaultValue = "0") long aziendaId){
        return spedizioneService.delete(spedizioneId,aziendaId);
    }
    @DeleteMapping("/byAdmin")
    @PreAuthorize("hasAuthority('Admin')")
    public boolean deleteByAdmin(@RequestParam(defaultValue = "0") long spedizioneId){
        return spedizioneService.deleteByAdmin(spedizioneId);
    }
}
