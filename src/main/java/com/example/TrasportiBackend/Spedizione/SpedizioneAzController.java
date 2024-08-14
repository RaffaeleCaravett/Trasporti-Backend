package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.exceptions.SpedizioneHasErrorsException;
import com.example.TrasportiBackend.payloads.entities.SpedizioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/azienda/spedizione")
public class SpedizioneAzController {
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
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Spedizione putById(@PathVariable long id,@RequestBody @Validated SpedizioneDTO spedizioneDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new SpedizioneHasErrorsException(bindingResult.getAllErrors());
        }
        return spedizioneService.putById(id,spedizioneDTO);
    }

    @GetMapping("/byAziendaId/{aziendaId}")
    public Page<Spedizione> getByAziendaId(@PathVariable long aziendaId,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return spedizioneService.getAllByAziendaId(aziendaId,page,size,orderBy);
    }
    @GetMapping("/byDa")
    public Page<Spedizione> findByDa(@RequestParam(defaultValue = "") String da,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return spedizioneService.findByDa(da,page,size,orderBy);
    }
    @GetMapping("/byA")
    public Page<Spedizione> findByA(@RequestParam(defaultValue = "") String a,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return spedizioneService.findByA(a,page,size,orderBy);
    }
    @GetMapping("/byDaAndA")
    public Page<Spedizione> findByDaAndA(@RequestParam(defaultValue = "") String da,@RequestParam(defaultValue = "") String a,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return spedizioneService.findByDaAndA(da,a,page,size,orderBy);
    }

}
