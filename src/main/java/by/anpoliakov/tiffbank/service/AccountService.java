package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.dto.TransferDto;
import by.anpoliakov.tiffbank.domain.entity.Account;
import by.anpoliakov.tiffbank.repository.AccountRepository;
import by.anpoliakov.tiffbank.util.exception.TransferException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {
    private final AuthService authService;
    private final AccountRepository accountRepo;

    @Transactional
    public void transferFunds(TransferDto transferDto) {
        Account debitAccount = accountRepo.findByUserLogin(authService.getAuthInfo().getUsername())
                .orElseThrow(() -> new TransferException("Invalid user for debiting money"));
        Account creditAccount = accountRepo.findByUserId(transferDto.getCreditUserId())
                .orElseThrow(() -> new TransferException("The recipient of the money transfer was not found!"));
        BigDecimal transferAmount = transferDto.getTransferAmount();

        BigDecimal finalBalanceDebitAccount = checkBalanceAfterDebit(debitAccount.getBalance(), transferAmount);
        debitAccount.setBalance(finalBalanceDebitAccount);
        creditAccount.setBalance(creditAccount.getBalance().add(transferAmount));
    }

    @Transactional
    public void save(Account account){
        accountRepo.save(account);
    }

    private BigDecimal checkBalanceAfterDebit(BigDecimal debitAccount, BigDecimal transferAmount) {
        BigDecimal finalBalance = debitAccount.subtract(transferAmount);
        if (finalBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransferException("For the transfer, you don't have enough money!");
        }

        return finalBalance;
    }
}
