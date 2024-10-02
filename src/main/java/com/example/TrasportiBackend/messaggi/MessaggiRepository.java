package com.example.TrasportiBackend.messaggi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessaggiRepository extends JpaRepository<Messaggi,Long> {
List<Messaggi> findByChat_Id(long chatId);
}
