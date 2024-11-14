package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.enums.Stato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SpedizioneRepository extends JpaRepository<Spedizione,Long> {
    Optional<Spedizione> findByAnnuncio_Id(long annuncioId);

    Page<Spedizione> findByAzienda_Id(long aziendaId, Pageable pageable);
    Page<Spedizione> findByTrasportatore_Id(long trasportatoreId, Pageable pageable);

    Page<Spedizione> findByTrasportatore_IdAndStato(long trasportatoreId,Stato stato, Pageable pageable);

    Page<Spedizione> findBydaSpedireBetween(LocalDate date1, LocalDate date2,Pageable pageable);
    Page<Spedizione> findByDa(String da, Pageable pageable);
    Page<Spedizione> findByA(String a, Pageable pageable);
    Page<Spedizione> findByDaContainingAndAContaining(String da,String a, Pageable pageable);
    Page<Spedizione> findByAzienda_IdAndStato(long aziendaId, Stato stato, Pageable pageable);

}
