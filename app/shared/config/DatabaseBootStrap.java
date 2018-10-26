package shared.config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Table;
import org.jooq.impl.SQLDataType;
import play.Logger;
import shared.jooq.JooqClient;

import java.math.BigDecimal;
import java.util.List;

import static jooq.tables.Account.ACCOUNT;
import static jooq.tables.Customer.CUSTOMER;
import static jooq.tables.Transfer.TRANSFER;
import static org.jooq.impl.DSL.constraint;

@Singleton
public final class DatabaseBootStrap {

    private JooqClient jooq;

    @Inject
    public DatabaseBootStrap(JooqClient jooq) {
        this.jooq = jooq;
        bootStrapDatabase();
    }

    private void bootStrapDatabase() {
        createTables();
        populateTables();

        final Result<Record> customers = jooq.client().select().from(getTable("customer")).fetch();
        final Result<Record> account = jooq.client().select().from(getTable("account")).fetch();

        Logger.info(() -> "In-Memory Customers: " + customers.toString());
        Logger.info(() -> "In-Memory Accounts: " + account.toString());
    }

    private void createTables() {
        jooq.client()
                .createTableIfNotExists(CUSTOMER)
                .column(CUSTOMER.ID, SQLDataType.BIGINT.identity(true))
                .column(CUSTOMER.NATIONAL_ID, SQLDataType.VARCHAR.length(15).nullable(false))
                .column(CUSTOMER.FIRST_NAME, SQLDataType.VARCHAR.length(64).nullable(false))
                .column(CUSTOMER.LAST_NAME, SQLDataType.VARCHAR.length(64).nullable(false))
                .constraints(
                        constraint("PK_CUSTOMER").primaryKey("id")
                )
                .execute();

        jooq.client()
                .createTableIfNotExists(ACCOUNT)
                .column(ACCOUNT.ID, SQLDataType.BIGINT.identity(true))
                .column(ACCOUNT.ACCOUNT_NUMBER, SQLDataType.BIGINT.length(9).nullable(false))
                .column(ACCOUNT.CUSTOMER_ID, SQLDataType.BIGINT)
                .column(ACCOUNT.BALANCE, SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(new BigDecimal(0)))
                .column(ACCOUNT.CURRENCY, SQLDataType.VARCHAR(3).nullable(false))
                .constraints(
                        constraint("PK_ACCOUNT").primaryKey("id")
                )
                .execute();

        jooq.client()
                .createTableIfNotExists(TRANSFER)
                .column(TRANSFER.ID, SQLDataType.BIGINT.identity(true))
                .column(TRANSFER.ORIGIN_ACCOUNT, SQLDataType.BIGINT)
                .column(TRANSFER.DESTINATION_ACCOUNT, SQLDataType.BIGINT)
                .column(TRANSFER.AMOUNT, SQLDataType.DECIMAL(10, 2).nullable(false))
                .column(TRANSFER.CURRENCY, SQLDataType.VARCHAR(3).nullable(false))
                .column(TRANSFER.DATE_TIME, SQLDataType.TIMESTAMP(1))
                .column(TRANSFER.TRANSFER_ID, SQLDataType.BIGINT)
                .constraints(
                        constraint("PK_TRANSFER").primaryKey("id")
                )
                .execute();
    }

    private void populateTables() {
        jooq.client()
                .insertInto(CUSTOMER, CUSTOMER.ID, CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.NATIONAL_ID)
                .values(1L, "Michael", "Jackson", "12900MA")
                .values(2L, "George", "Washington", "18933DU")
                .values(3L, "Mikhail", "Koklyaev", "12344CW")
                .execute();

        jooq.client()
                .insertInto(ACCOUNT, ACCOUNT.ID, ACCOUNT.ACCOUNT_NUMBER, ACCOUNT.CUSTOMER_ID, ACCOUNT.BALANCE, ACCOUNT.CURRENCY)
                .values(1L, 432925330L, 1L, new BigDecimal(5945.33), "EUR")
                .values(2L, 928321248L, 2L, new BigDecimal(0), "BRL")
                .values(3L, 538238213L, 3L, new BigDecimal(27.300), "USD")
                .execute();

    }

    private Table<?> getTable(String tableName) {
        final List<Table<?>> tableCandidate = jooq.client().meta().getTables(tableName);
        if (!tableCandidate.isEmpty() && tableCandidate.get(0) != null) {
            return tableCandidate.get(0);
        }
        return null;
    }
}
