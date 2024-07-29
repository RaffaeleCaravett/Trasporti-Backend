package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.enums.Settore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    private Spedizione spedizione;
    @OneToMany(mappedBy = "azienda")
    @JsonIgnore
    private Annuncio annuncio;
}
