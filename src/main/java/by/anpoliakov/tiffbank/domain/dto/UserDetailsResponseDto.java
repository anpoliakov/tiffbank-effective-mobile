package by.anpoliakov.tiffbank.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class UserDetailsResponseDto {
    private String login;
    private String fullName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthday;
}
