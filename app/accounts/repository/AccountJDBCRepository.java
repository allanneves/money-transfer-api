package accounts.repository;

import com.google.inject.Inject;
import customers.repository.CustomerRepository;
import org.javamoney.moneta.Money;
import org.jooq.DSLContext;
import org.jooq.Record1;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;

import static jooq.tables.Account.ACCOUNT;

public final class AccountJDBCRepository implements AccountRepository {

    private DSLContext jooq;
    private CustomerRepository customerRepository;

    @Inject
    public AccountJDBCRepository(DSLContext jooq, CustomerRepository customerRepository) {
        this.jooq = jooq;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean addAmount(Long accountNumber, Money amount) {
        final int amountAdded = jooq
                .update(ACCOUNT)
                .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.add(amount.getNumber()))
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .execute();

        return amountAdded > 0;
    }

    @Override
    public BigDecimal findBalance(Long accountNumber) {
        final Record1<BigDecimal> balance = jooq
                .select(ACCOUNT.BALANCE)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOne();

        return balance.value1();
    }

    @Override
    public CurrencyUnit findCurrency(Long accountNumber) {
        final Record1<String> currency = jooq
                .select(ACCOUNT.CURRENCY)
                .from(ACCOUNT)
                .where(ACCOUNT.ACCOUNT_NUMBER.equal(accountNumber))
                .fetchOne();

        return Monetary.getCurrency(currency.value1());
    }

    @Override
    public Long findAccountNumber(String nationalId) {
        final Long customerId = customerRepository.findId(nationalId);

        final Record1<Long> accoutNumber = jooq
                .select(ACCOUNT.ACCOUNT_NUMBER)
                .from(ACCOUNT)
                .where(ACCOUNT.CUSTOMER_ID.equal(customerId))
                .fetchOne();

        return accoutNumber.value1();
    }
}
