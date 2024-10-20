package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.chat.Chat;
import com.example.TrasportiBackend.enums.StatoMessaggio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "messaggi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Messaggi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonIgnore
    private Chat chat;
    @ManyToOne
    @JoinColumn(name="az_sender_id")
    private Azienda aziendaAsSender;
    @ManyToOne
    @JoinColumn(name="tr_sender_id")
    private Trasportatore trasportatoreAsSender;
    @ManyToOne
    @JoinColumn(name="az_receiver_id")
    private Azienda aziendaAsReceiver;
    @ManyToOne
    @JoinColumn(name="tr_receiver_id")
    private Trasportatore trasportatoreAsReceiver;
    private LocalDate createdAt;
    private String testo;
    private StatoMessaggio statoMessaggio;
}
