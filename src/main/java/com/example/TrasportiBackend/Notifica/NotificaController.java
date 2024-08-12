package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifica")
public class NotificaController {

    @Autowired
    NotificaService notificaService;


    @PostMapping("")
    public Notifica save(@RequestBody @Validated NotificaDTO notificaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return notificaService.save(notificaDTO);
    }

    @GetMapping("az/{id}/{statoNotifica}/{sender}")
    public Page<Notifica> findByAziendaIdAndStato(@PathVariable long id, @PathVariable String statoNotifica,@PathVariable String sender){
        return notificaService.findByAzienda_IdAndStatoNotificaAndSender(id,statoNotifica,sender,0,10,"id");
    }
    @GetMapping("tr/{id}/{statoNotifica}/{sender}")
    public Page<Notifica> findByTrasportatoreIdAndStato(@PathVariable long id, @PathVariable String statoNotifica,@PathVariable String sender){
        return notificaService.findByTrasportatore_IdAndStatoNotificaAndSender(id,statoNotifica,sender,0,10,"id");
    }
}
