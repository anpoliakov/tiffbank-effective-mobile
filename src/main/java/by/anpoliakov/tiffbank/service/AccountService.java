package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.TransferRequest;
import by.anpoliakov.tiffbank.domain.entity.Account;
import by.anpoliakov.tiffbank.repository.AccountRepository;
import by.anpoliakov.tiffbank.util.exception.TransferException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AuthService authService;
    private final AccountRepository accountRepo;

    @Transactional
    public void transferMoney(TransferRequest transferRequest) {
        String userLoginFrom = authService.getAuthInfo().getUsername();
        BigInteger userIdTo = transferRequest.getPayeeId();
        BigDecimal transferAmount = transferRequest.getTransferAmount();

        Account accountFrom = accountRepo.findByUserLogin(userLoginFrom).get();
        Account accountTo = accountRepo.findByUserId(userIdTo)
                .orElseThrow(() -> new TransferException("The recipient of the money transfer was not found!"));

        BigDecimal resultSubtract = subtractAccounts(accountFrom.getBalance(), transferAmount);
        accountFrom.setBalance(resultSubtract);
        accountTo.setBalance(accountTo.getBalance().add(transferAmount));
    }

    private BigDecimal subtractAccounts(BigDecimal account, BigDecimal transferAmount) {
        BigDecimal difference = account.subtract(transferAmount);
        if (difference.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransferException("For the transfer, you don't have enough money!");
        }

        return difference;
    }
}
