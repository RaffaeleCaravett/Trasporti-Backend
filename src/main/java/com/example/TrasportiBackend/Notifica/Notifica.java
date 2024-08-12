package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.StatoNotifica;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "notifiche")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notifica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @ManyToOne
    @JoinColumn(name = "spedizione_id")
    private Spedizione spedizione;
    @ManyToOne
    @JoinColumn(name = "azienda_id")
    @JsonIgnore
    private Azienda azienda;
    @ManyToOne
    @JoinColumn(name = "trasportatore_id")
    private Trasportatore trasportatore;
    @Enumerated(EnumType.STRING)
    private StatoNotifica statoNotifica;
    private LocalDate dateTime;
    private String inviataDa;
    private String testo;
}
