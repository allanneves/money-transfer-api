package accounts.service;

import accounts.repository.AccountRepository;
import com.google.inject.Inject;

import java.math.BigDecimal;

public class AccountInformation implements AccountService {

    private AccountRepository accountRepository;

    @Inject
    public AccountInformation(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public BigDecimal accountBalance(String accountNumber) {
        return accountRepository.findBalance(Long.decode(accountNumber));
    }
}
