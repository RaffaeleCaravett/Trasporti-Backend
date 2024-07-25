package com.example.TrasportiBackend.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Trasportatori")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Trasportatore extends User{
    private String nome;
    private String cognome;
    private int eta;
    private String codiceFiscale;
    private String partitaIva;
    private long flottaMezzi;
}
