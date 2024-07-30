package com.example.TrasportiBackend.Notifica;

import com.example.TrasportiBackend.Spedizione.Spedizione;
import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Azienda azienda;
    @ManyToOne
    @JoinColumn(name = "trasportatore_id")
    private Trasportatore trasportatore;
}
