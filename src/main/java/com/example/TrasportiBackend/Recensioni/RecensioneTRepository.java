package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.enums.PoloRecensione;
import com.example.TrasportiBackend.enums.StatoNotifica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecensioneTRepository extends JpaRepository<RecensioneT,Long> {
    Page<RecensioneT> findByA_IdAndPoloRecensione(long tId, PoloRecensione poloRecensione, Pageable pageable);
    Optional<RecensioneT> findByA_IdAndDa_Id(long tId, long AzId);

}
