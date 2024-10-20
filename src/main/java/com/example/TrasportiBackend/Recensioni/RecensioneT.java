package com.example.TrasportiBackend.Recensioni;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.PoloRecensione;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


    @Entity
    @Table(name = "recensioni_tr")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class RecensioneT {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        private String commento;
        private LocalDate localDate = LocalDate.now();
        @ManyToOne
        @JoinColumn(name = "azienda_id")
        private Azienda da;
        @ManyToOne
        @JoinColumn(name = "trasportatore_id")
        @JsonIgnore
        private Trasportatore a;
        private PoloRecensione poloRecensione;
    }

