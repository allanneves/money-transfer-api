package transfers.controller;

import transfers.request.TransferRequestDTO;

import static play.libs.Json.toJson;
import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.POST;

public final class TransferTestUtil {

    private static final String ENDPOINT = "/rest/v1/transfers";

    public static RequestBuilder getTransferRequest(String amount, String origin, String destination, String currency) {
        return new RequestBuilder()
                .method(POST)
                .uri(ENDPOINT)
                .bodyJson(toJson(TransferRequestDTO.builder()
                        .amount(amount)
                        .originAccountId(origin)
                        .destinationAccountId(destination)
                        .currency(currency)
                        .build()));
    }
}
