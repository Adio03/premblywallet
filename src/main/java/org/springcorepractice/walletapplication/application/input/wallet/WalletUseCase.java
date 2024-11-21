package org.springcorepractice.walletapplication.application.input.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;

public interface WalletUseCase {
    WalletIdentity createWalletIdentity(WalletIdentity walletIdentity) throws IdentityManagerException;

    WalletIdentity findWalletUserId(WalletIdentity walletIdentity) throws IdentityManagerException;
}
