package org.springcorepractice.walletapplication.application.input.identity;


import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;

public interface CreateUserUseCase {
    UserIdentity signup(UserIdentity userIdentity) throws IdentityManagerException;

    void deleteUserIdentity(UserIdentity foundUser) throws IdentityManagerException;

    UserIdentity findUserIdentity(UserIdentity userIdentity) throws IdentityManagerException;
}
