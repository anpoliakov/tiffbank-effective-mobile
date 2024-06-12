package by.anpoliakov.tiffbank.service;

import by.anpoliakov.tiffbank.domain.entity.Account;
import by.anpoliakov.tiffbank.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AccountsInterestLoaderSchedulerService {
    private final AccountService accountService;
    private final UserService userService;
    private final BigDecimal maxAccountRate;
    private final BigDecimal accountGrowthRate;

    @Autowired
    public AccountsInterestLoaderSchedulerService(@Value("${max.account.rate}") int maxAccountRate,
                                                  @Value("${account.growth.rate}") int accountGrowthRate,
                                                  AccountService accountService,
                                                  UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
        this.maxAccountRate = new BigDecimal(maxAccountRate / 100.0);
        this.accountGrowthRate = new BigDecimal(accountGrowthRate / 100.0);
    }

    @Scheduled(cron = "${cron.interest.accrual.time}", zone = "Europe/Moscow")
    public final void updateAccountEntities() {
        List<User> users = userService.findAll();

        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                BigDecimal maxValue = user.getInitialDeposit().multiply(maxAccountRate); //нач счёт * 2,07
                Account account = user.getAccount();
                BigDecimal currentValue = account.getBalance();

                BigDecimal increasedValue = currentValue.add(currentValue.multiply(accountGrowthRate));
                if (increasedValue.compareTo(maxValue) <= 0) {
                    //попадаём в метод если значение текущего счёта не превышает максимальное значение с процентами
                    account.setBalance(increasedValue);
                    accountService.save(account);
                }
            }
        }
    }
}
