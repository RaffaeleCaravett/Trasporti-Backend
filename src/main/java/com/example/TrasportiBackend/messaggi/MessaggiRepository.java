package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.enums.StatoMessaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessaggiRepository extends JpaRepository<Messaggi, Long> {
    List<Messaggi> findByChat_Id(long chatId);

    List<Messaggi> findByTrasportatoreAsReceiver_IdAndStatoMessaggio(long tId, StatoMessaggio statoMessaggio);

    List<Messaggi> findByAziendaAsReceiver_IdAndStatoMessaggio(long tId, StatoMessaggio statoMessaggio);
    List<Messaggi> findAllByRoom(String room);

}
