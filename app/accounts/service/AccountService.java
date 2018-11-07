package accounts.service;

import com.google.inject.ImplementedBy;

import java.math.BigDecimal;

@ImplementedBy(AccountInformation.class)
public interface AccountService {

    BigDecimal accountBalance(String accountNumber);
}
