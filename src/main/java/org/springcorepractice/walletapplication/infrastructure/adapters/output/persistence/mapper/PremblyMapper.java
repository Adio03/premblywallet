package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyNinResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyNinsResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.PremblyResponseEntity;

@Mapper(componentModel= "spring")
public interface PremblyMapper {
    PremblyResponseEntity mapPremblyResponseEntityToPremblyResponse(PremblyResponse premblyResponse);
    @Mapping(source = "ninData.firstname",target = "firstname")
    @Mapping(source = "ninData.lastname", target = "lastname")
    PremblyNinsResponse mapPremblyResponse(PremblyResponseEntity premblyResponse);
}
