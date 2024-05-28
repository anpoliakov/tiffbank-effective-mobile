package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, BigInteger> {
    Optional<Email> findById(BigInteger id);
    boolean existsByEmail(String email);
    void removeById(BigInteger id);
    long countByUserLogin(String login);
    boolean existsByUserLoginAndId(String login, BigInteger id);
}
