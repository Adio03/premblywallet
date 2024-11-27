package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.wallet;

import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.output.wallet.TransactionOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionAdapter implements TransactionOutputPort {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionIdentity save(TransactionIdentity transactionIdentity) throws IdentityManagerException, TransactionException {
        IdentityValidator.validateTransactionAmount(transactionIdentity.getAmount());
        IdentityValidator.validateDataElement(transactionIdentity.getWalletId());
        TransactionEntity transactionEntity = transactionMapper.mapToTransactionEntity(transactionIdentity);
        transactionEntity = transactionRepository.save(transactionEntity);
        return transactionMapper.mapToTransactionIdentity(transactionEntity);
    }

    @Override
    public TransactionIdentity findByReference(String reference) throws IdentityManagerException {
        TransactionEntity transactionEntity = transactionRepository.
                findByPaystackReference(reference).orElseThrow(()-> new IdentityManagerException("Transaction not found"));
        return transactionMapper.mapToTransactionIdentity(transactionEntity);
    }

    @Override
    public TransactionIdentity findTransactionById(String id) throws IdentityManagerException {
        TransactionEntity transactionEntity =
                transactionRepository.findById(id).orElseThrow(()-> new IdentityManagerException("Transaction not found"));
        return transactionMapper.mapToTransactionIdentity(transactionEntity);
    }


}
