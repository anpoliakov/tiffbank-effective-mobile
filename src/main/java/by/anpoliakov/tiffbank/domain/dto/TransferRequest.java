package by.anpoliakov.tiffbank.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
public class TransferRequest {
    @Min(0)
    private BigInteger payeeId;
    @Min(1)
    private BigDecimal transferAmount;
}
