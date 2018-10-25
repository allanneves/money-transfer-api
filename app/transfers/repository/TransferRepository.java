package transfers.repository;

import com.google.inject.ImplementedBy;
import transfers.model.Transfer;

@ImplementedBy(TransferJDBCRepository.class)
public interface TransferRepository {

    void create(Transfer transfer);
}
