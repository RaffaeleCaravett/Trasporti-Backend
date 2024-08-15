package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.enums.Stato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AnnuncioRepository extends JpaRepository<Annuncio,Long> {
    Optional<Annuncio> findBySpedizione_Id(long spedizioneId);
    Page<Annuncio> findByAzienda_Id(long aziendaId,Pageable pageable);
    Page<Annuncio> findByAzienda_IdAndSpedizione_Stato(long aziendaId, Stato stato, Pageable pageable);

    Page<Annuncio> findByRetribuzioneBetween(long da, long a, Pageable pageable);
    Page<Annuncio> findBydataPubblicazioneBetween(LocalDate da, LocalDate a, Pageable pageable);

}
