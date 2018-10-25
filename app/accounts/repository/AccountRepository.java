package accounts.repository;

import accounts.model.Account;
import com.google.inject.ImplementedBy;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

@ImplementedBy(AccountJDBCRepository.class)
public interface AccountRepository {

    boolean addAmount(Long accountNumber, Money amount);

    boolean removeAmount(Long accountNumber, Money amount);

    BigDecimal findBalance(Long accountNumber);

    CurrencyUnit findCurrency(Long accountNumber);

    Long findAccountNumber(String nationalId);

    boolean accountExists(Long accountNumber);

    Account findAccount(Long accountNumber);
}
