package accounts.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public final class Account {

    private Long id;
    private Double accountNumber;
    private BigDecimal balance;
    private String currency;
}
