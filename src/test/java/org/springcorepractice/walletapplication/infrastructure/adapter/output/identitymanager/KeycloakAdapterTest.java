package org.springcorepractice.walletapplication.infrastructure.adapter.output.identitymanager;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityManagerOutputPort;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.identitymanager.KeycloakAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j

class KeycloakAdapterTest {

    @Autowired
    private UserIdentityManagerOutputPort identityManagerOutputPort;

    private UserIdentity william;



    @BeforeEach
     void setUp (){
        william = UserIdentity.builder().
                firstName("william").
                lastName("williams").
                email("williamnowonsave@gmail.com").
                identityRole(IdentityRole.USER).
                createdAt(LocalDateTime.now().toString()).password("1234Aa@09").build();
    }

     UserIdentity createWiliam () throws IdentityManagerException {
      return identityManagerOutputPort.createUser(william);

    }
    @Test
    void createUser() throws IdentityManagerException {
            UserIdentity userIdentity = createWiliam();
            assertNotNull(userIdentity);
            assertNotNull(userIdentity.getId());
            assertEquals(userIdentity.getFirstName(), william.getFirstName());
            assertEquals(userIdentity.getLastName(), william.getLastName());
            assertEquals(userIdentity.getEmail(), william.getEmail());
            UserIdentity will = identityManagerOutputPort.findUser(william);
            assertNotNull( will);
            assertEquals(userIdentity.getId(),will.getId());

    }

    @Test
    void createUserWithNullUserIdentity(){
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.createUser(null));
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE})
    void createUserWithEmptyFirstname(String firstname){
        william.setFirstName(firstname);
        assertThrows(IdentityManagerException.class, ()-> identityManagerOutputPort.createUser(william));
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY})
    void createUserWithEmptyLastname(String lastName){
        william.setLastName(lastName);
        assertThrows(IdentityManagerException.class, ()-> identityManagerOutputPort.createUser(william));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY})
    void createUserWithInvalidPassword(String password){
        william.setPassword(password);
        assertThrows(IdentityManagerException.class, ()-> identityManagerOutputPort.createUser(william));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void creatUserWithValidEmail(String email){
        william.setEmail(email);
        assertThrows(IdentityManagerException.class,() -> identityManagerOutputPort.createUser(william));
    }
    @Test
    void findUser() throws IdentityManagerException {
        UserIdentity will = createWiliam();
        UserIdentity userIdentity = identityManagerOutputPort.findUser(will);
        assertNotNull(userIdentity);
        assertNotNull(userIdentity.getId());
        assertEquals(will.getId(),userIdentity.getId());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void findUserIdentityWithInvalidEmail(String email){
        william.setEmail(email);
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.findUser(william));
    }
    @Test
    void findUserNullUserIdentity()  {
        String message = IdentityMessage.INVALID_OBJECT.getMessage();
    IdentityManagerException exception = assertThrows(IdentityManagerException.class, () -> identityManagerOutputPort.findUser(null));
    assertEquals(message,exception.getMessage());
}

    @Test
    void login() throws IdentityManagerException {
        UserIdentity will = createWiliam();
        UserIdentity tokenResponse = identityManagerOutputPort.login(will);
        assertNotNull(tokenResponse);
        log.info("Access token ---> {}",tokenResponse);
        assertNotNull(tokenResponse.getAccessToken());
        assertNotNull(tokenResponse.getRefreshToken());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            "  email@gmail.com",
            "email@gmail.com  ",
            "  email@gmail.com  "
    })
    void loginWithInvalidEmail(String email){
        william.setEmail(email);
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.login(william));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            StringUtils.SPACE,
            StringUtils.EMPTY,
            "123456",
            " 1234Aa@09",
            " 1234A  a@09  ",
            "  1234Aa@09"
    })
    void loginWithInvalidPassword(String password){
        william.setPassword(password);
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.login(william));
    }

    @Test
    void deleteUser() throws IdentityManagerException {
        UserIdentity william = createWiliam();
        UserIdentity userIdentity = identityManagerOutputPort.findUser(william);
        william.setId(userIdentity.getId());
        identityManagerOutputPort.deleteUser(william);
        assertThrows(IdentityManagerException.class,() ->identityManagerOutputPort.findUser(william));
    }

    @Test
    void deleteUserWithNull()  {
        assertThrows(IdentityManagerException.class,() ->identityManagerOutputPort.deleteUser(null));
    }
    @Test
    void deleteUserWithInvalid_Id(){
        william.setId("invalid");
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.deleteUser(william));
    }
    @ParameterizedTest
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.EMPTY," 22879u9u000878726778  ", "yt766564367645  444"})
    void deleteUserWithInvalidId(String id){
        william.setId(id);
        assertThrows(IdentityManagerException.class,()-> identityManagerOutputPort.deleteUser(william));
    }

  @AfterEach
    void tearDown(){
        log.info("Cleaning up...");
        try {
            UserIdentity userIdentity = identityManagerOutputPort.findUser(william);
            william.setId(userIdentity.getId());
            identityManagerOutputPort.deleteUser(william);
        }catch (IdentityManagerException exception){
            exception.printStackTrace();
        }
    }


}

