package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.TransferDto;
import by.anpoliakov.tiffbank.domain.entity.Account;
import by.anpoliakov.tiffbank.domain.entity.User;
import by.anpoliakov.tiffbank.filter.JwtAuthentication;
import by.anpoliakov.tiffbank.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepo;

    @Mock
    AuthService authService;

    @InjectMocks
    AccountService accountService;

    @Test
    @DisplayName("Correct transfer money")
    void transferMoney_ifValidData_transfer() {
        //given
        TransferDto transferDto = new TransferDto(new BigInteger("2"), new BigDecimal("100"));
        Account debitAccount = new Account(
                new User("TestLogin", "TestPassword", "TestFullName", LocalDate.now(), new BigDecimal("100")),
                new BigDecimal("200"));
        Account creditAccount = new Account(
                new User("TestLogin2", "TestPassword2", "TestFullName2", LocalDate.now(), new BigDecimal("100")),
                new BigDecimal("300"));
        JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setUsername("TestLogin");

        when(authService.getAuthInfo()).thenReturn(jwtAuthentication);
        when(accountRepo.findByUserLogin(any())).thenReturn(Optional.of(debitAccount));
        when(accountRepo.findByUserId(any(BigInteger.class))).thenReturn(Optional.of(creditAccount));

        //when
        accountService.transferFunds(transferDto);

        //then
        assertEquals(BigDecimal.valueOf(100), debitAccount.getBalance());
        assertEquals(BigDecimal.valueOf(400), creditAccount.getBalance());
    }
}