package org.springcorepractice.walletapplication.application.output.identity;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;

public interface UserIdentityOutputPort {

    UserIdentity findByEmail(UserIdentity userIdentity) throws IdentityManagerException;

    UserIdentity save(UserIdentity userIdentity) throws IdentityManagerException;

    UserIdentity findById(String userId) throws IdentityManagerException;

    void deleteUser(UserIdentity userIdentity) throws IdentityManagerException;

}
