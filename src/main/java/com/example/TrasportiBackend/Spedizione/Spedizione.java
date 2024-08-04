package com.example.TrasportiBackend.Spedizione;

import com.example.TrasportiBackend.Annuncio.Annuncio;
import com.example.TrasportiBackend.Notifica.Notifica;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import com.example.TrasportiBackend.enums.Stato;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "spedizioni")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Spedizione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String da;
    private String a;
    private LocalDate daSpedire;
    private String descrizioneMerce;
    private long numeroPedane;
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @ManyToOne
    @JoinColumn(name = "azienda_id")
    private Azienda azienda;
    @ManyToOne
    @JoinColumn(name = "trasportatore_id")
    private Trasportatore trasportatore;
    @OneToOne(mappedBy = "spedizione")
    @JsonIgnore
    private Annuncio annuncio;
    @OneToMany(mappedBy = "spedizione")
    @JsonIgnore
    private List<Notifica> notifica;
}
