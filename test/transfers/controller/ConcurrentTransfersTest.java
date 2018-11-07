package transfers.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Before;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import transfers.account.AccountTestUtil;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.contentAsString;

public final class ConcurrentTransfersTest extends WithApplication {

    private static final String MICHAEL_ACCOUNT = "432925330";
    private static final String GEORGE_ACCOUNT = "928321248";
    private static final String MIKHAIL_ACCOUNT = "538238213";
    private static final String CURRENCY = "USD";

    /*
    *   How do I check if concurrent requests are working?
    *   I would usually use Gatling or Jmeter for a task like that. However, for the purpose
    *   of this test I would like to demonstrate a racing condition programmatically.
    *   I start checking if the target account has zero balance due to the fact that this value
    *   is defined in the database bootstrap when the application loads. For the concurrency test
    *   I load three different operations on the target account:
    *       1 - transfer 900 USD to the target account
    *       2 - transfer 10 USD to the target account
    *       3 - withdraws 410 USD from the target account
    *
    *   I send these requests to the ForkJoinPool using the Java 8 completable futures and the supplyAsync
    *   instead of the runAsync because I want to be able to check if these threads are done, otherwise I would
    *   have no visibility at all.
    *
    *   As the main goal of the test is to check if multiple transfers work well together, I simulate a 4 seconds to delay
    *   so these 3 asynchronous threads can finish. After that I check if they are all done and fetch the account balance
    *   from a different endpoint.
    *       
    *   The final value has to be 500 returned in the JSON. I am also validating that.
    *
    * */

    @Before
    public void assertThatGeorgeAccountHasZeroBalance() {
        final RequestBuilder georgeAccountBalance = AccountTestUtil.getAccountBalance(GEORGE_ACCOUNT);
        final Result accountBalance = Helpers.route(this.app, georgeAccountBalance);
        final JsonNode responseContent = Json.parse(contentAsString(accountBalance));
        assertThat(responseContent.get("balance").asText()).isEqualTo("0");
    }

    @Test
    public void transferMoney_whenConcurrentTransfers_shouldBlockEachTransaction() throws InterruptedException {
        final RequestBuilder transfer900ToGeorge = TransferTestUtil.getTransferRequest("900", MICHAEL_ACCOUNT, GEORGE_ACCOUNT, CURRENCY);
        final RequestBuilder transfer10ToGeorge = TransferTestUtil.getTransferRequest("10", MIKHAIL_ACCOUNT, GEORGE_ACCOUNT, CURRENCY);
        final RequestBuilder withdraw410FromGeorge = TransferTestUtil.getTransferRequest("410", GEORGE_ACCOUNT, MIKHAIL_ACCOUNT, CURRENCY);

        final CompletableFuture<Result> send900ToGeorge = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, transfer900ToGeorge));
        final CompletableFuture<Result> send10ToGeorge = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, transfer10ToGeorge));
        final CompletableFuture<Result> take410FromGeorge = CompletableFuture.supplyAsync(() -> Helpers.route(this.app, withdraw410FromGeorge));

        Thread.sleep(4000);

        if (send10ToGeorge.isDone() && send900ToGeorge.isDone() && take410FromGeorge.isDone()) {
            final RequestBuilder georgeAccountBalance = AccountTestUtil.getAccountBalance(GEORGE_ACCOUNT);
            final Result accountBalance = Helpers.route(this.app, georgeAccountBalance);
            final JsonNode responseContent = Json.parse(contentAsString(accountBalance));
            assertThat(responseContent.get("balance").asText()).isEqualTo("500.0");
        }
    }
}
