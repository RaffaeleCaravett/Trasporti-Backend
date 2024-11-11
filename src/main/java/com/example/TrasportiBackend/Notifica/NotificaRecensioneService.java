package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.User.TrasporatoreRepository;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
public class NotificaRecensioneService {
    @Autowired
    NotificaRecensioneRepository notificaRecensioneRepository;
@Autowired
    TrasporatoreRepository trasporatoreRepository;

    public NotificaRecensione save (NotificaRecensione notificaRecensione){
        return notificaRecensioneRepository.save(notificaRecensione);
    }

    public Page<NotificaRecensione> getNotificheByTrasportatoreId(long tId){
        Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
        Pageable pageable = PageRequest.of(0,25, Sort.by("id"));
        return notificaRecensioneRepository.findByA_Id(tId,pageable);
    }
    public List<NotificaRecensione> getNotificheByTrasportatoreIdAndStato(long tId,String stato){
        Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
        StatoNotifica statoNotifica = StatoNotifica.valueOf(stato);
        return notificaRecensioneRepository.findByA_IdAndStatoNotifica(tId,statoNotifica).stream().sorted(Comparator.comparing(NotificaRecensione::getDateTime)).toList();
    }
    public boolean leggi(List<NotificaRecensione> recensiones){
        try {
            for (NotificaRecensione n : recensiones) {
                n.setStatoNotifica(StatoNotifica.Letta);
                notificaRecensioneRepository.save(n);
            }
            return true;
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
