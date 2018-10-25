/*
 * This file is generated by jOOQ.
 */
package jooq;


import javax.annotation.Generated;

import jooq.tables.Account;
import jooq.tables.Customer;
import jooq.tables.Transfer;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>PUBLIC</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.5"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index PRIMARY_KEY_B = Indexes0.PRIMARY_KEY_B;
    public static final Index PRIMARY_KEY_2 = Indexes0.PRIMARY_KEY_2;
    public static final Index PRIMARY_KEY_4 = Indexes0.PRIMARY_KEY_4;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index PRIMARY_KEY_B = Internal.createIndex("PRIMARY_KEY_B", Account.ACCOUNT, new OrderField[] { Account.ACCOUNT.ID }, true);
        public static Index PRIMARY_KEY_2 = Internal.createIndex("PRIMARY_KEY_2", Customer.CUSTOMER, new OrderField[] { Customer.CUSTOMER.ID }, true);
        public static Index PRIMARY_KEY_4 = Internal.createIndex("PRIMARY_KEY_4", Transfer.TRANSFER, new OrderField[] { Transfer.TRANSFER.ID }, true);
    }
}