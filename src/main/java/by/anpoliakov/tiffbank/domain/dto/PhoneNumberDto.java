package by.anpoliakov.tiffbank.domain.dto;

import by.anpoliakov.tiffbank.util.validation.UniquePhoneNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Object for transferring the phone number to the server side")
public class PhoneNumberDto {
    @NonNull
    @Pattern(regexp = "\\+[0-9]{12}", message = "phone number should be format like +375291112233")
    @UniquePhoneNumber
    @Schema(description = "Adding or updating a phone number (it must not be occupied by anyone)", example = "+375291113355")
    private String phoneNumber;
}
