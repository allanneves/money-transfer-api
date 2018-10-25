package accounts.repository;

import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;

public interface AccountRepository {

    boolean addAmount(Long accountNumber, Money amount);

    BigDecimal findBalance(Long accountNumber);

    CurrencyUnit findCurrency(Long accountNumber);

    Long findAccountNumber(String nationalId);
}
