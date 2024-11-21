package org.springcorepractice.walletapplication.application.output.identity;


import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springcorepractice.walletapplication.domain.dtos.request.LoginRequest;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;

public interface UserIdentityManagerOutputPort {
    UserIdentity createUser(UserIdentity userIdentity) throws IdentityManagerException;
    UserIdentity findUser(UserIdentity userIdentity) throws IdentityManagerException;
    UserIdentity login(UserIdentity userIdentity) throws IdentityManagerException;
    void deleteUser(UserIdentity userIdentity) throws IdentityManagerException;



}
