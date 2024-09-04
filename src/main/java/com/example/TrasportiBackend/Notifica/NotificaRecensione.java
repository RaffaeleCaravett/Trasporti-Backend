package com.example.TrasportiBackend.Notifica;

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
@Table(name = "Notifica_recensione")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificaRecensione {
@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
private String testo;
private LocalDate dateTime;
@ManyToOne
@JoinColumn(name = "azienda_id")
@JsonIgnore
private Azienda da;
    @ManyToOne
    @JoinColumn(name = "trasportatore_id")
    @JsonIgnore
private Trasportatore a;
private StatoNotifica statoNotifica;
}
