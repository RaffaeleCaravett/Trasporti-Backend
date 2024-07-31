package com.example.TrasportiBackend.Annuncio;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.User.Azienda;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "annunci")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Annuncio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate dataPubblicazione = LocalDate.now();
    private long retribuzione;
    @ManyToOne
    @JoinColumn(name = "azienda_id")
    private Azienda azienda;
    @OneToOne
    @JoinColumn(name = "spedizione_id")
    private Spedizione spedizione;
}
