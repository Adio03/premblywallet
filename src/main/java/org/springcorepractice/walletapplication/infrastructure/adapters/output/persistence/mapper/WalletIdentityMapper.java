package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.WalletEntity;

@Mapper(componentModel = "spring")
public interface WalletIdentityMapper {
    @Mapping(source = "transactionIdentities",target = "transactionEntity")
    WalletEntity mapToWalletEntity(WalletIdentity walletIdentity);
    @Mapping(source ="transactionEntity",target = "transactionIdentities")
    WalletIdentity mapToWalletIdentity(WalletEntity walletEntity);


}
