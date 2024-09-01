package com.example.TrasportiBackend.Statistiche;

import com.example.TrasportiBackend.Annuncio.Annuncio;
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
    private List<Annuncio> annunciPubblicati;
    private List<Annuncio> annunciPresiInCarico;
    private List<Annuncio> annunciInCorso;
    private List<Annuncio> annunciStoppati;
    private List<Annuncio> annunciGuasti;
    private List<Annuncio> annunciATermine;
}
