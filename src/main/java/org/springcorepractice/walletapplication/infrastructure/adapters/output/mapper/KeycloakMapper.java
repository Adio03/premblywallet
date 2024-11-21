package org.springcorepractice.walletapplication.infrastructure.adapters.output.mapper;

import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface KeycloakMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "email", target = "username")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    UserRepresentation map(UserIdentity userIdentity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "email")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    UserIdentity map(UserRepresentation userRepresentation);


}