package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.enums.Settore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AziendaRepository extends JpaRepository<Azienda,Long> {
    Page<Azienda> findByNomeAziendaContaining(String nomeAzienda,Pageable pageable);
    Page<Azienda> findBySettore(Settore settore, Pageable pageable);

    Optional<Azienda> findByEmail(String email);

    Page<Azienda> findByNomeAziendaContainsAndEmailContainsAndCittaContainsAndPartitaIvaContains(String nomeAzienda,String email,String citta,String partitaIva,Pageable pageable);
}
