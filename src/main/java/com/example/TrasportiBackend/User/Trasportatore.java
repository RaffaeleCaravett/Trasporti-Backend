package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.Notifica.NotificaRecensione;
import com.example.TrasportiBackend.Recensioni.RecensioneAz;
import com.example.TrasportiBackend.Recensioni.RecensioneT;
import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.chat.Chat;
import com.example.TrasportiBackend.messaggi.Messaggi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Trasportatori")
@Getter
@Setter
public class Trasportatore extends User{
    private String nome;
    private String cognome;
    private int eta;
    private String codiceFiscale;
    private String partitaIva;
    private long flottaMezzi;
    private boolean isActive;
    @OneToMany(mappedBy = "trasportatore")
    @JsonIgnore
    private List<Spedizione> spedizione;
    @OneToMany(mappedBy = "trasportatore")
    @JsonIgnore
    private List<Notifica> notifica;
    @ManyToMany(mappedBy = "trasportatoreList")
    @JsonIgnore
    List<Azienda> aziendaList;
    @OneToMany(mappedBy = "da")
    @JsonIgnore
    private List<RecensioneAz> recensioniInviate;
    @OneToMany(mappedBy = "da")
    private List<RecensioneT> recensioniRicevute;
    @OneToMany(mappedBy = "a")
    private List<NotificaRecensione> notificaRecensioneRicevute;
    @OneToMany(mappedBy = "trasportatore")
    @JsonIgnore
    private List<Chat> chats;
    @OneToMany(mappedBy = "trasportatoreAsReceiver")
    @JsonIgnore
    private List<Messaggi> messaggi_as_receiver;
    @OneToMany(mappedBy = "trasportatoreAsSender")
    @JsonIgnore
    private List<Messaggi> messaggi_as_sender;
}
