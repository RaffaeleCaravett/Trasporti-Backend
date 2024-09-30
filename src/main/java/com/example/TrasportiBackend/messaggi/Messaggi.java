package com.example.TrasportiBackend.messaggi;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.chat.Chat;
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
    private Azienda azienda_as_sender;
    @ManyToOne
    @JoinColumn(name="tr_sender_id")
    private Trasportatore trasportatore_as_sender;
    @ManyToOne
    @JoinColumn(name="az_receiver_id")
    private Azienda azienda_as_receiver;
    @ManyToOne
    @JoinColumn(name="tr_receiver_id")
    private Trasportatore trasportatore_as_receiver;
    private LocalDate createdAt;,
    
}
