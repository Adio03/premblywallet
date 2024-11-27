//package org.springcorepractice.walletapplication.domain.service.identityverification;
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.mockito.Mock;
//import org.springcorepractice.walletapplication.application.input.identity.IdentityVerificationUseCase;
//import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationOutputPort;
//import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
//import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.*;
//import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyResponseCode;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//
//@SpringBootTest
//@Slf4j
//public class IdentitiyVerificationServiceTest {
//@Mock
//private IdentityVerificationUseCase verificationUseCase;
//@Mock
//private IdentityVerificationOutputPort identityVerificationOutputPort;
//private IdentityVerification identityVerification;
//private IdentityVerification bvnIdentityVerification;
//private LivelinessVerification livelinessVerification;
//private PremblyResponse premblyResponse;
//private PremblyBvnResponse premblyBvnResponse;
//
//@BeforeEach
//void setUp() {
//    bvnIdentityVerification = IdentityVerification.builder().identityNumber("12345678901").
//            imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/oebgq4kj0b0n3avrtxqa.jpg").build();
//
//            identityVerification = IdentityVerification.builder().identityNumber("23455677163").
//                    imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/oebgq4kj0b0n3avrtxqa.jpg").build();
//
//            livelinessVerification = LivelinessVerification.builder().
//                    imageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027769/oebgq4kj0b0n3avrtxqa.jpg").build();
//
//
//            premblyResponse = PremblyResponse.builder()
//                    .responseCode(PremblyResponseCode.SUCCESSFUL.getCode())
//                    .verificationCallSuccessful(true)
//                    .faceData(PremblyFaceData.builder()
//                            .responseCode(PremblyResponseCode.SUCCESSFUL.getCode())
//                            .faceVerified(true)
//                            .build())
//                    .verification(Verification.builder()
//                            .validIdentity(true)
//                            .build())
//                    .ninData(PremblyResponse.NinData.builder()
//                            .nin(identityVerification.getIdentityNumber())
//                            .build())
//                    .build();
//        }
//
//        @Test
//        void verifyIdentityWithValidNinAndValidImage() {
//            when(verificationUseCase.verifyNinIdentity(identityVerification)).thenReturn(premblyResponse);
//            PremblyResponse response = verificationUseCase.verifyBvnIdentity(identityVerification);
//            assertNotNull(response);
//            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
//            assertTrue(response.isVerificationCallSuccessful());
//            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getFaceData().getResponseCode());
//            assertTrue(response.getFaceData().isFaceVerified());
//            assertTrue(response.getVerification().isValidIdentity());
//            assertEquals(identityVerification.getIdentityNumber(), response.getNinData().getNin());
//            verify(verificationUseCase, times(1)).verifyBvnIdentity(identityVerification);
//            when(identityVerificationOutputPort.save(response)).thenReturn(response);
//        }
//
////        @Test
////        void verifyIdentityWithValidAndImageDoesNotMatch() throws IdentityManagerException, IdentityVerificationException {
////            identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
////            PremblyResponse mockResponse = PremblyResponse.builder()
////                    .verificationCallSuccessful(true)
////                    .faceData(PremblyFaceData.builder()
////                            .responseCode(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode())
////                            .faceVerified(false)
////                            .build())
////                    .verification(Verification.builder()
////                            .validIdentity(true)
////                            .status(PremblyVerificationMessage.VERIFIED.getMessage())
////                            .build())
////                    .build();
////            when(identityVerificationManagerOutputPort.verifyIdentity(identityVerification)).thenReturn(mockResponse);
////            PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
////            assertNotNull(response);
////            assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getFaceData().getResponseCode());
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyVerificationMessage.VERIFIED.getMessage(), response.getVerification().getStatus());
////            assertFalse(response.getFaceData().isFaceVerified());
////            assertTrue(response.getVerification().isValidIdentity());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////
////
////        @Test
////        void verifyIdentityWithInvalidNin() throws IdentityManagerException, IdentityVerificationException {
////            identityVerification.setIdentityNumber("12345678901");
////            PremblyResponse mockResponse = PremblyResponse.builder()
////                    .verificationCallSuccessful(true)
////                    .responseCode(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode())
////                    .build();
////            when(identityVerificationManagerOutputPort.verifyIdentity(identityVerification)).thenReturn(mockResponse);
////            PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
////            log.info("......{}", response);
////            assertNotNull(response);
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getResponseCode());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////
////
////        @Test
////        void verifyIdentityWhenImageIsNotPosition() throws IdentityManagerException, IdentityVerificationException {
////            identityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732027712/ez15xfsdj3whhd5kwscs.jpg");
////            PremblyResponse mockResponse = PremblyResponse.builder()
////                    .verificationCallSuccessful(true)
////                    .faceData(PremblyFaceData.builder()
////                            .faceVerified(false)
////                            .message(PremblyVerificationMessage.PREMBLY_FACE_CONFIRMATION.getMessage())
////                            .build())
////                    .build();
////            when(identityVerificationManagerOutputPort.verifyIdentity(identityVerification)).thenReturn(mockResponse);
////            PremblyResponse response = identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
////            assertNotNull(response);
////            assertTrue(response.isVerificationCallSuccessful());
////            assertFalse(response.getFaceData().isFaceVerified());
////            assertEquals(PremblyVerificationMessage.PREMBLY_FACE_CONFIRMATION.getMessage(), response.getFaceData().getMessage());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////
////
////        @Test
////        void verifyIdentityWhenBalanceIsInsufficient() throws IdentityManagerException, IdentityVerificationException {
////            PremblyResponse insufficientBalanceResponse = PremblyResponse.builder()
////                    .responseCode(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode())
////                    .verificationCallSuccessful(false)
////                    .build();
////
////            when(identityVerificationManagerOutputPort.verifyIdentity(identityVerification))
////                    .thenReturn(insufficientBalanceResponse);
////
////            PremblyResponse response =identityVerificationManagerOutputPort.verifyIdentity(identityVerification);
////            assertNotNull(response);
////            assertEquals(PremblyResponseCode.INSUFFICIENT_WALLET_BALANCE.getCode(), response.getResponseCode());
////            assertFalse(response.isVerificationCallSuccessful());
////
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////        //    @Test
////        void verifyLivelinessYTest(){
////            PremblyLivelinessResponse livelinessResponse = identityVerificationManagerOutputPort.verifyLiveliness(livelinessVerification);
////            assertNotNull(livelinessResponse);
////            log.info("Response...{}",livelinessResponse);
////            assertTrue(livelinessResponse.isStatus());
////            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(),livelinessResponse.getResponseCode());
////            assertTrue(livelinessResponse.getVerification().isValidIdentity());
////        }
////        @Test
////        void verifyIdentityWithNullIdentityVerification() throws IdentityManagerException, IdentityVerificationException {
////
////            when(identityVerificationManagerOutputPort.verifyIdentity(null))
////                    .thenThrow(new IdentityVerificationException("IdentityVerification should not be empty"));
////
////            IdentityVerificationException exception = assertThrows(
////                    IdentityVerificationException.class,
////                    () -> identityVerificationManagerOutputPort.verifyIdentity(null)
////            );
////            assertEquals("IdentityVerification should not be empty", exception.getMessage());
////
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(null);
////        }
////
////        @ParameterizedTest
////        @NullSource
////        @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
////        void verifyIdentityWithEmptyIdentityId(String nin) throws IdentityManagerException, IdentityVerificationException {
////            identityVerification.setIdentityNumber(nin);
////            when(identityVerificationManagerOutputPort.
////                    verifyIdentity(identityVerification)).thenThrow(new IdentityManagerException(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage()));
////            IdentityManagerException exception =
////                    assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyIdentity(identityVerification));
////            assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////
////        @ParameterizedTest
////        @NullSource
////        @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
////        void verifyIdentityWithNullIdentityImage(String url) throws IdentityManagerException, IdentityVerificationException {
////            identityVerification.setImageUrl(url);
////            when(identityVerificationManagerOutputPort.
////                    verifyIdentity(identityVerification)).thenThrow(new IdentityManagerException(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage()));
////            IdentityManagerException exception =
////                    assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyIdentity(identityVerification));
////            assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyIdentity(identityVerification);
////        }
////
////        void verifyIdentityWithValidBvnAndValidImage() throws IdentityManagerException, IdentityVerificationException {
////            PremblyBvnResponse response = identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification);
////            log.info("Prembly {}",response);
////            assertNotNull(response);
////            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getData().getFaceData().getResponseCode());
////            assertTrue(response.getData().getFaceData().isFaceVerified());
////            assertTrue(response.getVerification().isValidIdentity());
////            assertEquals(bvnIdentityVerification.getIdentityNumber(), response.getData().getBvn());
////        }
////        @Test
////        void verifyIdentityWithValidBvnAndValidImageTest() throws IdentityManagerException, IdentityVerificationException {
////            PremblyFaceData faceData = PremblyFaceData.builder().faceVerified(Boolean.TRUE).
////                    responseCode(PremblyResponseCode.SUCCESSFUL.getCode()).build();
////            PremblyBvnResponse.BvnData data =
////                    PremblyBvnResponse.BvnData.builder().faceData(faceData).bvn("22237509882").build();
////            Verification verification =  Verification.builder().validIdentity(Boolean.TRUE).build();
////
////            PremblyBvnResponse mockResponse = PremblyBvnResponse.builder().
////                    responseCode(PremblyResponseCode.SUCCESSFUL.getCode()).
////                    verificationCallSuccessful(Boolean.TRUE).data(data).verification(verification).build();
////
////            when(identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification)).thenReturn(mockResponse);
////            PremblyBvnResponse response = identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification);
////            assertNotNull(response);
////            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getResponseCode());
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyResponseCode.SUCCESSFUL.getCode(), response.getData().getFaceData().getResponseCode());
////            assertTrue(response.getData().getFaceData().isFaceVerified());
////            assertTrue(response.getVerification().isValidIdentity());
////            assertEquals(bvnIdentityVerification.getIdentityNumber(), response.getData().getBvn());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyBvn(bvnIdentityVerification);
////        }
////        @Test
////        void verifyIdentityWithValidBvnAndImageThatDoesNotMatch() throws IdentityManagerException, IdentityVerificationException {
////            bvnIdentityVerification.setImageUrl("https://res.cloudinary.com/drhrd1xkn/image/upload/v1732042468/gi2ppo8hsivajcn74idz.jpg");
////            PremblyFaceData faceData = PremblyFaceData.builder().faceVerified(Boolean.FALSE).
////                    responseCode(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode()).build();
////            PremblyBvnResponse.BvnData data =
////                    PremblyBvnResponse.BvnData.builder().faceData(faceData).bvn("22237509882").build();
////            Verification verification =  Verification.builder().status(PremblyVerificationMessage.VERIFIED.getMessage()).validIdentity(Boolean.TRUE).build();
////
////            PremblyBvnResponse mockResponse = PremblyBvnResponse.builder().
////                    responseCode(PremblyResponseCode.SUCCESSFUL.getCode()).
////                    verificationCallSuccessful(Boolean.TRUE).data(data).verification(verification).build();
////
////            when(identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification)).thenReturn(mockResponse);
////            PremblyBvnResponse response = identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification);
////            assertNotNull(response);
////            assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getData().getFaceData().getResponseCode());
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyVerificationMessage.VERIFIED.getMessage(), response.getVerification().getStatus());
////            assertFalse(response.getData().getFaceData().isFaceVerified());
////            assertTrue(response.getVerification().isValidIdentity());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyBvn(bvnIdentityVerification);
////        }
////        @Test
////        void verifyIdentityWithInvalidBvn() throws IdentityManagerException, IdentityVerificationException {
////            bvnIdentityVerification.setIdentityNumber("12345678901");
////            PremblyBvnResponse mockResponse = PremblyBvnResponse.builder()
////                    .verificationCallSuccessful(true)
////                    .responseCode(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode())
////                    .build();
////            when(identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification)).thenReturn(mockResponse);
////            PremblyBvnResponse response = identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification);
////            log.info("{}", response);
////            assertNotNull(response);
////            assertTrue(response.isVerificationCallSuccessful());
////            assertEquals(PremblyResponseCode.SUCCESSFUL_RECORD_NOT_FOUND.getCode(), response.getResponseCode());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyBvn(bvnIdentityVerification);
////        }
////        @ParameterizedTest
////        @NullSource
////        @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
////        void verifyIdentityWithEmptyIdentityNumber(String bvn) throws IdentityManagerException, IdentityVerificationException {
////            bvnIdentityVerification.setIdentityNumber(bvn);
////            when(identityVerificationManagerOutputPort.
////                    verifyBvn(identityVerification)).thenThrow(new IdentityManagerException(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage()));
////            IdentityManagerException exception =
////                    assertThrows(IdentityManagerException.class, () -> identityVerificationManagerOutputPort.verifyBvn(bvnIdentityVerification));
////            assertEquals(IdentityMessage.EMPTY_INPUT_FIELD_ERROR.getMessage(),exception.getMessage());
////            verify(identityVerificationManagerOutputPort, times(1)).verifyBvn(bvnIdentityVerification);
////        }
////
//
//    }
//
//
//
//
//
