package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.enums.StatoMessaggio;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessaggiRepository extends JpaRepository<Messaggi, Long> {
    @Query("select m from Messaggi m where m.chat.id = ?1 order by id asc")
    List<Messaggi> findByChat_IdOrderByIdAsc(long chatId);

    List<Messaggi> findByTrasportatoreAsReceiver_IdAndStatoMessaggio(long tId, StatoMessaggio statoMessaggio);

    List<Messaggi> findByAziendaAsReceiver_IdAndStatoMessaggio(long tId, StatoMessaggio statoMessaggio);
    List<Messaggi> findAllByRoom(String room);

}
