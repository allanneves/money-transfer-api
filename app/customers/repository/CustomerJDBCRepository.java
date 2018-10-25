package customers.repository;

import com.google.inject.Inject;
import org.jooq.Record1;
import shared.jooq.JooqClient;

import static jooq.tables.Customer.CUSTOMER;

public final class CustomerJDBCRepository implements CustomerRepository {

    private JooqClient jooq;

    @Inject
    public CustomerJDBCRepository(JooqClient jooq) {
        this.jooq = jooq;
    }

    @Override
    public Long findId(String nationalId) {
        final Record1<Long> id = jooq.client()
                .select(CUSTOMER.ID)
                .from(CUSTOMER)
                .where(CUSTOMER.NATIONAL_ID.equal(nationalId))
                .fetchOne();

        return id.value1();
    }
}