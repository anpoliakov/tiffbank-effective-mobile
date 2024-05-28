package by.anpoliakov.tiffbank.controller;

import by.anpoliakov.tiffbank.domain.dto.TransferRequest;
import by.anpoliakov.tiffbank.service.AccountService;
import by.anpoliakov.tiffbank.util.exception.AccountException;
import jakarta.validation.Valid;
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
@RequestMapping("api/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<HttpStatus> transferMoney(@RequestBody @Valid TransferRequest transferRequest,
                                                    BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new AccountException(errorMsg.toString());
        }

        accountService.transferMoney(transferRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
