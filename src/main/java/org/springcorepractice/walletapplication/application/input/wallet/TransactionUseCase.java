package org.springcorepractice.walletapplication.application.input.wallet;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;

public interface TransactionUseCase {
    TransactionIdentity processTransaction(TransactionIdentity transactionIdentity) throws IdentityManagerException;

}
