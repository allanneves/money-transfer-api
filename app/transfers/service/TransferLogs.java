package transfers.service;

import accounts.model.Account;
import accounts.repository.AccountRepository;
import com.google.inject.Inject;
import org.javamoney.moneta.Money;
import transfers.model.Transfer;
import transfers.model.TransferLog;
import transfers.repository.TransferRepository;

import java.math.BigDecimal;
import java.util.Random;

public final class TransferLogs implements TransferLogService {

    private TransferRepository transferRepository;
    private AccountRepository accountRepository;

    @Inject
    public TransferLogs(TransferRepository transferRepository, AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public TransferLog log(Transfer transfer, String status) {
        persist(transfer);
        final Long originAccountId = transfer.getOriginAccountId();
        final Long destinationAccountId = transfer.getDestinationAccountId();
        final Account origin = accountRepository.findAccount(originAccountId);
        final Account destination = accountRepository.findAccount(destinationAccountId);
        final BigDecimal amount = transfer.getAmount().getNumberStripped();
        final String currencyCode = transfer.getAmount().getCurrency().getCurrencyCode();

        return TransferLog.builder()
                .status(status)
                .originAccount(origin)
                .destinationAccount(destination)
                .amount(amount)
                .currency(currencyCode)
                .build();
    }

    private void persist(Transfer transfer) {
        transfer.setTransferId(new Random().nextInt());
        transferRepository.create(transfer);
    }
}
