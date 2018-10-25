package transfers.service;

import com.google.inject.ImplementedBy;
import transfers.model.Transfer;

@ImplementedBy(TransferBetweenAccounts.class)
public interface TransferService {
    Transfer perform(Transfer transfer);
}
