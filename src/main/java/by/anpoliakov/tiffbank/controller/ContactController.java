package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.EmailRequest;
import by.anpoliakov.tiffbank.domain.dto.PhoneNumberRequest;
import by.anpoliakov.tiffbank.service.EmailService;
import by.anpoliakov.tiffbank.service.PhoneNumberService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/contacts")
@RequiredArgsConstructor
public class ContactController {
    private final PhoneNumberService phoneNumberService;
    private final EmailService emailService;

    @PostMapping("phone")
    public ResponseEntity<HttpStatus> addPhoneNumber(@RequestBody @Valid PhoneNumberRequest phoneNumber,
                                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(prepareErrorMessage(bindingResult));
        }
        phoneNumberService.addPhoneNumber(phoneNumber);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("email")
    public ResponseEntity<HttpStatus> addEmail(@RequestBody @Valid EmailRequest emailRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(prepareErrorMessage(bindingResult));
        }
        emailService.addEmail(emailRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("phone/{id}")
    public ResponseEntity<HttpStatus> deletePhoneNumber(@PathVariable @Min(0) int id) {
        phoneNumberService.deletePhoneNumberById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("email/{id}")
    public ResponseEntity<HttpStatus> deleteEmail(@PathVariable @Min(0) int id) {
        emailService.deleteEmailById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("phone/{id}")
    public ResponseEntity<HttpStatus> updatePhoneNumber(@PathVariable @Min(0) int id,
                                                        @RequestBody @Valid PhoneNumberRequest phoneNumber,
                                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(prepareErrorMessage(bindingResult));
        }
        phoneNumberService.updatePhoneNumber(id, phoneNumber);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("email/{id}")
    public ResponseEntity<HttpStatus> updateEmail(@PathVariable @Min(0) int id,
                                                  @RequestBody @Valid EmailRequest emailRequest,
                                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(prepareErrorMessage(bindingResult));
        }
        emailService.updateEmail(id, emailRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private String prepareErrorMessage(BindingResult bindingResult){
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append("; ");
        }

        return errorMsg.toString();
    }
}
