package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.filter.JwtAuthentication;
import by.anpoliakov.tiffbank.domain.dto.JwtRequest;
import by.anpoliakov.tiffbank.domain.dto.JwtResponse;
import by.anpoliakov.tiffbank.domain.entity.RefreshToken;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.repository.RefreshTokenRepository;
import by.anpoliakov.tiffbank.repository.UserRepository;
import by.anpoliakov.tiffbank.util.JwtProvider;
import by.anpoliakov.tiffbank.util.exception.UserAccountException;
import by.anpoliakov.tiffbank.util.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Transactional
    public JwtResponse login(@Valid JwtRequest authRequest) {
        final User user = userRepo.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new UserNotFoundException("User is not found!"));

        if (user.getPassword().equals(authRequest.getPassword())) {
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
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new UserAccountException("Incorrect password");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();

            final User user = userRepo.findByLogin(login)
                    .orElseThrow(() -> new UserNotFoundException("User is not found!"));
            final String saveRefreshToken = user.getRefreshToken().getToken();

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken){
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
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
