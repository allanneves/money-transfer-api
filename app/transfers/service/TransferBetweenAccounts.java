package transfers.service;

import accounts.repository.AccountRepository;
import com.google.inject.Inject;
import org.javamoney.moneta.Money;
import shared.exceptions.BadRequestException;
import transfers.model.Transfer;

public final class TransferBetweenAccounts implements TransferService {

    private AccountRepository accountRepository;

    @Inject
    public TransferBetweenAccounts(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transfer perform(Transfer transfer) {
        validateAccounts(transfer);
        doTransfer(transfer);
        return transfer;
    }

    private void validateAccounts(Transfer transfer) {
        final Long originAccount = transfer.getOriginAccountId();
        final Long destinationAccount = transfer.getDestinationAccountId();

        if (!accountRepository.accountExists(originAccount)) {
            throw new BadRequestException("origin account does not exist");
        }

        if (!accountRepository.accountExists(destinationAccount)) {
            throw new BadRequestException("destination account does not exist");
        }
    }

    private void doTransfer(Transfer transfer) {
        final Long destination = transfer.getDestinationAccountId();
        final Long origin = transfer.getOriginAccountId();
        final Money amount = transfer.getAmount();
        accountRepository.addAmount(destination, amount);
        accountRepository.removeAmount(origin, amount);
    }
}
