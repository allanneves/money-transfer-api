package transfers.service;

import accounts.repository.AccountJDBCRepository;
import org.javamoney.moneta.Money;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import transfers.model.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class TransferBetweenAccountsTest {

    private static final String AMOUNT = "330.50";
    private static final String ORIGIN_ACC = "432925330";
    private static final String DESTINATION_ACC = "538238213";
    private static final String CURRENCY = "USD";

    @Mock
    private AccountJDBCRepository accountRepository;

    @InjectMocks
    private TransferBetweenAccounts transferService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void perform() {
        final Transfer build = Transfer.builder()
                .amount(Money.of(new BigDecimal(AMOUNT), CURRENCY))
                .destinationAccountId(Long.decode(DESTINATION_ACC))
                .originAccountId(Long.decode(ORIGIN_ACC))
                .timestamp(LocalDateTime.now())
                .transferId(new Random().nextInt())
                .build();

        when(accountRepository.accountExists(Long.decode(DESTINATION_ACC))).thenReturn(true);
        when(accountRepository.accountExists(Long.decode(ORIGIN_ACC))).thenReturn(true);
        final Transfer returnedTransfer = transferService.perform(build);
        assertThat(returnedTransfer.getStatus()).isEqualByComparingTo(Transfer.Status.SUCCESS);
    }
}