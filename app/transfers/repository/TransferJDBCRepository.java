package transfers.repository;

import com.google.inject.Inject;
import jooq.tables.records.TransferRecord;
import shared.jooq.JooqClient;
import transfers.model.Transfer;

import java.math.BigDecimal;

import static jooq.tables.Transfer.TRANSFER;

public final class TransferJDBCRepository implements TransferRepository {

    private JooqClient jooq;

    @Inject
    public TransferJDBCRepository(JooqClient jooq) {
        this.jooq = jooq;
    }

    @Override
    public void create(Transfer transferCandidate) {
        final Long origin = transferCandidate.getOriginAccountId();
        final Long destination = transferCandidate.getDestinationAccountId();
        final BigDecimal amount = transferCandidate.getAmount().getNumberStripped();
        final String currency = transferCandidate.getAmount().getCurrency().getCurrencyCode();
        final Integer transferId = transferCandidate.getTransferId();

        TransferRecord transfer = jooq.client().newRecord(TRANSFER);
        transfer.setOriginAccount(origin);
        transfer.setDestinationAccount(destination);
        transfer.setAmount(amount);
        transfer.setCurrency(currency);

        transfer.store();
    }
}
