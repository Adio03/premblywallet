package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
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
    private TransactionIdentity transactionIdentity;

    @BeforeEach
    void setUp() {
        wallet = WalletIdentity.builder().
                balance(BigDecimal.ZERO).
                userId("2c521790-563a-4449-a4bd-459bd5a2d4d7").
                createdAt(LocalDateTime.now().toString()).
                transactionIdentities(new ArrayList<>()).build();


        transactionIdentity = TransactionIdentity.builder().build();
    }

    @Test
    void createWalletIdentityTest() throws IdentityManagerException {
        assertThrows(IdentityManagerException.class, () -> walletUseCase.findWalletUserId(wallet));
        WalletIdentity walletIdentity = walletUseCase.createWalletIdentity(wallet);
        assertNotNull(walletIdentity);
        assertNotNull(walletIdentity.getUserId());
        assertEquals(walletIdentity.getUserId(),wallet.getUserId());


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
//    @Test
//    void depositTest(){
//        WalletIdentity walletIdentity = walletUseCase.deposit()
//    }


}