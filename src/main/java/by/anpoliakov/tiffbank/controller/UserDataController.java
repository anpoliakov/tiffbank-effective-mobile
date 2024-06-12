package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.EmailDto;
import by.anpoliakov.tiffbank.domain.dto.PhoneNumberDto;
import by.anpoliakov.tiffbank.service.EmailService;
import by.anpoliakov.tiffbank.service.PhoneNumberService;
import by.anpoliakov.tiffbank.util.validation.MessagePreparer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/userData")
@RequiredArgsConstructor
@Tag(name="User data Controller", description="Provides endpoints to update/delete/add user data")
public class UserDataController {
    private final PhoneNumberService phoneNumberService;
    private final EmailService emailService;
    private final MessagePreparer messagePreparer;

    @PostMapping("phone")
    @Operation(
            summary = "Add phone number",
            description = "Adds a phone number to the current user, the phone number must be unique"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> addPhoneNumber(@RequestBody @Valid PhoneNumberDto phoneNumber,
                                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }
        phoneNumberService.addPhoneNumber(phoneNumber);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("email")
    @Operation(
            summary = "Add email",
            description = "Adds email address to the current user, email must be unique"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> addEmail(@RequestBody @Valid EmailDto emailDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }
        emailService.addEmail(emailDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("phone/{id}")
    @Operation(
            summary = "Delete phone number",
            description = "Deletes a phone number by specific id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> deletePhoneNumber(@PathVariable @Min(0) int id) {
        phoneNumberService.deletePhoneNumberById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("email/{id}")
    @Operation(
            summary = "Delete email address",
            description = "Deletes a email address by specific id"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> deleteEmail(@PathVariable @Min(0) int id) {
        emailService.deleteEmailById(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("phone/{id}")
    @Operation(
            summary = "Update phone number",
            description = "Update a phone number by specific id (the request body contains DTO)"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> updatePhoneNumber(@PathVariable @Min(0) int id,
                                                        @RequestBody @Valid PhoneNumberDto phoneNumber,
                                                        BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }
        phoneNumberService.updatePhoneNumber(id, phoneNumber);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("email/{id}")
    @Operation(
            summary = "Update email address",
            description = "Update a email address by specific id (the request body contains DTO)"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> updateEmail(@PathVariable @Min(0) int id,
                                                  @RequestBody @Valid EmailDto emailDto,
                                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }
        emailService.updateEmail(id, emailDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
