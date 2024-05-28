package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
}
