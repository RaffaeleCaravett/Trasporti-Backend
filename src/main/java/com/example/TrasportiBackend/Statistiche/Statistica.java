package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.User.Azienda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "statistiche")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Statistica {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private int annunciPubblicati;
    private int annunciPresiInCarico;
    private int annunciInCorso;
    private int annunciStoppati;
    private int annunciGuasti;
    private int annunciATermine;
    @OneToOne
    private Azienda azienda;
}
