package transfers.service;

import com.google.inject.ImplementedBy;
import transfers.model.Transfer;
import transfers.model.TransferLog;

@ImplementedBy(TransferLogs.class)
public interface TransferLogService {

    enum Status {
        SUCCESS, FAILURE
    }

    TransferLog log(Transfer transfer, String status);
}
