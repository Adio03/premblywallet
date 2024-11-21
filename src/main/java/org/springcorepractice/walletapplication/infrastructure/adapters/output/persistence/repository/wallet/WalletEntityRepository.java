package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.wallet;

import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.WalletEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WalletEntityRepository extends MongoRepository<WalletEntity, String> {

    Optional<WalletEntity> findByuserId(String userId);


}

