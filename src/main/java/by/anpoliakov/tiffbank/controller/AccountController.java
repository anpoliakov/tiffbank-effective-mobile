package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.TransferDto;
import by.anpoliakov.tiffbank.service.AccountService;
import by.anpoliakov.tiffbank.util.validation.MessagePreparer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/account")
@RequiredArgsConstructor
@Tag(name="Account Controller", description="Provides endpoints of bank account management")
public class AccountController {
    private final AccountService accountService;
    private final MessagePreparer messagePreparer;

    @PostMapping("transfer")
    @Operation(
            summary = "Transfer money",
            description = "Specify to which account and what amount of money to transfer"
    )
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<HttpStatus> transferMoney(@RequestBody @Valid TransferDto transferDto,
                                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(messagePreparer.prepareErrorMessage(bindingResult));
        }

        accountService.transferFunds(transferDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
