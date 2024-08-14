package com.example.TrasportiBackend.Spedizione;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore/spedizione")
public class SpedizioneTController {
    @Autowired
    SpedizioneService spedizioneService;

    @GetMapping("/byAziendaId/{aziendaId}")
    public Page<Spedizione> getByAziendaId(@PathVariable long aziendaId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
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