package com.example.TrasportiBackend.Recensioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecensioneTRepository extends JpaRepository<RecensioneT,Long> {
}
