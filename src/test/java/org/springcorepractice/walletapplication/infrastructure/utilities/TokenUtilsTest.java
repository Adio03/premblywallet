package org.springcorepractice.walletapplication.infrastructure.utilities;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class TokenUtilsTest {
    @Autowired
    private TokenUtils tokenUtils;

    private String email = "email@gmail.com";


    @Test
    void generateToken() {
        try {
            String token = tokenUtils.generateToken(email);
            assertNotNull(token);
            log.info("TOKEN ---> {}", token);
        } catch (IdentityManagerException exception) {
            exception.printStackTrace();
            fail(exception.getCause());

        }
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY,StringUtils.SPACE,"email@gmail . com","...email@.com"," email@gmail.com"," email@gmail.com  ","  email@gmail. com"})
    void validateEmail(String email){
           assertThrows(IdentityManagerException.class,()-> tokenUtils.generateToken(email));

    }
    @Test
    void decodeJWTToken() {
        try {
            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJpYXQiOjE3MzE1NzM4NDAsImV4cCI6MTczMjQ3Mzg0MH0.5EvgpIxM-PeuEKlB_TRZfhvgl9vwLP0q9DjJ7g_mNOs";
            String response = tokenUtils.decodeJWT(token);
            assertNotNull(response);
            log.info("decode token --> {}", response);

        } catch (IdentityManagerException exception) {
                exception.printStackTrace();
                fail(exception.getCause());
        }
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.SPACE,StringUtils.SPACE})
    void validateElement(String token){
        assertThrows(IdentityManagerException.class, ()-> tokenUtils.decodeJWT(token));
    }

}