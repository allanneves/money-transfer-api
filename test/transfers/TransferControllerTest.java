package transfers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Http.RequestBuilder;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import transfers.request.TransferRequestDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static play.libs.Json.toJson;
import static play.mvc.Http.Status.CREATED;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;

public class TransferControllerTest extends WithApplication {

    @Test
    public void transferMoney_whenDataIsValid_ShouldReturnCreated() {
        final Result performTransfer = Helpers.route(this.app,
                new RequestBuilder()
                        .method(POST)
                        .uri("/rest/v1/transfers")
                        .bodyJson(toJson(TransferRequestDTO.builder()
                                .amount("330.0")
                                .originAccountId("12")
                                .destinationAccountId("144")
                                .currency("USD")
                                .build())));

        final JsonNode responseContent = Json.parse(contentAsString(performTransfer));

        assertThat(performTransfer.status()).isEqualTo(CREATED);
        responseContent.has("status");
        assertThat(responseContent.get("status").textValue()).isEqualTo("OK");
    }
}
