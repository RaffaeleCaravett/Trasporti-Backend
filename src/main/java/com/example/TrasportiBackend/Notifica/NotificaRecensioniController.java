package com.example.TrasportiBackend.Notifica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trasportatore/notificaRecensione")
public class NotificaRecensioniController {
    @Autowired
    NotificaRecensioneService notificaRecensioneService;

    @PostMapping("/leggi")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public boolean leggi (@RequestBody @Validated List<NotificaRecensione> notificaRecensiones){
        return notificaRecensioneService.leggi(notificaRecensiones);
    }
    @GetMapping("TId/{id}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public Page<NotificaRecensione> getByTId(@PathVariable long id){
        return  notificaRecensioneService.getNotificheByTrasportatoreId(id);
    }
    @GetMapping("TIdAndStato/{id}/{stato}")
    @PreAuthorize("hasAuthority('Trasportatore')")
    public List<NotificaRecensione> getByTIdAndStato(@PathVariable long id,@PathVariable String stato){
        return  notificaRecensioneService.getNotificheByTrasportatoreIdAndStato(id,stato);
    }
}

