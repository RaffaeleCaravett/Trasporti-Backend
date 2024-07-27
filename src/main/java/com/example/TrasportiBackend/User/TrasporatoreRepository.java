package com.example.TrasportiBackend.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrasporatoreRepository extends JpaRepository<Trasportatore,Long> {

    Page<Trasportatore> findByNomeContainingAndCognomeContaining(String nome, String cognome, Pageable pageable);
    Optional<Trasportatore> findByEmail(String email);
}
