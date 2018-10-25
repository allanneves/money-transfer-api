import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.codegen.GenerationTool;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import play.Logger;
import play.api.db.Database;

import java.math.BigDecimal;
import java.util.List;

import static org.jooq.impl.DSL.constraint;

@Singleton
public final class DatabaseBootStrap {

    private Database database;
    private DSLContext jooq;

    @Inject
    public DatabaseBootStrap(Database database) {
        this.database = database;
        bootStrapDatabase();
        createClasses();
    }

    private void createClasses() {
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.h2.Driver")
                        .withUrl("jdbc:h2:mem:play")
                        .withUser("sa")
                        .withPassword("sa"))
                .withGenerator(new Generator()
                        .withDatabase(new org.jooq.meta.jaxb.Database()
                                .withName("org.jooq.meta.h2.H2Database")
                                .withIncludes(".*")
                                .withExcludes("")
                                .withInputSchema("PUBLIC"))
                        .withTarget(new Target()
                                .withPackageName("jooq")
                                .withDirectory("app/")));

        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            Logger.error(() -> "Could not generate database table classes");
            System.exit(1);
        }
    }

    private void bootStrapDatabase() {
        jooq = DSL.using(database.getConnection(), SQLDialect.H2);
        createTables();
        populateTables();

        final List<Table<?>> tables = jooq.meta().getTables();
        final Result<Record> customers = jooq.select().from(getTable("customer")).fetch();
        final Result<Record> account = jooq.select().from(getTable("account")).fetch();

        Logger.info(tables::toString);
        Logger.info(customers::toString);
        Logger.info(account::toString);
    }

    private void createTables() {
        jooq.createTable("customer")
                .column("id", SQLDataType.BIGINT.identity(true))
                .column("national_id", SQLDataType.VARCHAR.length(15).nullable(false))
                .column("first_name", SQLDataType.VARCHAR.length(64).nullable(false))
                .column("last_name", SQLDataType.VARCHAR.length(64).nullable(false))
                .constraints(
                        constraint("PK_CUSTOMER").primaryKey("id")
                )
                .execute();

        jooq.createTable("account")
                .column("id", SQLDataType.BIGINT.identity(true))
                .column("account_number", SQLDataType.BIGINT.length(9).nullable(false))
                .column("customer_id", SQLDataType.BIGINT)
                .column("balance", SQLDataType.DECIMAL(10, 2).nullable(false).defaultValue(new BigDecimal(0)))
                .column("currency", SQLDataType.VARCHAR(3).nullable(false))
                .constraints(
                        constraint("PK_ACCOUNT").primaryKey("id")
                )
                .execute();

        jooq.createTable("transfer")
                .column("id", SQLDataType.BIGINT.identity(true))
                .column("origin_account", SQLDataType.BIGINT)
                .column("destination_account", SQLDataType.BIGINT)
                .column("amount", SQLDataType.DECIMAL(10, 2).nullable(false))
                .column("currency", SQLDataType.VARCHAR(3).nullable(false))
                .column("date_time", SQLDataType.TIMESTAMP(1))
                .constraints(
                        constraint("PK_TRANSFER").primaryKey("id")
                )
                .execute();
    }

    private void populateTables() {
        final Table<?> customerTable = getTable("customer");
        final Field<Long> customerId = (Field<Long>) customerTable.field("id");
        final Field<String> first_name = (Field<String>) customerTable.field("first_name");
        final Field<String> last_name = (Field<String>) customerTable.field("last_name");
        final Field<String> nationalId = (Field<String>) customerTable.field("national_id");
        jooq.insertInto(customerTable, customerId, first_name, last_name, nationalId)
                .values(1L, "Michael", "Jackson", "12900MA")
                .values(2L, "George", "Washington", "18933DU")
                .values(3L, "Mikhail", "Koklyaev", "12344CW")
                .execute();

        final Table<?> accountTable = getTable("account");
        final Field<Long> accountId = (Field<Long>) accountTable.field("id");
        final Field<Long> accountNumber = (Field<Long>) accountTable.field("account_number");
        final Field<Integer> fkCustomerId = (Field<Integer>) accountTable.field("customer_id");
        final Field<BigDecimal> balance = (Field<BigDecimal>) accountTable.field("balance");
        final Field<String> currency = (Field<String>) accountTable.field("currency");
        jooq.insertInto(accountTable, accountId, accountNumber, fkCustomerId, balance, currency)
                .values(1L, 432925330L, 1, new BigDecimal(5945.33), "EUR")
                .values(2L, 928321248L, 2, new BigDecimal(0), "BRL")
                .values(3L, 538238213L, 3, new BigDecimal(27.300), "USD")
                .execute();

    }

    private Table<?> getTable(String tableName) {
        final List<Table<?>> tableCandidate = jooq.meta().getTables(tableName);
        if (!tableCandidate.isEmpty() && tableCandidate.get(0) != null) {
            return tableCandidate.get(0);
        }
        return null;
    }
}
