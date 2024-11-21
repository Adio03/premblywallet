package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.wallet;

import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransactionRepository extends MongoRepository<TransactionEntity, String> {

    Optional<TransactionEntity> findByPaystackReference(String reference);
}
