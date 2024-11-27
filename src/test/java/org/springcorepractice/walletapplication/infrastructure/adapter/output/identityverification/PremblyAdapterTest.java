package org.springcorepractice.walletapplication.infrastructure.adapter.output.identityverification;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationManagerOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityVerificationException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.*;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.identityverification.PremblyAdapter;
import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyResponseCode;
import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyVerificationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@Slf4j
public class PremblyAdapterTest {
    @Autowired
    private IdentityVerificationManagerOutputPort identityVerificationManagerOutputPort;
    private IdentityVerification identityVerification;
    private IdentityVerification bvnIdentityVerification;
    private IdentityVerification ninIdentityVerification;
    private IdentityVerification livelinessVerification;
    @Mock
    private PremblyAdapter premblyAdapter;


    @BeforeEach
    void setUp() {
        bvnIdentityVerification = IdentityVerification.builder().nin("1234567890").
                imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/.jpg").build();

        identityVerification = IdentityVerification.builder().nin("12345678903").
                imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/.jpg").build();

        livelinessVerification = IdentityVerification.builder().
                imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/.jpg").build();

        ninIdentityVerification = IdentityVerification.builder().nin("1234567890").build();



    }

    @Test
    void verifyIdentityWithValidNinAndValidImage() throws IdentityManagerException, IdentityVerificationException {
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationManagerOutputPort.verifyNinLikeness(identityVerification);
        log.info("Response {}",response);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getFaceData().getResponseCode());
        assertTrue(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
        assertEquals(identityVerification.getNin(), response.getNinData().getNin());
    }

    @Test
    void verifyIdentityWithValidAndImageDoesNotMatch() throws IdentityManagerException, IdentityVerificationException {
        identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getFaceData().getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyVerificationMessage.VERIFIED.getMessage(), response.getVerification().getStatus());
        assertFalse(response.getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
    }


    @Test
    void verifyIdentityWithInvalidNin() throws IdentityManagerException, IdentityVerificationException {
        identityVerification.setNin("12345678901");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationManagerOutputPort.verifyNinLikeness(identityVerification);
        log.info("......{}", response);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getResponseCode());
    }


    @Test
    void verifyIdentityWhenImageIsNotPosition() throws IdentityManagerException, IdentityVerificationException {
        identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027712/ez15xfsdj3whhd5kwscs.jpg");
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationManagerOutputPort.verifyNinLikeness(identityVerification);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertFalse(response.getFaceData().isFaceVerified());
        assertEquals(PremblyVerificationMessage.PREMBLY_FACE_CONFIRMATION.getMessage(), response.getFaceData().getMessage());
    }


    @Test
    void verifyIdentityWhenBalanceIsInsufficient() throws IdentityManagerException, IdentityVerificationException {
        PremblyNinResponse insufficientBalanceResponse = PremblyNinResponse.builder()
                .responseCode(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode())
                .verificationCallSuccessful(false)
                .build();

        when(premblyAdapter.verifyNinLikeness(identityVerification))
                .thenReturn(insufficientBalanceResponse);

        PremblyNinResponse response = (PremblyNinResponse) premblyAdapter.verifyNinLikeness(identityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode(), response.getResponseCode());
        assertFalse(response.isVerificationCallSuccessful());

        verify(premblyAdapter, times(1)).verifyIdentity(identityVerification);
    }
    @Test
    void verifyLivelinessYTest(){
        PremblyLivelinessResponse livelinessResponse = (PremblyLivelinessResponse) identityVerificationManagerOutputPort.verifyLiveliness(livelinessVerification);
        assertNotNull(livelinessResponse);
        log.info("Response...{}",livelinessResponse);
        assertTrue(livelinessResponse.isStatus());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),livelinessResponse.getResponseCode());
        assertTrue(livelinessResponse.getVerification().isValidIdentity());
    }
    @Test
    void verifyIdentityWithNullIdentityVerification() throws IdentityManagerException {
        IdentityVerificationException exception = assertThrows(
                IdentityVerificationException.class,
                () -> identityVerificationManagerOutputPort.verifyNinLikeness(null)
        );
        assertEquals(IdentityMessage.IDENTITY_SHOULD_NOT_BE_NULL.getMessage(), exception.getMessage());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithEmptyIdentityId(String nin) throws IdentityManagerException {
        identityVerification.setNin(nin);
        IdentityManagerException exception =
                assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyNinLikeness(identityVerification));
        assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithNullIdentityImage(String url) throws IdentityManagerException {
        identityVerification.setImageUrl(url);
        IdentityManagerException exception =
                assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyNinLikeness(identityVerification));
        assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());

    }
