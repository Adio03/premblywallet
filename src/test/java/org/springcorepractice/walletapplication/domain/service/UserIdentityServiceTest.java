package org.springcorepractice.walletapplication.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springcorepractice.walletapplication.application.input.identity.CreateUserUseCase;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserIdentityServiceTest {
    @Autowired
    private CreateUserUseCase createUserUseCase;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserIdentity userIdentity;



    @BeforeEach
    void setUp(){
        userIdentity = UserIdentity.builder().
                firstName("Akin").
                lastName("Akintola").
                email("akin@gmail.com").
                identityRole(IdentityRole.USER).
                password("Test@123").build();


    }
    @Test
    void signupUser() throws IdentityManagerException {
            UserIdentity saveResponse = createUserUseCase.signup(userIdentity);
            assertNotNull(saveResponse);
            assertEquals(saveResponse.getFirstName(), userIdentity.getFirstName());
            assertEquals(saveResponse.getLastName(), userIdentity.getLastName());
            assertEquals(saveResponse.getEmail(), userIdentity.getEmail());
    }
    @Test
    void deleteUserIdentityTest() throws IdentityManagerException {
        UserIdentity foundUser = createUserUseCase.findUserIdentity(userIdentity);
        createUserUseCase.deleteUserIdentity(foundUser);
        assertThrows(IdentityManagerException.class,()-> createUserUseCase.findUserIdentity(userIdentity));
    }


    @Test
    void testPasswordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("myPassword");
       log.info("password -----> {}", encodedPassword);
        assertNotNull(encodedPassword);
    }

}