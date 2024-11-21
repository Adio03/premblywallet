package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springcorepractice.walletapplication.application.input.wallet.TransactionUseCase;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class TransactionServiceTest {
    @Autowired
    private TransactionUseCase transactionUseCase;
    private TransactionIdentity transactionIdentity;

    @BeforeEach
    void setUp(){
        transactionIdentity = TransactionIdentity.builder().
                amount(new BigDecimal("1000")).
                walletId("673b9282190e1b5e2a8db75e").
                type(TransactionType.DEPOSIT).description("School fees").build();
    }

    @Test
    void processDepostTrannsactionTest() throws IdentityManagerException {
        TransactionIdentity transactionResponse = transactionUseCase.processTransaction(transactionIdentity);
        log.info("Transaction response ... {}",transactionResponse);
        assertNotNull(transactionResponse);
        assertNotNull(transactionResponse.getPaystackReference());
    }
}
