//package org.springcorepractice.walletapplication.infrastructure.adapter.output.persistence;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.ObjectUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationOutputPort;
//import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyFaceData;
//import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyNinResponse;
//import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;
//import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.Verification;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Slf4j
//public class PremblyManagerAdapterTest {
//    @Autowired
//    private IdentityVerificationOutputPort identityVerificationOutputPort;
//
//    private PremblyResponse premblyResponse;
//
//
//    @BeforeEach
//    void setUp(){
//        premblyResponse = PremblyResponse.builder()
//                .verificationCallSuccessful(true)
//                .detail("Verification successful")
//                .responseCode("200")
//                .endpointName("NIN Verification")
//                .userId("userId")
//                .ninData(PremblyResponse.NinData.builder()
//                        .title("Mr.")
//                        .lastname("Doe")
//                        .firstname("John")
//                        .gender("Male")
//                        .birthCountry("Nigeria")
//                        .birthDate("1990-01-01")
//                        .birthLGA("Lagos Mainland")
//                        .birthState("Lagos")
//                        .centralID("123456789")
//                        .educationalLevel("Bachelor's Degree")
//                        .email("john.doe@example.com")
//                        .nin("987654321")
//                        .employmentStatus("Employed")
//                        .height("180cm")
//                        .maritalStatus("Single")
//                        .photo("https://example.com/photo.jpg")
//                        .middleName("A.")
//                        .religion("Christianity")
//                        .telephoneNo("+2348000000000")
//                        .residenceAddress("123 Example Street")
//                        .residenceLGA("Eti-Osa")
//                        .residenceState("Lagos")
//                        .residenceTown("Lekki")
//                        .residenceStatus("Owner")
//                        .selfOriginLGA("Ijebu-Ode")
//                        .selfOriginPlace("Ijebu-Ode")
//                        .selfOriginState("Ogun")
//                        .signature("https://example.com/signature.jpg")
//                        .spokenLanguage("English")
//                        .nokAddress1("456 Example Avenue")
//                        .nokAddress2("Apt 1B")
//                        .nokFirstName("Jane")
//                        .nokLGA("Yaba")
//                        .nokMiddleName("B.")
//                        .nokPostalCode("101212")
//                        .nokState("Lagos")
//                        .nokSurname("Doe")
//                        .nokTown("Surulere")
//                        .oSpokenLang("Yoruba")
//                        .pFirstName("Paul")
//                        .pMiddleName("C.")
//                        .profession("Engineer")
//                        .pSurname("Smith")
//                        .trackingId("ABC123XYZ")
//                        .userId("user_12345")
//                        .vnin("123456789012")
//                        .build())
//                .faceData(PremblyFaceData.builder()
//                        .faceVerified(true)
//                        .message("Face matched successfully")
//                        .confidence("99.99")
//                        .responseCode("200")
//                        .build())
//                        .verification(Verification.builder()
//                                .status("verified")
//                                .reference("ref_123456")
//                                .build())
//                .session(ObjectUtils.NULL)
//                .build();
//
//    }
//
//    @Test
//    void save() {
//        PremblyNinResponse response = identityVerificationOutputPort.save(premblyResponse);
//        log.info("Response");
//        assertNotNull(response);
//        assertNotNull(response.getId());
//        assertEquals(response.getFirstname(),premblyResponse.getNinData().getFirstname());
//        assertEquals(response.getLastname(),premblyResponse.getNinData().getLastname());
//        assertEquals(response.getUserId(),premblyResponse.getUserId());
//    }
//}
