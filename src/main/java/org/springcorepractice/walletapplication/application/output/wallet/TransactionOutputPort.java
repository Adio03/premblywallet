package org.springcorepractice.walletapplication.application.output.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;

import java.util.Optional;

public interface TransactionOutputPort {
    TransactionIdentity save (TransactionIdentity transactionIdentity) throws IdentityManagerException;

    TransactionIdentity findByReference(String reference) throws IdentityManagerException;
}
