package by.anpoliakov.tiffbank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Object to send JWT to the server side")
public class JwtRequestDto {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
