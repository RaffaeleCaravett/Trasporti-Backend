package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.enums.StatoNotifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica,Long> {
    Page<Notifica> findByAzienda_IdAndStatoNotifica(long aziendaId, StatoNotifica statoNotifica, Pageable pageable);
    Page<Notifica> findByTrasportatore_IdAndStatoNotifica(long aziendaId, StatoNotifica statoNotifica, Pageable pageable);

}
