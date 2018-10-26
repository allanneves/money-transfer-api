package transfers.controller;

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
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Http.Status.CREATED;
import static play.test.Helpers.POST;
import static play.test.Helpers.contentAsString;

public class TransferControllerTest extends WithApplication {

    private static final String AMOUNT = "330.50";
    private static final String ORIGIN_ACC = "432925330";
    private static final String DESTINATION_ACC = "538238213";
    private static final String CURRENCY = "USD";

    @Test
    public void transferMoney_whenDataIsValid_ShouldReturnCreated() {
        final Result performTransfer = Helpers.route(this.app,
                new RequestBuilder()
                        .method(POST)
                        .uri("/rest/v1/transfers")
                        .bodyJson(toJson(TransferRequestDTO.builder()
                                .amount(AMOUNT)
                                .originAccountId(ORIGIN_ACC)
                                .destinationAccountId(DESTINATION_ACC)
                                .currency(CURRENCY)
                                .build())));

        final JsonNode responseContent = Json.parse(contentAsString(performTransfer));

        assertThat(performTransfer.status()).isEqualTo(CREATED);
        responseContent.has("status");
        assertThat(responseContent.get("status").textValue()).isEqualTo("SUCCESS");
    }

    @Test
    public void transferMoney_whenCurrencyIsInvalid_ShouldReturnBadRequest() {
        final Result performTransfer = Helpers.route(this.app,
                new RequestBuilder()
                        .method(POST)
                        .uri("/rest/v1/transfers")
                        .bodyJson(toJson(TransferRequestDTO.builder()
                                .amount(AMOUNT)
                                .originAccountId(ORIGIN_ACC)
                                .destinationAccountId(DESTINATION_ACC)
                                .currency("foo")
                                .build())));

        assertThat(performTransfer.status()).isEqualTo(BAD_REQUEST);
    }

}
