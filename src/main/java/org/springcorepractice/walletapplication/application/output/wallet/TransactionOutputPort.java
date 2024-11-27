package org.springcorepractice.walletapplication.application.output.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;

import java.util.Optional;

public interface TransactionOutputPort {
    TransactionIdentity save (TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException;

    TransactionIdentity findByReference(String reference) throws IdentityManagerException;

    TransactionIdentity findTransactionById(String id) throws IdentityManagerException;
}
