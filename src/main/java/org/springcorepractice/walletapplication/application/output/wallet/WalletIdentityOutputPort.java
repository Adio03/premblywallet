package org.springcorepractice.walletapplication.application.output.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.WalletEntity;

import java.util.Optional;

public interface WalletIdentityOutputPort {
    WalletIdentity save(WalletIdentity walletIdentity) throws IdentityManagerException;

    WalletIdentity findWalletByUserId(String userId) throws IdentityManagerException;

    Optional<WalletIdentity> getWalletByUserId(String userId) throws IdentityManagerException;
    WalletIdentity findById(String id) throws IdentityManagerException;

    void deleteUser(WalletIdentity wallet) throws IdentityManagerException;
}
