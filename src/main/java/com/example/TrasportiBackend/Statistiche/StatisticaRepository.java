package com.example.TrasportiBackend.Statistiche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatisticaRepository extends JpaRepository<Statistica,Long> {
    Optional<Statistica> findByAzienda_Id(long aziendaId);
}
