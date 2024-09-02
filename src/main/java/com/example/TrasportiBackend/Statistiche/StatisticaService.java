package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticaService {
    @Autowired
    StatisticaRepository statisticaRepository;


    public Statistica getByAziendaId(long id){
        return statisticaRepository.findByAzienda_Id(id).orElseThrow(() ->new BadRequestException("Non è stata trovata una statistica appartenente all'azienda con id " + id));
    }
}
