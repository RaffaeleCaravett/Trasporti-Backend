package com.example.TrasportiBackend.SecretCode;

import com.example.TrasportiBackend.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "secret_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecretCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String secretCode;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
