package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.UserIdentityRequest;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.UserIdentityResponse;

@Mapper(componentModel = "spring")
public interface IdentityMapper {
    @Mappings({ @Mapping(source = "role", target = "identityRole"),
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "emailVerified", ignore = true),
    @Mapping(target = "enabled", ignore = true),
    @Mapping(target = "createdAt", ignore = true),
    @Mapping(target = "createdBy", ignore = true)})

    UserIdentity mapUserIdentityRequestToUserIdentity(UserIdentityRequest userIdentityRequest);
    UserIdentityResponse mapUserIdentityResponseToUserIdentity(UserIdentity userIdentity);
}
