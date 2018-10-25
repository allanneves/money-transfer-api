package transfers.request;

import shared.exceptions.BadRequestException;
import org.javamoney.moneta.Money;
import transfers.model.Transfer;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.UnknownCurrencyException;
import java.math.BigDecimal;

import static org.apache.commons.lang3.StringUtils.isBlank;

public final class TransferRequestMapper {

    public Transfer transform(TransferRequestDTO transferRequestDTO) {
        final Long origin = parseOriginAccountId(transferRequestDTO);
        final Long destination = parseDestinationAccountId(transferRequestDTO);
        final Money amount = parseTransferAmount(transferRequestDTO);

        return Transfer.builder()
                .originAccountId(origin)
                .destinationAccountId(destination)
                .amount(amount)
                .build();
    }

    private Long parseOriginAccountId(TransferRequestDTO transferRequestDTO) {
        final String originAccountCandidate = transferRequestDTO.getOriginAccountId();
        return parseAccountId(originAccountCandidate);
    }

    private Long parseDestinationAccountId(TransferRequestDTO transferRequestDTO) {
        final String destinationAccountCandidate = transferRequestDTO.getDestinationAccountId();
        return parseAccountId(destinationAccountCandidate);
    }

    private Long parseAccountId(String accountId) {
        if (isBlank(accountId)) {
            throw new BadRequestException("origin and destination accounts are needed for the transfer");
        }

        if (accountId.length() > 9) {
            throw new BadRequestException("account numbers must not contain more than 9 characters");
        }

        try {
            return new Long(accountId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("accounts must contain numbers only");
        }
    }

    private Money parseTransferAmount(TransferRequestDTO transferRequestDTO) {
        final String amountCandidate = transferRequestDTO.getAmount();
        final String currencyCandidate = transferRequestDTO.getCurrency();

        if (isBlank(amountCandidate)) {
            throw new BadRequestException("amount is needed to proceed with the transfer");
        }

        if (isBlank(currencyCandidate)) {
            throw new BadRequestException("currency code is needed to proceed with the transfer");
        }

        try {
            final BigDecimal amount = new BigDecimal(amountCandidate);
            final CurrencyUnit currency = Monetary.getCurrency(currencyCandidate.toUpperCase());
            return Money.of(amount, currency);
        } catch (NumberFormatException e) {
            throw new BadRequestException("amount must represent a money value (i.e.: 330 or 12.55 or 20)");
        } catch (UnknownCurrencyException e) {
            throw new BadRequestException("currency code specified is not valid");
        }
    }
}
