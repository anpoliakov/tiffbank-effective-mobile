package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.UserAccountRequest;
import by.anpoliakov.tiffbank.service.RegistrationService;
import by.anpoliakov.tiffbank.util.exception.UserNotCreatedException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid UserAccountRequest user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new ValidationException(errorMsg.toString());
        }

        registrationService.saveUserData(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
