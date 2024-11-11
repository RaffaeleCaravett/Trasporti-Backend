package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.payloads.entities.NotificaDTO;
import org.aspectj.weaver.ast.Not;
import org.hibernate.engine.jdbc.mutation.spi.BindingGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/azienda/notifica")
public class NotificaController {

    @Autowired
    NotificaService notificaService;


    @PostMapping("")
    public Notifica save(@RequestBody @Validated NotificaDTO notificaDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return notificaService.save(notificaDTO);
    }

    @GetMapping("/{id}/{statoNotifica}/{sender}")
    public Page<Notifica> findByAziendaIdAndStato(@PathVariable long id, @PathVariable String statoNotifica, @PathVariable String sender) {
        return notificaService.findByAzienda_IdAndStatoNotificaAndSender(id, statoNotifica, sender, 0, 10, "id");
    }

    @PostMapping("/leggi/{aziendaId}")
    public List<Notifica> leggi(@RequestBody @Validated List<Notifica> notificas, @PathVariable long aziendaId) {
        return this.notificaService.leggi(notificas, aziendaId);
    }

    @GetMapping("/accetta/{idNotifica}/{idAzienda}")
    @PreAuthorize("hasAuthority('Azienda')")
    public byte[] accetta(@PathVariable long idNotifica, @PathVariable long idAzienda) {
        return notificaService.accetta(idNotifica, idAzienda);
    }

    @GetMapping("/rifiuta/{idNotifica}/{idAzienda}")
    @PreAuthorize("hasAuthority('Azienda')")
    public boolean rifiuta(@PathVariable long idNotifica, @PathVariable long idAzienda) {
        return notificaService.respingi(idNotifica, idAzienda);
    }
}
