package com.example.TrasportiBackend.Spedizione;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpedizioneRepository extends JpaRepository<Spedizione,Long> {
    Optional<Spedizione> findByAnnuncio_Id(long annuncioId);
}
