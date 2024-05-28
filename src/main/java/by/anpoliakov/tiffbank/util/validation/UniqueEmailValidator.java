package by.anpoliakov.tiffbank.util.validation;

import by.anpoliakov.tiffbank.service.EmailService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final EmailService emailService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            return !emailService.existsByEmail(value);
        }
        return false;
    }
}
