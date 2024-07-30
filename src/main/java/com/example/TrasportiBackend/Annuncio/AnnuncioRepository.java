package com.example.TrasportiBackend.Annuncio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnuncioRepository extends JpaRepository<Annuncio,Long> {
    <Optional>Annuncio findBySpedizione_Id(long spedizioneId);
}
