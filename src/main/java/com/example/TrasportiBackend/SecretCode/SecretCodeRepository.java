package com.example.TrasportiBackend.SecretCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecretCodeRepository extends JpaRepository<SecretCode,Long> {
    Optional<SecretCode> findByUser_Id(long id);
    Optional<SecretCode> findBySecretCodeAndUser_Id(String secretCode,long id);

}
