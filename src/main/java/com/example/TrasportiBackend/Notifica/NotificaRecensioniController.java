package com.example.TrasportiBackend.Notifica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public boolean leggi (@RequestBody @Validated List<NotificaRecensione> notificaRecensiones){
        return notificaRecensioneService.leggi(notificaRecensiones);
    }
    @GetMapping("TId/{id}")
    public Page<NotificaRecensione> getByTId(@PathVariable long id){
        return  notificaRecensioneService.getNotificheByTrasportatoreId(id);
    }
    @GetMapping("TIdAndStato/{id}/{stato}")
    public List<NotificaRecensione> getByTIdAndStato(@PathVariable long id,@PathVariable String stato){
        return  notificaRecensioneService.getNotificheByTrasportatoreIdAndStato(id,stato);
    }
}

