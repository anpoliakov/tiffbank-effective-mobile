package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByUserLogin(String login);
    Optional<Account> findByUserId(BigInteger userId);
}
