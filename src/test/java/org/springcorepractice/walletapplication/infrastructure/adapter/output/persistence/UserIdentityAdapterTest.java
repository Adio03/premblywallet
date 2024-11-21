package org.springcorepractice.walletapplication.infrastructure.adapter.output.persistence;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.output.identity.UserIdentityOutputPort;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
@SpringBootTest
class UserIdentityAdapterTest {
    @Autowired
    private UserIdentityOutputPort userIdentityOutputPort;
    private UserIdentity william;
    private UserIdentity tate;


    @BeforeEach
    void setUp() {

        william = new UserIdentity();
        william.setFirstName("william");
        william.setLastName("will");
        william.setEmail("william@gmail.com");
        william.setId("2c521790-563a-4449-a4bd-459bd5a2d4d7");
        william.setEmailVerified(false);
        william.setEnabled(false);
        william.setCreatedAt(LocalDateTime.now().toString());
        william.setIdentityRole(IdentityRole.USER);
        william.setCreatedBy(william.getId());;


    }
    UserIdentity creatUser() throws IdentityManagerException {
    return  userIdentityOutputPort.save(william);
    }


    @Test
    void saveUser() throws IdentityManagerException {
            UserIdentity user = creatUser();
            assertNotNull(user);
            assertNotNull(user.getId());
            UserIdentity foundUser = userIdentityOutputPort.findByEmail(william);
            assertEquals(foundUser.getFirstName(), user.getFirstName());
            assertEquals(foundUser.getLastName(), user.getLastName());
            assertEquals(foundUser.getCreatedBy(), user.getCreatedBy());
    }


    @Test
    void saveUserWithExistingEmail() throws IdentityManagerException {
        UserIdentity foundUser = userIdentityOutputPort.findByEmail(william);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(foundUser));

    }

    @Test
    void createUserWithNull() {
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(null));
    }

    @Test
    void createUserWithNullId() {
        william.setId(null);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithEmpytId() {
        william.setId(StringUtils.EMPTY);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithFirstnameNull() {
        william.setFirstName(null);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithFirstnameEmpyt() {
        william.setFirstName(StringUtils.EMPTY);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithlastnameNull() {
        william.setLastName(null);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithLastnameEmpyt() {
        william.setLastName(StringUtils.EMPTY);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithEmailNull() {
        william.setEmail(null);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void createUserWithEmailEmpyt() {
        william.setEmail(StringUtils.EMPTY);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.save(william));
    }

    @Test
    void findUserEntityWithEmail() {
        try {
            UserIdentity foundUser = userIdentityOutputPort.findByEmail(william);
            assertNotNull(foundUser);
            assertEquals(foundUser.getFirstName(), william.getFirstName());
            assertEquals(foundUser.getLastName(), william.getLastName());
            assertEquals(foundUser.getCreatedBy(), william.getCreatedBy());
        } catch (IdentityManagerException exception) {
            log.error("{} {}->", exception.getClass().getName(), exception.getMessage());
        }

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "invalid@gmail.com", "invalid"})
    void findUserEntityWithEmail(String email) {
        william.setEmail(email);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.findByEmail(william));
    }


    @Test
    void findUserEntityById() {
        try {
            UserIdentity foundUser = userIdentityOutputPort.findById(william.getId());
            assertNotNull(foundUser);
            assertEquals(foundUser.getFirstName(), william.getFirstName());
            assertEquals(foundUser.getLastName(), william.getLastName());
            assertEquals(foundUser.getCreatedBy(), william.getCreatedBy());
        } catch (IdentityManagerException exception) {
            log.error("{} {}->", exception.getClass().getName(), exception.getMessage());
        }

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "23245", "invalid"})
    void findUserEntityIdWithNull(String userId) {
        william.setId(userId);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.findById(william.getId()));
    }

    @Test
    void deleteUserEntity() throws IdentityManagerException {

            UserIdentity foundUser = userIdentityOutputPort.findById(william.getId());
            userIdentityOutputPort.deleteUser(foundUser);
            UserIdentity deletedUser = userIdentityOutputPort.findById(william.getId());
            assertNull(deletedUser);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.findById(william.getId()));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, "12345",})
    void deleteUseEntityWithNull(String userId) {
        william.setId(userId);
        assertThrows(IdentityManagerException.class, () -> userIdentityOutputPort.deleteUser(william));
    }

   @Test
    void deleteUserEntityWithEmail() {
        try {
            UserIdentity foundUser = userIdentityOutputPort.findByEmail(tate);
            assertNotNull(foundUser);
            userIdentityOutputPort.deleteUser(foundUser);

        } catch (IdentityManagerException exception) {
            log.info("{} ->", exception.getMessage());
            fail("Attempt to delete user");
        }

    }
    @AfterEach
    void tearDown() throws IdentityManagerException {
        UserIdentity foundUser = userIdentityOutputPort.findByEmail(tate);
        assertNotNull(foundUser);
        userIdentityOutputPort.deleteUser(foundUser);


    }



}





