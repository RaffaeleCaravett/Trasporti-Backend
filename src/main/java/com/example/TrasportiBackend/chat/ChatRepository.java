package com.example.TrasportiBackend.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByAzienda_IdAndTrasportatore_Id(long azId,long trId);
}
