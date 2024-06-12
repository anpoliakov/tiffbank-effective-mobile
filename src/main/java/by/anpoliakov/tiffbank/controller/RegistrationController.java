package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.UserDetailsRequestDto;
import by.anpoliakov.tiffbank.service.RegistrationService;
import by.anpoliakov.tiffbank.util.validation.MessagePreparer;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
@Tag(name="Registration Controller", description="Provides an endpoint for registering a new user")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final MessagePreparer messagePreparer;

    @PostMapping
    public HttpStatus register(@RequestBody @Valid UserDetailsRequestDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }

        registrationService.saveUserData(user);
        return HttpStatus.OK;
    }
}
