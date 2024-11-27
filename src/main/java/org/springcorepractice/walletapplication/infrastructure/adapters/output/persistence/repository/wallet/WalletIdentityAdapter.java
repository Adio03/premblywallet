package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.wallet;

import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.output.wallet.WalletIdentityOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.WalletEntity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper.WalletIdentityMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletIdentityAdapter implements WalletIdentityOutputPort {
    private final WalletEntityRepository walletEntityRepository;
    private final WalletIdentityMapper walletIdentityMapper;


    @Override
    public WalletIdentity save(WalletIdentity walletIdentity) throws IdentityManagerException {
        IdentityValidator.validateDataElement(walletIdentity.getUserId());
        WalletEntity savedWalletEntity = walletIdentityMapper.mapToWalletEntity(walletIdentity);
        savedWalletEntity.setWalletActive(Boolean.TRUE);
        savedWalletEntity = walletEntityRepository.save(savedWalletEntity);
        return walletIdentityMapper.mapToWalletIdentity(savedWalletEntity);
    }

    @Override
    public WalletIdentity findWalletByUserId(String userId) throws IdentityManagerException {
        IdentityValidator.validateDataElement(userId);
        WalletEntity walletEntity = walletEntityRepository.
                findByuserId(userId).orElseThrow(() -> new IdentityManagerException(IdentityMessage.WALLET_NOT_FOUND.getMessage()));
        return walletIdentityMapper.mapToWalletIdentity(walletEntity);
    }

    @Override
    public Optional<WalletIdentity> getWalletByUserId(String userId) throws IdentityManagerException {
        IdentityValidator.validateDataElement(userId);
        return walletEntityRepository.findByuserId(userId)
                .map(walletIdentityMapper::mapToWalletIdentity);

    }

    @Override
    public WalletIdentity findById(String id) throws IdentityManagerException {
        WalletEntity walletEntity = walletEntityRepository.
                findById(id).orElseThrow(()-> new IdentityManagerException(IdentityMessage.WALLET_NOT_FOUND.getMessage()));
        return walletIdentityMapper.mapToWalletIdentity(walletEntity);
    }

    @Override
    public void deleteUser(WalletIdentity wallet) throws IdentityManagerException {
        IdentityValidator.validateWalletIdentity(wallet);
        WalletEntity walletEntity = walletEntityRepository.
                findById(wallet.getId()).
                orElseThrow(()-> new IdentityManagerException(IdentityMessage.WALLET_NOT_FOUND.getMessage()));
        walletEntityRepository.delete(walletEntity);
    }



}
