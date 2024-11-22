package org.springcorepractice.walletapplication.infrastructure.adapters.output.identityverification;

import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationManagerOutputPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PremblyResponse;
import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PremblyAdapter implements IdentityVerificationManagerOutputPort {
    @Value("${PREMBLY_URL}")
    private String premblyUrl;


    @Value("${PREMBLY_APP_ID}")
    private String appId;

    @Value("${PREMBLY_APP_KEY}")
    private String apiKey;

    @Override
    public PremblyResponse verifyIdentity(IdentityVerification identityVerification) throws IdentityManagerException {
        return getNinDetails(identityVerification);
    }

    public PremblyResponse getNinDetails(IdentityVerification verificationRequest) throws IdentityManagerException {
        IdentityValidator.validateIdentityVerificationRequest(verificationRequest);
        PremblyResponse premblyResponse = getIdentityDetailsByNin(verificationRequest);
        premblyResponse.getVerification().updateValidNin();
        log.info("Verification Result : {}", premblyResponse);
        return premblyResponse;
    }

//    private ResponseEntity<PremblyResponse> getIdentityDetailsByNin(IdentityVerification verificationRequest) {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = getHttpHeaders();
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add(PremblyParameter.NIN_NUMBER.getValue(), verificationRequest.getNin());
//        formData.add(PremblyParameter.NIN_IMAGE.getValue(), verificationRequest.getImageUrl());
//        log.info("Form Data... {}", formData);
//        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
//        String url = premblyUrl.concat(PremblyParameter.NIN_FACE_URL.getValue());
//        log.info(url);
//        ResponseEntity<PremblyResponse> responseEntity = ResponseEntity.ofNullable(PremblyResponse.builder().build());
//        log.info("Response ---> {}",responseEntity.getBody());
//        try {
//            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyResponse.class);
//        } catch (HttpServerErrorException ex) {
//            log.info("Prembly server error {}", ex.getMessage());
//            log.error("Prembly Server error {}", ex.getMessage());
//        }
//        return responseEntity;
//    }

    private PremblyResponse getIdentityDetailsByNin(IdentityVerification verificationRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(PremblyParameter.NIN_NUMBER.getValue(), verificationRequest.getNin());
        formData.add(PremblyParameter.NIN_IMAGE.getValue(), verificationRequest.getImageUrl());
        log.info("Form Data... {}", formData);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formData, headers);
        String url = premblyUrl.concat(PremblyParameter.NIN_FACE_URL.getValue());
        log.info(url);
        ResponseEntity<PremblyResponse> responseEntity = ResponseEntity.ofNullable(PremblyResponse.builder().build());
        log.info("Response ---> {}",responseEntity.getBody());
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyResponse.class);
        } catch (HttpServerErrorException ex) {
            log.info("Prembly server error {}", ex.getMessage());
            log.error("Prembly Server error {}", ex.getMessage());
        }
        return responseEntity.getBody();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PremblyParameter.ACCEPT.getValue(), PremblyParameter.APPLICATION_JSON.getValue());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(PremblyParameter.APP_ID.getValue(), appId);
        headers.add(PremblyParameter.API_KEY.getValue(), apiKey);
        return headers;
    }
}


