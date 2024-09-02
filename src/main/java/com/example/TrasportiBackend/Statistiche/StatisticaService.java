package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticaService {
    @Autowired
    StatisticaRepository statisticaRepository;

    @Autowired
    AnnuncioRepository annuncioRepository;

    public Statistica getByAziendaId(long id){

        Optional<Statistica> statistica = statisticaRepository.findByAzienda_Id(id);

        if(statistica.isPresent()){
            Statistica statistica1 = statistica.get();
            List<Annuncio> annuncios = annuncioRepository.findByAzienda_Id(id);
            if(annuncios.isEmpty()){
                return  statistica1;
            }
            
        }


        return statisticaRepository.findByAzienda_Id(id).orElseThrow(() ->new BadRequestException("Non è stata trovata una statistica appartenente all'azienda con id " + id));
    }
}
