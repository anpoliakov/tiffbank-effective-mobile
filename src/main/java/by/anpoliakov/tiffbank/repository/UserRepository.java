package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);

    boolean existsByLogin(String login);

    List<User> findAllByBirthdayGreaterThanOrderByBirthday(LocalDate birthday);

    List<User> findAllByBirthdayGreaterThan(LocalDate birthday, Pageable pageable);

    List<User> findByFullNameStartingWith(String fullName);
}
