package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.enums.StatoNotifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecensioneAzRepository extends JpaRepository<RecensioneAz,Long> {

    Page<RecensioneAz> findByA_IdAndStatoNotifica(long azId, StatoNotifica statoNotifica, Pageable pageable);
}
