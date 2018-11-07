package transfers.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Transfer {

    public enum Status {
        SUCCESS, FAILURE;
    }

    private Integer transferId;
    private Long originAccountId;
    private Long destinationAccountId;
    private Money amount;
    private LocalDateTime timestamp;
    private Status status;
}
