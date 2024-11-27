package org.springcorepractice.walletapplication.application.input.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;

public interface TransactionUseCase {
    TransactionIdentity processTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException;
    TransactionIdentity verifyTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException;
    TransactionIdentity findById(String transactionIdentity) throws IdentityManagerException;

}
