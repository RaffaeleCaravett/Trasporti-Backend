package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trasportatore/notifica")
public class NotificaTrasportatoreController {

    @Autowired
    NotificaService notificaService;


    @PostMapping("")
    public Notifica save(@RequestBody @Validated NotificaDTO notificaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return notificaService.save(notificaDTO);
    }
    @GetMapping("/{id}/{statoNotifica}/{sender}")
    public Page<Notifica> findByTrasportatoreIdAndStato(@PathVariable long id, @PathVariable String statoNotifica,@PathVariable String sender){
        return notificaService.findByTrasportatore_IdAndStatoNotificaAndSender(id,statoNotifica,sender,0,10,"id");
    }
}