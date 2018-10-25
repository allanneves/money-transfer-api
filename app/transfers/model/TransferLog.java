package transfers.model;

import accounts.model.Account;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static play.libs.Json.toJson;

@Getter
@Setter
@Builder
public final class TransferLog {

    private String status;
    private Account originAccount;
    private Account destinationAccount;
    private BigDecimal amount;
    private String currency;

    public JsonNode asJson() {
        return toJson(this);
    }
}
