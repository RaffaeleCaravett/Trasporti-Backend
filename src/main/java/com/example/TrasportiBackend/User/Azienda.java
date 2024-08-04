package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.Spedizione.Spedizione;
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
}
