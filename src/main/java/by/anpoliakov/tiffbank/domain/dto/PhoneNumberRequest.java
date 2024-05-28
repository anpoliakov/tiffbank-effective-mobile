package by.anpoliakov.tiffbank.domain.dto;

import by.anpoliakov.tiffbank.util.validation.UniquePhoneNumber;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class PhoneNumberRequest {
    @NonNull
    @Pattern(regexp = "\\+[0-9]{12}", message = "phone number should be format like +375291112233")
    @UniquePhoneNumber
    private String phoneNumber;
}
