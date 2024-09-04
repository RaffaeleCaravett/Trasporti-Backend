package com.example.TrasportiBackend.Notifica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificaRecensioneRepository extends JpaRepository<NotificaRecensione,Long> {
    Page<NotificaRecensione> findByA_Id(long aId, Pageable pageable);

}
