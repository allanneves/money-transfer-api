package transfers.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static play.libs.Json.fromJson;
import static play.mvc.Http.Request;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class TransferRequestDTO {

    private String originAccountId;
    private String destinationAccountId;
    private String amount;
    private String currency;

    public static TransferRequestDTO getFromRequest(Request request) {
        return fromJson(request.body().asJson(), TransferRequestDTO.class);
    }
}
