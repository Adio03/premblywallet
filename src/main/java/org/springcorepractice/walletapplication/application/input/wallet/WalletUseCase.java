package org.springcorepractice.walletapplication.application.input.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;

public interface WalletUseCase {
    WalletIdentity createWalletIdentity(WalletIdentity walletIdentity) throws IdentityManagerException;
    WalletIdentity findWalletUserId(WalletIdentity walletIdentity) throws IdentityManagerException;
    WalletIdentity deposit(UserIdentity userIdentity,WalletIdentity walletIdentity) throws IdentityManagerException, TransactionException;
    WalletIdentity updateWalletBalance(UserIdentity userIdentity,String transactionId) throws IdentityManagerException, TransactionException;
}
