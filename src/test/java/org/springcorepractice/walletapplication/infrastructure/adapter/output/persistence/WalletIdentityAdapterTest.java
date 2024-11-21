package org.springcorepractice.walletapplication.infrastructure.adapter.output.persistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.output.wallet.WalletIdentityOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class WalletIdentityAdapterTest {
    @Autowired
    private WalletIdentityOutputPort walletIdentityOutputPort;
    private WalletIdentity wallet;

    @BeforeEach
    void setUp() {
        wallet = WalletIdentity.builder().balance(BigDecimal.ZERO).
                transactionIdentities(new ArrayList<>()).
                createdAt(LocalDateTime.now().toString()).
                userId("2b593895-5926-443d-bd32-1532f993e8d9")
                .build();
    }

    WalletIdentity createWallet() throws IdentityManagerException {
        return walletIdentityOutputPort.save(wallet);
    }

    @Test
    void saveWallet() throws IdentityManagerException {
        WalletIdentity graceWallet = createWallet();
        assertNotNull(graceWallet);
        assertNotNull(graceWallet.getUserId());
        log.info("Wallet Entity ----> {}", wallet);

        assertEquals(graceWallet.getBalance(), wallet.getBalance());
        assertEquals(graceWallet.getCreatedAt(), wallet.getCreatedAt());
        assertEquals(Collections.emptyList(), wallet.getTransactionIdentities());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void validateUserIdWhenSavingWallet(String userId) {
        wallet.setUserId(userId);
        assertThrows(IdentityManagerException.class, () -> walletIdentityOutputPort.save(wallet));
    }


    @Test
    void findWalletByUserId() throws IdentityManagerException {
            WalletIdentity graceWallet = createWallet();
            WalletIdentity walletIdentity = walletIdentityOutputPort.findWalletByUserId(graceWallet.getUserId());
            assertNotNull(walletIdentity);
            assertEquals(walletIdentity.getBalance(), wallet.getBalance());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void validateUserId(String userId) {
        assertThrows(IdentityManagerException.class, () -> walletIdentityOutputPort.findWalletByUserId(userId));
    }

    @Test
    void deleteWallet() throws IdentityManagerException {
        WalletIdentity graceWallet = createWallet();
        WalletIdentity walletIdentity = walletIdentityOutputPort.findWalletByUserId(graceWallet.getUserId());
        wallet.setId(walletIdentity.getId());
        walletIdentityOutputPort.deleteUser(walletIdentity);
        assertThrows(IdentityManagerException.class, () -> walletIdentityOutputPort.findById(walletIdentity.getId()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY})
    void deleteWalletWhenUserId_IsInvalid(String userId)  {
        wallet.setUserId(userId);
        assertThrows(IdentityManagerException.class,()-> walletIdentityOutputPort.deleteUser(wallet));
    }
    @Test
    void deletWalletWithNull(){
        assertThrows(IdentityManagerException.class,()-> walletIdentityOutputPort.deleteUser(null));
    }


    @AfterEach
    void tearDown(){
        log.info("Cleaning up...");
        try {
            WalletIdentity graceWallet = walletIdentityOutputPort.findWalletByUserId(wallet.getUserId());
            wallet.setId(graceWallet.getId());
            walletIdentityOutputPort.deleteUser(graceWallet);
        }catch(IdentityManagerException exception){
            exception.printStackTrace();
        }
    }
    }
