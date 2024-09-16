package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.enums.Stato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AnnuncioRepository extends JpaRepository<Annuncio,Long> {
    Optional<Annuncio> findBySpedizione_Id(long spedizioneId);
    Page<Annuncio> findByAzienda_Id(long aziendaId,Pageable pageable);
    List<Annuncio> findByAzienda_Id(long aziendaId);

    Page<Annuncio> findByAzienda_IdAndSpedizione_Stato(long aziendaId, Stato stato, Pageable pageable);
    List<Annuncio> findByAzienda_IdAndSpedizione_Stato(long aziendaId, Stato stato);

    Page<Annuncio> findByRetribuzioneBetween(int da,int a, Pageable pageable);
    Page<Annuncio> findBydataPubblicazioneBetween(LocalDate da, LocalDate a, Pageable pageable);
    Page<Annuncio> findBySpedizione_DaSpedireBetween(LocalDate da, LocalDate a, Pageable pageable);
    Page<Annuncio> findByAzienda_NomeAzienda(String nomeAzienda, Pageable pageable);
    Page<Annuncio> findBySpedizione_NumeroPedane(Long numeroPedane, Pageable pageable);
}
