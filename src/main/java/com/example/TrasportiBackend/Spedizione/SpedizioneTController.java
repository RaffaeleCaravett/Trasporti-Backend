package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.User.Trasportatore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    @GetMapping("/richiedi/{spedizioneId}/me")
    public Notifica richiedi(@AuthenticationPrincipal Trasportatore trasportatore, @PathVariable long spedizioneId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return spedizioneService.richiedi(trasportatore.getId(),spedizioneId);
    }
}