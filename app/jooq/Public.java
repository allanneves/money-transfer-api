/*
 * This file is generated by jOOQ.
 */
package jooq;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.tables.Account;
import jooq.tables.Customer;
import jooq.tables.Transfer;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = -2115507505;

    /**
     * The reference instance of <code>PUBLIC</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>PUBLIC.account</code>.
     */
    public final Account ACCOUNT = jooq.tables.Account.ACCOUNT;

    /**
     * The table <code>PUBLIC.customer</code>.
     */
    public final Customer CUSTOMER = jooq.tables.Customer.CUSTOMER;

    /**
     * The table <code>PUBLIC.transfer</code>.
     */
    public final Transfer TRANSFER = jooq.tables.Transfer.TRANSFER;

    /**
     * No further instances allowed
     */
    private Public() {
        super("PUBLIC", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Account.ACCOUNT,
            Customer.CUSTOMER,
            Transfer.TRANSFER);
    }
}
