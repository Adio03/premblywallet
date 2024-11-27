package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springcorepractice.walletapplication.application.input.wallet.TransactionUseCase;
import org.springcorepractice.walletapplication.application.output.wallet.WalletIdentityOutputPort;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.domain.enums.TransactionStatus;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {
    private final WalletIdentityOutputPort walletIdentityOutputPort;
    private final TransactionUseCase transactionUseCase;

    @Override
    public WalletIdentity createWalletIdentity(WalletIdentity walletIdentity) throws IdentityManagerException {
        IdentityValidator.validateWalletIdentity(walletIdentity);

        WalletIdentity wallet = validatAndCreateWallet(walletIdentity);
        return walletIdentityOutputPort.save(wallet);
    }

    private WalletIdentity createWallet(WalletIdentity walletIdentity) {
        return  WalletIdentity.builder().
                balance(BigDecimal.ZERO).
                transactionIdentities(new ArrayList<>()).
                userId(walletIdentity.getUserId()).
                createdAt(LocalDateTime.now().toString()).build();
    }

    private WalletIdentity validatAndCreateWallet(WalletIdentity walletIdentity) throws IdentityManagerException {
         Optional<WalletIdentity> foundWallet = walletIdentityOutputPort.getWalletByUserId(walletIdentity.getUserId());
        if (foundWallet.isPresent() && foundWallet.get().isWalletActive()) {
            throw new IdentityManagerException(IdentityMessage.WALLET_ALREADY_EXIST.getMessage());
        }
        return createWallet(walletIdentity);
    }

    @Override
    public WalletIdentity findWalletUserId(WalletIdentity walletIdentity) throws IdentityManagerException {
        return walletIdentityOutputPort.findWalletByUserId(walletIdentity.getUserId());
    }

    @Override
    public WalletIdentity deposit(UserIdentity userIdentity ,WalletIdentity walletIdentity) throws IdentityManagerException, TransactionException {
        WalletIdentity wallet = walletIdentityOutputPort.findWalletByUserId(userIdentity.getId());
        log.info("here--->{}",wallet);

        TransactionIdentity transactionIdentity = TransactionIdentity.builder()
                .type(TransactionType.DEPOSIT)
                .walletId(wallet.getId())
                .amount(walletIdentity.getAmount())
                .description(walletIdentity.getDecription())
                .userEmail(userIdentity.getEmail())
                .build();
        TransactionIdentity transaction = transactionUseCase.processTransaction(transactionIdentity);
        if (wallet.getTransactionIdentities() == null) {
            wallet.setTransactionIdentities(new ArrayList<>());

        }
        wallet.getTransactionIdentities().add(transaction);
        log.info("Transaction...{}",wallet);
        return walletIdentityOutputPort.save(wallet);
    }


    @Override
    public WalletIdentity updateWalletBalance(UserIdentity userIdentity,String transactionId) throws IdentityManagerException, TransactionException {
        WalletIdentity walletIdentity = walletIdentityOutputPort.findWalletByUserId(userIdentity.getId());
        if (walletIdentity.getTransactionIdentities() == null || walletIdentity.getTransactionIdentities().isEmpty()) {
            throw new IdentityManagerException("No transactions found in the wallet.");
        }
        TransactionIdentity existingTransaction = getTransactionById(walletIdentity,transactionId);
        TransactionIdentity verifiedTransaction = transactionUseCase.verifyTransaction(existingTransaction);


        if (StringUtils.equalsIgnoreCase(verifiedTransaction.getTransactionStatus(),TransactionStatus.SUCCESS.toString())){
            BigDecimal newBalance = walletIdentity.getBalance().add(verifiedTransaction.getAmount());
            walletIdentity.setBalance(newBalance);
        } else {
            throw new IdentityManagerException("Transaction verification failed.");
        }

        return walletIdentityOutputPort.save(walletIdentity);
    }
    public TransactionIdentity getTransactionById(WalletIdentity walletIdentity, String transactionId) throws IdentityManagerException {
        if (walletIdentity.getTransactionIdentities() != null) {
            return walletIdentity.getTransactionIdentities().stream()
                    .filter(transaction -> transaction.getId().equals(transactionId))
                    .findFirst()
                    .orElseThrow(() -> new IdentityManagerException("Transaction with ID " + transactionId + " not found."));
        } else {
            throw new IdentityManagerException("No transactions found for this wallet.");
        }
    }


}
