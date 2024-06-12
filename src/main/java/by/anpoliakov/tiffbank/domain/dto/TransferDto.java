package by.anpoliakov.tiffbank.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
@Schema(description = "Object for transferring money between users")
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    @Min(0)
    @Schema(description = "Id of the user to whom the transfer should be made")
    private BigInteger creditUserId;
    @Min(1)
    @Schema(description = "Bank transfer amount")
    private BigDecimal transferAmount;
}
