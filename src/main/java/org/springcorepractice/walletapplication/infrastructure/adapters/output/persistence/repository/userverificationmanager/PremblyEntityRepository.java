package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.userverificationmanager;

import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.PremblyResponseEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PremblyEntityRepository extends MongoRepository<PremblyResponseEntity, String> {
}
