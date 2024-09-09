package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.enums.PoloRecensione;
import com.example.TrasportiBackend.enums.StatoNotifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecensioneAzRepository extends JpaRepository<RecensioneAz,Long> {

    Page<RecensioneAz> findByA_IdAndPoloRecensione(long tId, PoloRecensione poloRecensione, Pageable pageable);
    Optional<RecensioneAz> findByA_IdAndDa_Id(long AzId,long tId);
    Page<RecensioneAz> findByA_Id(long azId,Pageable pageable);

}
