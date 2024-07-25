package com.example.TrasportiBackend.User;

import com.example.TrasportiBackend.Settore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Aziende")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Azienda extends User{
    private String nomeAzienda;
    private String partitaIva;
    private String fatturatoMedio;
    private long numeroDipendenti;
    @Enumerated(EnumType.STRING)
    private Settore settore;
}
