package by.anpoliakov.tiffbank.util.validation;

import by.anpoliakov.tiffbank.service.PhoneNumberService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final PhoneNumberService phoneNumberService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null && !value.isEmpty()) {
            return !phoneNumberService.existsByPhoneNumber(value);
        }
        return false;
    }
}
