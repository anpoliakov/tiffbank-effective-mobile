package by.anpoliakov.tiffbank.domain.dto;

import by.anpoliakov.tiffbank.util.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest {
    @NotEmpty(message = "email should not be empty")
    @Size(min = 2, max = 50, message = "email should be between 2 and 50 characters")
    @Email(message = "email should be right format")
    @UniqueEmail
    private String email;
}
