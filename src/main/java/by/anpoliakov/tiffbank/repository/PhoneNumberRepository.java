package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.PhoneNumber;
import by.anpoliakov.tiffbank.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, BigInteger> {
    boolean existsByPhoneNumber(String phoneNumber);
    void removeById(BigInteger id);
    long countByUserLogin(String login);
    boolean existsByUserLoginAndId(String login, BigInteger id);
    Optional<PhoneNumber> findById(BigInteger id);
    Optional<PhoneNumber> findByPhoneNumber(String phoneNumber);
}
