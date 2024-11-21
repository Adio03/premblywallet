package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.repository.identity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.UserEntity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.mapper.UserIdentityMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserIdentityAdapter implements UserIdentityOutputPort {
    private final UserEntityRepository userEntityRepository;
    private final UserIdentityMapper userIdentityMapper;


    @Override
    public UserIdentity save(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateUserIdentity(userIdentity);
        if(isUserExist(userIdentity)){
            throw new IdentityManagerException(IdentityMessage.USER_IDENTITY_ALREADY_EXISTS.getMessage());
        }
        UserEntity userEntity = userIdentityMapper.toUserEntity(userIdentity);
        userEntity = userEntityRepository.save(userEntity);
        log.info("UserIdentity saved {}", userIdentity);
        return userIdentityMapper.toUserIdentity(userEntity);

    }

    @Override
    public UserIdentity findById(String userId) throws IdentityManagerException {
        IdentityValidator.validateUserIdentityId(userId);
        UserEntity userEntity = getUserEntityById(userId);
        return userIdentityMapper.toUserIdentity(userEntity);
    }

    @Override
    public void deleteUser(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateUserIdentityId(userIdentity.getId());
        UserEntity userEntity = getUserEntityById(userIdentity.getId());
        deleteUserEntity(userEntity);
    }


    private boolean isUserExist(UserIdentity userIdentity) {
        return userEntityRepository.findByEmail(userIdentity.getEmail()).isPresent();
    }


    @Override
    public UserIdentity findByEmail(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validatEmail(userIdentity.getEmail());
        UserEntity userEntity = getUserEntityByEmail(userIdentity);
        return userIdentityMapper.toUserIdentity(userEntity);

    }


    private UserEntity getUserEntityByEmail(UserIdentity userIdentity) throws IdentityManagerException {
        return userEntityRepository.findByEmail(userIdentity.getEmail()).
                orElseThrow( ()-> new IdentityManagerException(IdentityMessage.USER_NOT_FOUND.getMessage()));

    }

    private UserEntity getUserEntityById(String userId) throws IdentityManagerException {
        return userEntityRepository.findById(userId).
                orElseThrow(()-> new IdentityManagerException(IdentityMessage.USER_NOT_FOUND.getMessage()));

    }
    private void deleteUserEntity(UserEntity userEntity) throws IdentityManagerException {
        IdentityValidator.validateObjectInstance(userEntity);
        userEntityRepository.delete(userEntity);
    }


}
