package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.*;

import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserIdentityMapper {
    @Mappings({@Mapping(target = "password", ignore = true)})
    UserEntity toUserEntity(UserIdentity userIdentity);

    UserIdentity toUserIdentity(UserEntity userEntity);
}
