package by.anpoliakov.tiffbank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Transfer of refresh token")
public class RefreshJwtDto {
    public String refreshToken;
}
