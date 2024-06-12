package by.anpoliakov.tiffbank.domain.dto;

import by.anpoliakov.tiffbank.util.validation.UniqueEmail;
import by.anpoliakov.tiffbank.util.validation.UniqueLogin;
import by.anpoliakov.tiffbank.util.validation.UniquePhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@Schema(description = "Object for transferring user data to the server side ")
public class UserDetailsRequestDto {
    @NotEmpty(message = "login should not be empty")
    @Size(min = 2, max = 50, message = "login should be between 2 and 50 characters")
    @UniqueLogin
    private String login;

    @NotEmpty(message = "password should not be empty")
    @Size(min = 2, max = 50, message = "password should be between 2 and 50 characters")
    private String password;

    @NotEmpty(message = "fullName should not be empty")
    @Size(min = 2, max = 100, message = "fullName should be between 2 and 100 characters")
    private String fullName;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate birthday;

    @NotEmpty(message = "phoneNumber should not be empty")
    @Pattern(regexp = "\\+[0-9]{12}", message = "phone number should be format like +375293247711")
    @UniquePhoneNumber
    private String phoneNumber;

    @NotEmpty(message = "email should not be empty")
    @Size(min = 2, max = 50, message = "email should be between 2 and 50 characters")
    @Email(message = "email should be right format")
    @UniqueEmail
    private String email;

    @Positive(message = "The starting account balance should be > 1!")
    @Min(1)
    private BigDecimal balance;
}
