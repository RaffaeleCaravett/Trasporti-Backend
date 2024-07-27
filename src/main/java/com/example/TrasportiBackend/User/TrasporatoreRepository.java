package com.example.TrasportiBackend.User;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrasporatoreRepository extends JpaRepository<Trasportatore,Long> {

    Page<Trasportatore> findByNomeContainingAndCognomeContaining(String nome, String cognome);

}
