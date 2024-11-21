package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.identity;


import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserEntityRepository extends MongoRepository<UserEntity,String> {
    Optional<UserEntity> findByEmail(String email);
}
