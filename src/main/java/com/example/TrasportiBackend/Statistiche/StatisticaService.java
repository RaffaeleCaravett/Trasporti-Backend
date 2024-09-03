package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Annuncio.AnnuncioRepository;
import com.example.TrasportiBackend.User.AziendaRepository;
import com.example.TrasportiBackend.enums.Stato;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
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
    @Autowired
    AziendaRepository aziendaRepository;
    public Statistica getByAziendaId(long id) {

        Optional<Statistica> statistica = statisticaRepository.findByAzienda_Id(id);
        List<Annuncio> annuncios = annuncioRepository.findByAzienda_Id(id);
        Statistica statistica1 = new Statistica();
        statistica1.setAzienda(aziendaRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Azienda con id " + id + " non trovata in db.")));
        if (statistica.isPresent()) {
            statistica1 = statistica.get();
        }
        if (annuncios.isEmpty()) {
            return statistica1;
        }
        statistica1.setAnnunciGuasti(0);
        statistica1.setAnnunciATermine(0);
        statistica1.setAnnunciInCorso(0);
        statistica1.setAnnunciPubblicati(0);
        statistica1.setAnnunciPresiInCarico(0);
        statistica1.setAnnunciStoppati(0);
        for (Annuncio a : annuncios) {
            switch (a.getSpedizione().getStato()) {
                case A_termine:
                    statistica1.setAnnunciATermine(statistica1.getAnnunciATermine() + 1);
                    break;
                case Guasto:
                    statistica1.setAnnunciGuasti(statistica1.getAnnunciGuasti() + 1);
                    break;
                case Stoppata:
                    statistica1.setAnnunciStoppati(statistica1.getAnnunciStoppati() + 1);
                    break;
                case In_corso:
                    statistica1.setAnnunciInCorso(statistica1.getAnnunciInCorso() + 1);
                    break;
                case Presa_in_carico:
                    statistica1.setAnnunciPresiInCarico(statistica1.getAnnunciPresiInCarico() + 1);
                    break;
                case Pubblicata:
                    statistica1.setAnnunciPubblicati(statistica1.getAnnunciPubblicati() + 1);
                    break;
                default:
                    System.out.println("State non available.");
                    break;
            }
        }

        return statisticaRepository.save(statistica1);
    }
}
