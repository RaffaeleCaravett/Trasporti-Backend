package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.enums.Settore;
import com.example.TrasportiBackend.exceptions.BadRequestException;
import com.example.TrasportiBackend.exceptions.PasswordMismatchException;
import com.example.TrasportiBackend.exceptions.UserNotFoundException;
import com.example.TrasportiBackend.payloads.entities.AziendaDTO;
import com.example.TrasportiBackend.payloads.entities.AziendaPutDTO;
import com.example.TrasportiBackend.payloads.entities.TrasportatoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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

    public Azienda putAziendaById(long id , AziendaPutDTO aziendaDTO) {
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

    public boolean deleteTrasportatoreById(long id){
        try{
            Trasportatore trasportatore = trasporatoreRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Trasportatore con id " + id + " non trovato in db"));
            trasportatore.setIsActive(0);
            /*trasporatoreRepository.deleteById(id);*/
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public boolean deleteAziendaById(long id){
        try{
            Azienda azienda = aziendaRepository.findById(id).orElseThrow(()->new UserNotFoundException("Azienda con id " + id + " non trovata in db"));
            azienda.setIsActive(0);
            /*aziendaRepository.deleteById(id);*/
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
    public Page<Trasportatore> getAllTrasportatori(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return trasporatoreRepository.findAll(pageable);
    }
    public Page<Azienda> getAllAziende(int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));

        return aziendaRepository.findAll(pageable);
    }

    public Page<Trasportatore> findByNomeAndCognomeContaining(String nome,String cognome, int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return trasporatoreRepository.findByNomeContainingAndCognomeContaining(nome,cognome,pageable);
    }
    public Page<Azienda> findBySettore(String settore, int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        Settore settore1 = Settore.valueOf(settore);
        return aziendaRepository.findBySettore(settore1,pageable);
    }
    public Page<Azienda> findByNomeAzienda(String nomeAzienda, int page, int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return aziendaRepository.findByNomeAziendaContaining(nomeAzienda,pageable);
    }
public boolean bloccaTrasportatore(long tId,long azId){
  Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
try{
Azienda azienda = aziendaRepository.findById(azId).orElseThrow(()->new UserNotFoundException("Azienda con id " + azId + " non trovata in db"));
if(!azienda.getTrasportatoreList().isEmpty()) {
    for (Trasportatore trasportatore1 : azienda.getTrasportatoreList()) {
        if (trasportatore1.getId() == tId) {
            throw new BadRequestException("Trasportatore già bloccato");
        }
    }
    azienda.getTrasportatoreList().add(trasportatore);
}else{
    azienda.getTrasportatoreList().add(trasportatore);
}
return true;
}catch (Exception e){
   throw new BadRequestException(e.getMessage());
}

    }
    public boolean sbloccaTrasportatore(long tId,long azId) {
        Trasportatore trasportatore = trasporatoreRepository.findById(tId).orElseThrow(()->new UserNotFoundException("Trasportatore con id " + tId + " non trovato in db."));
        try {
Azienda azienda = aziendaRepository.findById(azId).orElseThrow(()->new UserNotFoundException("Azienda con id " + azId + " non trovata in db"));
if(azienda.getTrasportatoreList().isEmpty()){
    throw new BadRequestException("Non c'è nessun trasportatore da sbloccare.");
}
List<Trasportatore> newTList = new ArrayList<>();
for(Trasportatore trasportatore1 : azienda.getTrasportatoreList()){
    if(trasportatore1.getId()!=tId){
newTList.add(trasportatore1);
    }
}
azienda.setTrasportatoreList(newTList);
return true;
        } catch (Exception e) {
           throw new BadRequestException(e.getMessage());
        }
    }
    public Page<Trasportatore> findByCitta(String citta,int page,int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return this.trasporatoreRepository.findByCitta(citta,pageable);
    }
    public Page<Trasportatore> findByCittaAndNomeAndCognome(String citta,String nome,String cognome,int page,int size,String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return this.trasporatoreRepository.findByCittaAndNomeContainingAndCognomeContaining(citta,nome,cognome,pageable);
    }
    }
