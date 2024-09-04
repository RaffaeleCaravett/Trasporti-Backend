package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.Notifica.NotificaRecensione;
import com.example.TrasportiBackend.Recensioni.RecensioneAz;
import com.example.TrasportiBackend.Recensioni.RecensioneT;
import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.Statistiche.Statistica;
import com.example.TrasportiBackend.enums.Settore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Aziende")
@Getter
@Setter
public class Azienda extends User{
    private String nomeAzienda;
    private String partitaIva;
    private String fatturatoMedio;
    private long numeroDipendenti;
    @Enumerated(EnumType.STRING)
    private Settore settore;
    @OneToMany(mappedBy = "azienda")
    @JsonIgnore
    private List<Spedizione> spedizione;
    @OneToMany(mappedBy = "azienda")
    @JsonIgnore
    private List<Annuncio> annuncio;
    @OneToMany(mappedBy = "azienda")
    private List<Notifica> notifica;
    @ManyToMany
    @JoinTable(name = "azienda_trasportatori_blocchi",
    joinColumns= @JoinColumn(name = "azienda_id"),
            inverseJoinColumns = @JoinColumn(name = "trasportatore_id"))
            private List<Trasportatore> trasportatoreList;
    @OneToOne(mappedBy = "azienda")
    @JsonIgnore
    private Statistica statistica;
    @OneToMany(mappedBy = "da")
    private List<RecensioneT> recensioniInviate;
    @OneToMany(mappedBy = "a")
    private List<RecensioneAz> recensioniRicevute;
    @OneToMany(mappedBy = "da")
    private List<NotificaRecensione> notificaRecensioneInviate;
}
