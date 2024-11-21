package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.WalletEntity;

@Mapper(componentModel = "spring")
public interface WalletIdentityMapper {
    WalletEntity mapToWalletEntity(WalletIdentity walletIdentity);

    WalletIdentity mapToWalletIdentity(WalletEntity walletEntity);
}
