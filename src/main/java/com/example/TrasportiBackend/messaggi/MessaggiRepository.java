package com.example.TrasportiBackend.messaggi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessaggiRepository extends JpaRepository<Messaggi,Long> {

}
