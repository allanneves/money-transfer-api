package transfers.repository;

import com.google.inject.Inject;
import jooq.tables.records.TransferRecord;
import org.jooq.DSLContext;
import transfers.model.Transfer;

import java.math.BigDecimal;

import static jooq.tables.Transfer.TRANSFER;

public final class TransferJDBCRepository implements TransferRepository {

    private DSLContext jooq;

    @Inject
    public TransferJDBCRepository(DSLContext jooq) {
        this.jooq = jooq;
    }

    @Override
    public void create(Transfer transferCandidate) {
        final Long origin = transferCandidate.getOriginAccountId();
        final Long destination = transferCandidate.getDestinationAccountId();
        final BigDecimal amount = transferCandidate.getAmount().getNumberStripped();
        final String currency = transferCandidate.getAmount().getCurrency().getCurrencyCode();

        TransferRecord transfer = jooq.newRecord(TRANSFER);
        transfer.setOriginAccount(origin);
        transfer.setDestinationAccount(destination);
        transfer.setAmount(amount);
        transfer.setCurrency(currency);

        transfer.store();
    }
}
