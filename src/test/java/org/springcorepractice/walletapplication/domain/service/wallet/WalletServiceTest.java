package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class WalletServiceTest {
    @Autowired
    private WalletUseCase walletUseCase;
    private WalletIdentity wallet;
    private WalletIdentity walletIdentity;
    private TransactionIdentity transactionIdentity;
    private UserIdentity userIdentity;

    @BeforeEach
    void setUp() {
        userIdentity = UserIdentity.builder().id("a9218f54-e750-4c75-85db-984261ef2ab8").email("akin@gmail.com").build();
        wallet = WalletIdentity.builder().amount(new BigDecimal("10000")).decription("nothing").build();

        walletIdentity = WalletIdentity.builder().
                balance(BigDecimal.ZERO).
                userId("a0c").
                createdAt(LocalDateTime.now().toString()).
                transactionIdentities(new ArrayList<>()).
                balance(BigDecimal.ZERO)
                .build();
        transactionIdentity = TransactionIdentity.builder().build();
    }


    @Test
    void createWalletIdentityTest() throws IdentityManagerException {
//        assertThrows(IdentityManagerException.class, () -> walletUseCase.findWalletUserId(wallet));
        WalletIdentity identity = walletUseCase.createWalletIdentity(walletIdentity);
        assertNotNull(identity.getTransactionIdentities());
        assertNotNull(identity);
        assertNotNull(identity.getUserId());
        assertEquals(identity.getUserId(),wallet.getUserId());


    }
    @Test
    void createWalletWhenWalletIdentityIsNull(){
        assertThrows(IdentityManagerException.class,()-> walletUseCase.createWalletIdentity(null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void createWalletWhenUserIdIsEmpty(String userId){
        wallet.setUserId(userId);
        assertThrows(IdentityManagerException.class,()-> walletUseCase.createWalletIdentity(wallet));

    }
    @Test
    void testDeposit_Success() throws IdentityManagerException, TransactionException {

        WalletIdentity result = walletUseCase.deposit(userIdentity, wallet);
        assertNotNull(result);
        assertEquals(BigDecimal.ZERO, result.getBalance());
    }
    @Test
    void updateBalance() throws IdentityManagerException, TransactionException {
        String transaction ="67437708e8134678d0576093";
        WalletIdentity walletIdentity = walletUseCase.updateWalletBalance(userIdentity,transaction);
        log.info("{}",walletIdentity);
        assertNotNull(walletIdentity);
    }



}