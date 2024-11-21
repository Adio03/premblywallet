package org.springcorepractice.walletapplication.domain.service.wallet;

import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.output.wallet.WalletIdentityOutputPort;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {
    private final WalletIdentityOutputPort walletIdentityOutputPort;

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



}