//    @Test
    void verifyIdentityWithValidBvnAndValidImage() throws IdentityManagerException, IdentityVerificationException {
        PremblyBvnResponse response = (PremblyBvnResponse) identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification);
        log.info("Prembly {}",response);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getData().getFaceData().getResponseCode());
        assertTrue(response.getData().getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
        assertEquals(bvnIdentityVerification.getNin(), response.getData().getBvn());
    }
        @Test
        void verifyIdentityWithValidBvnAndValidImageTest() throws IdentityManagerException, IdentityVerificationException {
            PremblyBvnResponse response = (PremblyBvnResponse) identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification);
            assertNotNull(response);
            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
            assertTrue(response.isVerificationCallSuccessful());
            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getData().getFaceData().getResponseCode());
            assertTrue(response.getData().getFaceData().isFaceVerified());
            assertTrue(response.getVerification().isValidIdentity());
            assertEquals(bvnIdentityVerification.getNin(), response.getData().getBvn());
        }
    @Test
    void verifyIdentityWithValidBvnAndImageThatDoesNotMatch() throws IdentityManagerException, IdentityVerificationException {
        bvnIdentityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
        PremblyBvnResponse response = (PremblyBvnResponse) identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getData().getFaceData().getResponseCode());
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyVerificationMessage.VERIFIED.getMessage(), response.getVerification().getStatus());
        assertFalse(response.getData().getFaceData().isFaceVerified());
        assertTrue(response.getVerification().isValidIdentity());
    }
    @Test
    void verifyIdentityWithInvalidBvn() throws IdentityManagerException, IdentityVerificationException {
        bvnIdentityVerification.setNin("12345678901");
        PremblyResponse response = identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification);
        log.info("{}", response);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getResponseCode());
    }
    @Test
    void verifyBvnLikenessIdentityWithNullIdentityVerification() throws IdentityManagerException {
        IdentityVerificationException exception = assertThrows(
                IdentityVerificationException.class,
                () -> identityVerificationManagerOutputPort.verifyBvnLikeness(null)
        );
        assertEquals(IdentityMessage.IDENTITY_SHOULD_NOT_BE_NULL.getMessage(), exception.getMessage());

    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyIdentityWithEmptyIdentityNumber(String bvn) {
        bvnIdentityVerification.setNin(bvn);
        IdentityVerificationException  exception =
                assertThrows(IdentityVerificationException.class, () -> identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification));
        assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
    }
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void verifyBvnLikenessIdentityWithNullIdentityImage(String url) {
        bvnIdentityVerification.setImageUrl(url);
        IdentityVerificationException exception =
                assertThrows(IdentityVerificationException.class, () -> identityVerificationManagerOutputPort.verifyBvnLikeness(bvnIdentityVerification));
        assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());

    }

    @Test
    void verifyBvnLikenessIdentityWhenBalanceIsInsufficient() throws IdentityVerificationException {
        PremblyBvnResponse insufficientBalanceResponse = PremblyBvnResponse.builder()
                .responseCode(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode())
                .verificationCallSuccessful(false)
                .build();

        when(premblyAdapter.verifyBvn(bvnIdentityVerification))
                .thenReturn(insufficientBalanceResponse);

        PremblyResponse response =premblyAdapter.verifyBvnLikeness(bvnIdentityVerification);
        assertNotNull(response);
        assertEquals(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode(), response.getResponseCode());
        assertFalse(response.isVerificationCallSuccessful());

        verify(premblyAdapter, times(1)).verifyBvnLikeness(bvnIdentityVerification);
    }
    @Test
    void verifyNinIdentity() throws IdentityManagerException, IdentityVerificationException {
        PremblyNinResponse response = (PremblyNinResponse) identityVerificationManagerOutputPort.verifyIdentity(ninIdentityVerification);
        assertNotNull(response);
        assertTrue(response.isVerificationCallSuccessful());
        assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),response.getResponseCode());
        assertEquals(response.getNinData().getNin(),ninIdentityVerification.getNin());
    }

}


