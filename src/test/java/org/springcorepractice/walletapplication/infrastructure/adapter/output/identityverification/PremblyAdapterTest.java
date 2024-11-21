package org.springcorepractice.walletapplication.infrastructure.adapter.output.identityverification;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationManagerOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PremblyResponse;
import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyVerificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class PremblyAdapterTest {
    @Autowired
    private IdentityVerificationManagerOutputPort identityVerificationManagerOutputPort;
    private IdentityVerification identityVerification;

    @BeforeEach
    void setUp() {
        identityVerification = IdentityVerification.builder().nin("33704462248").
                imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/oebgq4kj0b0n3avrtxqa.jpg").build();
    }

    @Test
    void verifyIdentityWithValidNinAndValidImage() throws IdentityManagerException {
        PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
        log.info("Response ----> {}", response);
        assertNotNull(response);
        assertEquals("00",response.getResponseCode());
        assertTrue(response.isVerified());
        assertEquals("00",response.getFaceData().getResponseCode());
        assertTrue(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isIdentityVerified());
        assertEquals(response.getNinData().getNin(),identityVerification.getNin());
    }

    @Test
    void verifyIdentityWhenImageDoesNotMatch() throws IdentityManagerException {
        identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
        PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerified());
        assertEquals("VERIFIED",response.getVerification().getStatus());
        assertFalse(response.getFaceData().isFaceVerified());
        assertEquals("01",response.getFaceData().getResponseCode());
    }

    @Test
    void verifyIdentityWithInvalidNin() throws IdentityManagerException {
        identityVerification.setNin("12345678901");
        PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerified());
        assertEquals("01",response.getResponseCode());
        assertEquals("NOT-VERIFIED",response.getVerification().getStatus());
    }
    @Test
    void verifyIdentityWhenImageIsNotPosition() throws IdentityManagerException {
        identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027712/ez15xfsdj3whhd5kwscs.jpg");
        PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerified());
        assertFalse(response.getFaceData().isFaceVerified());
        assertEquals(PremblyVerificationMessage.PREMBLY_FACE_CONFIRMATION.getMessage(),response.getFaceData().getMessage());
    }
    @Test
    void verifyIdentityWithNullIdentityVerification() {
        assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyIdentity(null));
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithEmptyIdentityId(String nin) {
        identityVerification.setNin(nin);
        assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyIdentity(identityVerification));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithNullIdentityImage(String url) {
        identityVerification.setImageUrl(url);
        assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyIdentity(identityVerification));
    }

}