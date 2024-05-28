package by.anpoliakov.tiffbank.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
