package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.filter.JwtAuthentication;
import by.anpoliakov.tiffbank.domain.dto.JwtRequestDto;
import by.anpoliakov.tiffbank.domain.dto.JwtResponseDto;
import by.anpoliakov.tiffbank.domain.entity.RefreshToken;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.repository.RefreshTokenRepository;
import by.anpoliakov.tiffbank.repository.UserRepository;
import by.anpoliakov.tiffbank.util.JwtProvider;
import by.anpoliakov.tiffbank.util.exception.AuthException;
import by.anpoliakov.tiffbank.util.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepo;
    private final RefreshTokenRepository tokenRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtResponseDto login(@Valid JwtRequestDto authRequest) {
        final User user = userRepo.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException("User is not found!"));

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            //TODO: заменить на Redis
            RefreshToken saveRefreshToken = user.getRefreshToken();
            if (saveRefreshToken != null) {
                saveRefreshToken.setToken(refreshToken);
                userRepo.save(user);
            } else {
                tokenRepo.save(new RefreshToken(user, refreshToken));
            }
            return new JwtResponseDto(accessToken, refreshToken);
        } else {
            throw new AuthException("Incorrect password!");
        }
    }

    public JwtResponseDto getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final User user = userRepo.findByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException("User is not found!"));
            final String saveRefreshToken = user.getRefreshToken().getToken();

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponseDto(accessToken, null);
            }
        }
        return new JwtResponseDto(null, null);
    }

    public JwtResponseDto refresh(@NonNull String refreshToken){
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final User user = userRepo.findByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException("User is not found!"));
            final RefreshToken saveRefreshToken = user.getRefreshToken();

            if (saveRefreshToken != null && saveRefreshToken.getToken().equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                saveRefreshToken.setToken(refreshToken);
                userRepo.save(user);
                return new JwtResponseDto(accessToken, newRefreshToken);
            }
        }
        return new JwtResponseDto(null, null);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
