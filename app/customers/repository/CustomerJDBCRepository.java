package customers.repository;

import com.google.inject.Inject;
import org.jooq.DSLContext;
import org.jooq.Record1;

import static jooq.tables.Customer.CUSTOMER;

public final class CustomerJDBCRepository implements CustomerRepository {

    private DSLContext jooq;

    @Inject
    public CustomerJDBCRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public Long findId(String nationalId) {
        final Record1<Long> id = jooq
                .select(CUSTOMER.ID)
                .from(CUSTOMER)
                .where(CUSTOMER.NATIONAL_ID.equal(nationalId))
                .fetchOne();

        return id.value1();
    }
}