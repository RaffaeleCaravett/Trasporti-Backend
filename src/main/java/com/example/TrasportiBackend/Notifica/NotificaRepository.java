package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Recensioni.RecensioneAz;
import com.example.TrasportiBackend.Recensioni.RecensioneT;
import com.example.TrasportiBackend.enums.StatoNotifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificaRepository extends JpaRepository<Notifica,Long> {
    Page<Notifica> findByAzienda_IdAndStatoNotificaAndInviataDa(long aziendaId, StatoNotifica statoNotifica,String inviataDa, Pageable pageable);
    Page<Notifica> findByTrasportatore_IdAndStatoNotificaAndInviataDa(long aziendaId, StatoNotifica statoNotifica,String inviataDa, Pageable pageable);
    Optional<Notifica> findByTesto(String testo);
    Optional<Notifica> findByTrasportatore_IdAndSpedizione_IdAndInviataDa(long trasportatoreId,long spedizioneId,String inviataDa);
}
