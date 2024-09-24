package com.example.TrasportiBackend.chat;

import com.example.TrasportiBackend.User.Azienda;
import com.example.TrasportiBackend.User.Trasportatore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "azienda_id")
    private Azienda azienda;
    @ManyToOne
    @JoinColumn(name = "trasportatore_id")
    private Trasportatore trasportatore;
    @OneToMany(mappedBy = "chat")
    private List<Messaggi> messaggiList;

}
