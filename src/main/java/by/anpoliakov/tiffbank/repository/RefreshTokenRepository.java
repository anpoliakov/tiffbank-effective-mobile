package by.anpoliakov.tiffbank.repository;

import by.anpoliakov.tiffbank.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
