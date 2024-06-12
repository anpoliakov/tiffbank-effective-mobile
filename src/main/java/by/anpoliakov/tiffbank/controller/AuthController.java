package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.JwtRequestDto;
import by.anpoliakov.tiffbank.domain.dto.JwtResponseDto;
import by.anpoliakov.tiffbank.domain.dto.RefreshJwtDto;
import by.anpoliakov.tiffbank.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Tag(name="Authentication Controller", description="Provides endpoints for obtaining JWT and partial/full update")
@Slf4j
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    @Operation(
            summary = "User authentication",
            description = "User authentication and issuance of token and refresh token"
    )
    public ResponseEntity<JwtResponseDto> login(@RequestBody JwtRequestDto authRequest) {
        final JwtResponseDto token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    @Operation(
            summary = "Renewal token",
            description = "Endpoint for renewal token by refresh token"
    )
    public ResponseEntity<JwtResponseDto> getNewAccessToken(@RequestBody RefreshJwtDto request) {
        final JwtResponseDto token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    @Operation(
            summary = "Renewal refresh token and token",
            description = "Endpoint for renewal refresh token and token (by refresh token)"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<JwtResponseDto> getNewRefreshToken(@RequestBody RefreshJwtDto request) {
        final JwtResponseDto token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
