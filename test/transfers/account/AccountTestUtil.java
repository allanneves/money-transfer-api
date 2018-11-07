package transfers.account;

import org.jooq.tools.StringUtils;

import static play.mvc.Http.RequestBuilder;
import static play.test.Helpers.GET;

public final class AccountTestUtil {

    private static final String ENDPOINT = "/rest/v1/accounts/{accountNumber}/balance";

    public static RequestBuilder getAccountBalance(String accountNumber) {
        final String endpoint = StringUtils.replace(ENDPOINT, "{accountNumber}", accountNumber);
        return new RequestBuilder()
                .method(GET)
                .uri(endpoint);
    }
}
