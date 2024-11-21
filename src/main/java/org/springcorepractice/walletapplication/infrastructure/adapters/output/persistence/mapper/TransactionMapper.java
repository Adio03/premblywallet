package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.*;
import org.springcorepractice.walletapplication.domain.model.wallet.TransactionIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.TransactionEntity;

@Mapper(componentModel= "spring")
public interface TransactionMapper {
    TransactionEntity mapToTransactionEntity(TransactionIdentity transactionIdentity);

    TransactionIdentity mapToTransactionIdentity(TransactionEntity transactionEntity);
}
