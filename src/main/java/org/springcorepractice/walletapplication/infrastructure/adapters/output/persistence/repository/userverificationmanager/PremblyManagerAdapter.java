package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.userverificationmanager;

import lombok.RequiredArgsConstructor;
import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationOutputPort;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyNinResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.PremblyResponseEntity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper.PremblyMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PremblyManagerAdapter implements IdentityVerificationOutputPort {
    private final PremblyMapper premblyMapper;
    private final PremblyEntityRepository premblyEntityRepository;

    @Override
    public PremblyNinResponse save(PremblyResponse premblyResponse) {
        PremblyResponseEntity premblyResponseEntity = premblyMapper.mapPremblyResponseEntityToPremblyResponse(premblyResponse);
        PremblyResponseEntity saveEntity = premblyEntityRepository.save(premblyResponseEntity);
//        return premblyMapper.mapPremblyResponse(saveEntity);
        return null;
    }
}
