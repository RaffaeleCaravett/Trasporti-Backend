package com.example.TrasportiBackend.Notifica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificaRecensioneRepository extends JpaRepository<NotificaRecensione,Long> {
}
