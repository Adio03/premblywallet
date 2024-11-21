package org.springcorepractice.walletapplication.infrastructure.adapters.output.identitymanager;



import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityManagerOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.mapper.KeycloakMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAdapter implements UserIdentityManagerOutputPort {
    private final Keycloak keycloak;
    private final KeycloakMapper mapper;
    @Value("${realm}")
    private String KEYCLOAK_REALM;
    @Value("${keycloak.clientId}")
    private String CLIENT_ID;

    @Value("${keycloak.server.url}")
    private String SERVER_URL;

    @Value("${keycloak.client.secret}")
    private String CLIENT_SECRET;


    @Override
    public UserIdentity createUser(UserIdentity userIdentity) throws IdentityManagerException {
        log.info("USERIDENTITY {} ",userIdentity);
        IdentityValidator.validateDataInput(userIdentity);
        UserRepresentation userRepresentation = mapper.map(userIdentity);
        setUpPassword(userIdentity.getPassword(),userRepresentation);
        userRepresentation.setEmailVerified(Boolean.TRUE);
        userRepresentation.setEnabled(Boolean.TRUE);
        creatUserResources(userRepresentation,userIdentity);
        log.info("UserIdentity Successfully created {} ", userIdentity);
        return userIdentity;

    }

    @Override
    public UserIdentity findUser(UserIdentity userIdentity) throws IdentityManagerException {
        UserRepresentation userRepresentation = getUserRepresentation(userIdentity);
        return mapper.map(userRepresentation);
    }

    private void creatUserResources(UserRepresentation userRepresentation, UserIdentity userIdentity) throws IdentityManagerException {
        try{
            UsersResource users = keycloak.realm(KEYCLOAK_REALM).users();
            Response response = users.create(userRepresentation);
            if (response.getStatusInfo().equals(Response.Status.CONFLICT)) {
                log.error("{} - {} --- An error occurred while attempting to create a user in Keycloak.", Response.Status.CONFLICT, IdentityMessage.USER_IDENTITY_ALREADY_EXISTS.getMessage());
                throw new IdentityManagerException(IdentityMessage.USER_IDENTITY_ALREADY_EXISTS.getMessage());
            }
            UserRepresentation createdUserRepresentation = getUserRepresentation(userIdentity);
            userIdentity.setId(createdUserRepresentation.getId());
            createRole(userIdentity);
        } catch (NotFoundException | IdentityManagerException exception) {
            log.error("{} - {} --- Error occurred on attempting to create user on keycloak", exception.getClass().getName(), exception.getMessage());
            throw new IdentityManagerException(exception.getMessage());
        }
    }

    private void setUpPassword(String password, UserRepresentation keyCloakUser) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        keyCloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
    }

    private  UserRepresentation getUserRepresentation(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateObjectInstance(userIdentity);
       IdentityValidator.validatEmail(userIdentity.getEmail());
        return keycloak.realm(KEYCLOAK_REALM).
                users().
                searchByEmail(userIdentity.getEmail(), Boolean.TRUE).stream().findFirst().orElseThrow(()-> new IdentityManagerException(IdentityMessage.USER_NOT_FOUND.getMessage()));
    }

    public UserResource getUserResource(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateObjectInstance(userIdentity);
        IdentityValidator.validateDataElement(userIdentity.getId());
        return keycloak
                .realm(KEYCLOAK_REALM)
                .users()
                .get(userIdentity.getId());
    }

    @Override
    public void deleteUser(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateUserIdentityObject(userIdentity);
        IdentityValidator.validateUserIdentityId(userIdentity.getId());
        UserResource userResource = getUserResource(userIdentity);
        try{
            userResource.remove();
        }catch (NotFoundException exception) {
            log.info("deleteUser called with invalid user id: {}", userIdentity.getId());
            throw new IdentityManagerException(IdentityMessage.USER_NOT_FOUND.getMessage());
        }

    }

    @Override
    public UserIdentity login(UserIdentity userIdentity) throws IdentityManagerException {
        IdentityValidator.validateDataElement(userIdentity.getEmail());
        IdentityValidator.validateDataElement(userIdentity.getPassword());
        UserIdentity user = findUser(userIdentity);
        log.info("User login credentials: {}, {}", userIdentity.getEmail(), userIdentity.getPassword());
        try {
            Keycloak keycloakClient = getKeycloak(userIdentity);
            TokenManager tokenManager = keycloakClient.tokenManager();
            user.setAccessToken(tokenManager.getAccessToken().getToken());
            user.setRefreshToken(tokenManager.refreshToken().getRefreshToken());
            log.info("Token {}", user);
            return user;

        } catch (NotAuthorizedException | BadRequestException exception) {
            log.info("Error logging in user: {}", exception.getMessage());
            throw new IdentityManagerException(IdentityMessage.INVALID_EMAIL_OR_PASSWORD.getMessage());
        }

    }

    private Keycloak getKeycloak(UserIdentity userIdentity) {
        String email = userIdentity.getEmail().trim();
        String password = userIdentity.getPassword().trim();

        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.PASSWORD)
                .realm(KEYCLOAK_REALM)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .username(email)
                .password(password)
                .serverUrl(SERVER_URL)
                .build();
    }

    public void createRole(UserIdentity userIdentity){
        RealmResource realmResource = keycloak.realm(KEYCLOAK_REALM);
        RolesResource rolesResource = realmResource.roles();
        if(!roleExists(rolesResource,userIdentity.getIdentityRole().name())){
            RoleRepresentation roleRepresentation = new RoleRepresentation();
            roleRepresentation.setName(userIdentity.getIdentityRole().name());
            rolesResource.create(roleRepresentation);
            ClientRepresentation client = realmResource.clients().findByClientId(CLIENT_ID).get(0);
            realmResource.clients().get(client.getId()).roles().create(roleRepresentation);
        }
    }

    private boolean roleExists(RolesResource rolesResource, String roleName) {
        List<RoleRepresentation> existingRoles = rolesResource.list();
        return existingRoles.stream().anyMatch(role -> roleName.equals(role.getName()));
    }




}

