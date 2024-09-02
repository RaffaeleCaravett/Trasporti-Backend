package com.example.TrasportiBackend.Statistiche;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticaRepository extends JpaRepository<Statistica,Long> {
}
