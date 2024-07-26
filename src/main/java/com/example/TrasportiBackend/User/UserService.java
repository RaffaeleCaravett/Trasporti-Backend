package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.TrasportatoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TrasporatoreRepository trasporatoreRepository;
    @Autowired
    AziendaRepository aziendaRepository;

    public Trasportatore getTrasportatoreById(long id){
        return trasporatoreRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Trasportatore con id " + id + " non trovato in db"));
    }
    public Azienda getAziendaById(long id){
        return aziendaRepository.findById(id).orElseThrow(()-> new UserNotFoundException("Azienda con id " + id + " non trovato in db"));
    }

    public Trasportatore putById(long id , TrasportatoreDTO trasportatoreDTO) {
        try {
            Trasportatore trasportatore = trasporatoreRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + id + " non trovato in db"));

            trasportatore.setNome(trasportatoreDTO.nome());
            trasportatore.setCognome(trasportatoreDTO.cognome());
            trasportatore.setEta(trasportatoreDTO.eta());
            trasportatore.setCodiceFiscale(trasportatoreDTO.codiceFiscale());
            trasportatore.setCap(trasportatoreDTO.cap());
            trasportatore.setFlottaMezzi(trasportatoreDTO.flottaMezzi());
            trasportatore.setPartitaIva(trasportatoreDTO.partitaIva());
            trasportatore.setCitta(trasportatoreDTO.citta());
            trasportatore.setRegione(trasportatoreDTO.regione());
            trasportatore.setIndirizzo(trasportatoreDTO.indirizzo());
            trasportatore.setEmail(trasportatoreDTO.email());

            return trasporatoreRepository.save(trasportatore);
        }catch (Exception e){
            return new BadRequestException(e.getMessage());
        }
    }
    }
