package org.springcorepractice.walletapplication.infrastructure.adapter.output.persistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.output.wallet.TransactionOutputPort;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class TransactionIdentityAdapterTest {
    @Autowired
    private TransactionOutputPort transactionOutputPort;
    private TransactionIdentity firstTransactionIdentity;

    @BeforeEach
    void setUp(){
        firstTransactionIdentity =
                TransactionIdentity.builder().
                        amount(new BigDecimal("100")).
                        type(TransactionType.DEPOSIT).
                        paystackReference("rygertdccvv").description("school fees").walletId("wallet").build();
    }
    @Test
    void saveTransaction() throws TransactionException, IdentityManagerException {

            TransactionIdentity transactionIdentity = transactionOutputPort.save(firstTransactionIdentity);
            assertNotNull(transactionIdentity);
            log.info("Transaction ----> {}", transactionIdentity);
            assertEquals(transactionIdentity.getAmount(), firstTransactionIdentity.getAmount());
            assertEquals(transactionIdentity.getPaystackReference(), firstTransactionIdentity.getPaystackReference());

        }

    @ParameterizedTest
    @ValueSource(strings = {"-1.0", "-0.01"})
    void testAmountCannotBeNegative(String amount) {
        firstTransactionIdentity.setAmount(new BigDecimal(amount));
        assertThrows(IdentityManagerException.class, () -> transactionOutputPort.save(firstTransactionIdentity));
    }

    @ParameterizedTest
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void validateUserId(String walletId){
        firstTransactionIdentity.setWalletId(walletId);
        assertThrows(IdentityManagerException.class,()-> transactionOutputPort.save(firstTransactionIdentity));
    }

}
