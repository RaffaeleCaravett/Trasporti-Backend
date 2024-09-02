package com.example.TrasportiBackend.Statistiche;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/azienda/statistica")
public class StatisticaController {

    @Autowired
    StatisticaService statisticaService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Admin','Azienda')")
    public Statistica getByAziendaId(@PathVariable long id){
        return statisticaService.getByAziendaId(id);
    }
}
