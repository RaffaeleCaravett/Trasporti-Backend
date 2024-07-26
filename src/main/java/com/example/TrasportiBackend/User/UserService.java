package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.enums.Settore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.AziendaDTO;
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

    public Trasportatore putTrasportatoreById(long id , TrasportatoreDTO trasportatoreDTO) {
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
            throw new BadRequestException(e.getMessage());
        }
    }

    public Azienda putAziendaById(long id , AziendaDTO aziendaDTO) {
        try {
            Azienda azienda = aziendaRepository.findById(id).orElseThrow(()->new UserNotFoundException("Azienda con id " + id + " non trovata in db"));
            azienda.setCap(aziendaDTO.cap());
            azienda.setCitta(aziendaDTO.citta());
            azienda.setRegione(aziendaDTO.regione());
            azienda.setIndirizzo(aziendaDTO.indirizzo());
            azienda.setEmail(aziendaDTO.email());
            azienda.setNomeAzienda(aziendaDTO.nomeAzienda());
            azienda.setFatturatoMedio(aziendaDTO.fatturatoMedio());
            azienda.setNumeroDipendenti(aziendaDTO.numeroDipendenti());
            azienda.setSettore(Settore.valueOf(aziendaDTO.settore()));
            azienda.setPartitaIva(aziendaDTO.partitaIva());
            return aziendaRepository.save(azienda);
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    }
