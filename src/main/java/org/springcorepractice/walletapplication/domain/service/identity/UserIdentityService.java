package org.springcorepractice.walletapplication.domain.service.identity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.input.identity.CreateUserUseCase;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityManagerOutputPort;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityOutputPort;
import org.springcorepractice.walletapplication.application.input.wallet.WalletUseCase;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserIdentityService implements CreateUserUseCase {
    private final UserIdentityOutputPort userIdentityOutputPort;
    private final UserIdentityManagerOutputPort userIdentityManagerOutputPort;
    private final WalletUseCase walletUseCase;

    @Override
    public UserIdentity signup(UserIdentity userIdentity) throws IdentityManagerException {
        log.info("Signup UserIdentity {}", userIdentity);
        IdentityValidator.validateUserInput(userIdentity);
        UserIdentity user = userIdentityManagerOutputPort.createUser(userIdentity);
        log.info("UserIdentity after been save to Keycloak {}", user);
        user.setCreatedBy(user.getId());
        user.setCreatedAt(LocalDateTime.now().toString());
        WalletIdentity walletIdentity = WalletIdentity.builder().build();
        walletIdentity.setUserId(user.getId());
        walletIdentity = walletUseCase.createWalletIdentity(walletIdentity);
        user.setWalletIdentity(walletIdentity);
          log.info("User Wallet -----> {}",user.getWalletIdentity());
        return userIdentityOutputPort.save(user);

    }

    @Override
    public void deleteUserIdentity(UserIdentity userIdentity) throws IdentityManagerException {
        UserIdentity user = findUserIdentity(userIdentity);
        user = findUserIdentityIn(user);
        userIdentityManagerOutputPort.deleteUser(user);
        userIdentityOutputPort.deleteUser(user);

    }

    @Override
    public UserIdentity findUserIdentity(UserIdentity userIdentity) throws IdentityManagerException {
        return userIdentityManagerOutputPort.findUser(userIdentity);

    }

    public UserIdentity findUserIdentityIn(UserIdentity userIdentity) throws IdentityManagerException {
        return userIdentityOutputPort.findById(userIdentity.getId());
    }


}




