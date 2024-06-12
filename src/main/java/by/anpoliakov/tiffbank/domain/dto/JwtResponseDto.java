package by.anpoliakov.tiffbank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Object to send JWT to the front end side (client side)")
public class JwtResponseDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    private final String type = "Bearer";
    private String token;
    private String refreshToken;
}
