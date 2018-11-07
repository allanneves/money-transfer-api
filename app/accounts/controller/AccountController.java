package accounts.controller;

import accounts.service.AccountService;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;

import static play.libs.Json.toJson;

@Slf4j
public class AccountController extends Controller {

    private AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Result checkBalance(String accountNumber) {
        final HashMap<Object, Object> wrapper = new HashMap<>();
        wrapper.put("balance", accountService.accountBalance(accountNumber));
        return ok(toJson(wrapper));
    }
}
