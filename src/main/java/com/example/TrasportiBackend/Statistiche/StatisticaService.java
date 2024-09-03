package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.enums.Stato;
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
            for(Annuncio a : annuncios){
                switch (a.getSpedizione().getStato()) {
                    case A_termine:
                        statistica1.setAnnunciATermine(statistica1.getAnnunciATermine()+1);
                            break;
                    case Guasto:
                        statistica1.setAnnunciGuasti(statistica1.getAnnunciGuasti()+1);
                        break;
                    case Stoppata:
                        statistica1.setAnnunciStoppati(statistica1.getAnnunciStoppati()+1);
                        break;
                    case In_corso:
                        statistica1.setAnnunciInCorso(statistica1.getAnnunciInCorso()+1);
                        break;
                    case Presa_in_carico:
                        statistica1.setAnnunciPresiInCarico(statistica1.getAnnunciPresiInCarico()+1);
                        break;

                }
            }
        }


        return statisticaRepository.findByAzienda_Id(id).orElseThrow(() ->new BadRequestException("Non è stata trovata una statistica appartenente all'azienda con id " + id));
    }
}
