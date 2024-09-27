package com.example.TrasportiBackend.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    Optional<Chat> findByAzienda_IdAndTrasportatore_Id(long azId, long trId);
    List<Chat> findByAzienda_Id(long id);
    List<Chat> findByTrasportatore_Id(long id);
}
