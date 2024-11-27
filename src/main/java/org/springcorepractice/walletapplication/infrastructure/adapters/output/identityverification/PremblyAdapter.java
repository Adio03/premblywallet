package org.springcorepractice.walletapplication.infrastructure.adapters.output.identityverification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.output.identity.IdentityVerificationManagerOutputPort;
import org.springcorepractice.walletapplication.domain.enums.constants.IdentityMessage;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityVerificationException;
import org.springcorepractice.walletapplication.domain.model.identity.IdentityVerification;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyBvnResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyLivelinessResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyNinResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses.PremblyResponse;
import org.springcorepractice.walletapplication.infrastructure.enums.prembly.PremblyParameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j@RequiredArgsConstructor
public class PremblyAdapter implements IdentityVerificationManagerOutputPort {
    @Value("${PREMBLY_URL}")
    private String premblyUrl;

    @Value("${PREMBLY_APP_ID}")
    private String appId;

    @Value("${PREMBLY_APP_KEY}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public PremblyResponse verifyIdentity(IdentityVerification identityVerification) throws IdentityManagerException, IdentityVerificationException {
        IdentityValidator.validateIdentityVerificationRequest(identityVerification);
        if (identityVerification.getNin() != null) {
            if (identityVerification.getImageUrl() != null) {
                return verifyNinLikeness(identityVerification);
            } else {
                return verifyNin(identityVerification);
            }

        } else if (identityVerification.getBvn() != null) {
            if (identityVerification.getImageUrl() != null) {
                return verifyBvnLikeness(identityVerification);
            } else {
                return verifyBvn(identityVerification);
            }
        } else {
            throw new IdentityVerificationException("Either NIN or BVN must be provided.");
        }
    }

    @Override
    public PremblyResponse verifyNinLikeness(IdentityVerification identityVerification) throws IdentityManagerException, IdentityVerificationException {
        return getNinDetails(identityVerification);
    }

    public PremblyResponse getNinDetails(IdentityVerification identityVerification) throws IdentityManagerException {
        PremblyResponse premblyResponse = getIdentityDetailsByNin(identityVerification);
        premblyResponse.getVerification().updateValidIdentity();
        log.info("Response: {}", premblyResponse);
        return premblyResponse;
    }

    private PremblyResponse getIdentityDetailsByNin(IdentityVerification identityVerification) {
        HttpEntity<MultiValueMap<String, String>> entity = createRequestEntity(identityVerification);
        String url = premblyUrl.concat(PremblyParameter.NIN_FACE_URL.getValue());
        log.info(url);
        ResponseEntity<PremblyNinResponse> responseEntity = ResponseEntity.ofNullable(PremblyNinResponse.builder().build());
        log.info("Response {}",responseEntity.getBody());
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyNinResponse.class);
        } catch (HttpServerErrorException ex) {
            log.info("Prembly server error {}", ex.getMessage());
            log.error("Prembly Server error {}", ex.getMessage());
        }
        return responseEntity.getBody();
      }

    @Override
    public PremblyResponse verifyNin(IdentityVerification identityVerification) throws IdentityVerificationException {
        validateInput(identityVerification);
        String URL = premblyUrl.concat(PremblyParameter.NIN_URL.getValue());
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(PremblyParameter.NIN.getValue(), identityVerification.getNin());
        HttpEntity<Map<String, String>> requestHttpEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<PremblyNinResponse> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                PremblyNinResponse.class
        );

        return responseEntity.getBody();
    }



    @Override
    public PremblyResponse verifyBvnLikeness(IdentityVerification identityVerification) throws IdentityVerificationException {
        IdentityValidator.validateIdentityVerificationRequest(identityVerification);
        return getBvnDetails(identityVerification);
    }

    public PremblyResponse getBvnDetails(IdentityVerification identityVerification) {
        PremblyResponse premblyBvnResponse = getIdentityDetailsByBvn(identityVerification);
        premblyBvnResponse.getVerification().updateValidIdentity();
        log.info("Verification Result : {}", premblyBvnResponse);
        return premblyBvnResponse;
    }

    private PremblyResponse getIdentityDetailsByBvn(IdentityVerification verificationRequest) {
        HttpEntity<MultiValueMap<String, String>> entity = createRequestEntity(verificationRequest);
        log.info("url {}", premblyUrl);
        String url = premblyUrl.concat(PremblyParameter.BVN_FACE.getValue());
        log.info(url);
        ResponseEntity<PremblyBvnResponse> responseEntity = ResponseEntity.ofNullable(PremblyBvnResponse.builder().build());
        log.info("Response...{}",responseEntity.getBody());
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, PremblyBvnResponse.class);
        } catch (HttpServerErrorException ex) {
            log.info("server error {}", ex.getMessage());
            log.error("Server error {}", ex.getMessage());
        }
        return responseEntity.getBody();
    }

    @Override
    public PremblyResponse verifyBvn(IdentityVerification identityVerification) throws IdentityVerificationException {
        validateInput(identityVerification);
        String URL = premblyUrl.concat(PremblyParameter.BVN_URL.getValue());
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put(PremblyParameter.BVN_NUMBER.getValue(), identityVerification.getBvn());
        HttpEntity<Map<String, String>> requestHttpEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<PremblyBvnResponse> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                PremblyBvnResponse.class
        );

        return responseEntity.getBody();
    }

    @Override
    public PremblyResponse verifyLiveliness(IdentityVerification identityVerification) {
        String URL = premblyUrl.concat(PremblyParameter.NIN_LIVENESS_URL.getValue());
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity<IdentityVerification> requestHttpEntity = new HttpEntity<>(identityVerification, httpHeaders);
        ResponseEntity<PremblyLivelinessResponse> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                PremblyLivelinessResponse.class
        );
        return responseEntity.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntity(IdentityVerification verificationRequest) {
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add(PremblyParameter.NUMBER.getValue(), verificationRequest.getNin());
        formData.add(PremblyParameter.IMAGE.getValue(), verificationRequest.getImageUrl());
        log.debug("Prepared form data: {}", formData);
        return new HttpEntity<>(formData, headers);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PremblyParameter.ACCEPT.getValue(), PremblyParameter.APPLICATION_JSON.getValue());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(PremblyParameter.APP_ID.getValue(), appId);
        headers.add(PremblyParameter.API_KEY.getValue(), apiKey);
        return headers;
    }

    public void validateInput(IdentityVerification identityVerification) throws IdentityVerificationException {
        validateIdentityVerifcation(identityVerification);
        if(identityVerification.getImageUrl() != null){
            throw new IdentityVerificationException("only Identity number is require");
        }
    }
    public void validateIdentityVerifcation(IdentityVerification identityVerification) throws IdentityVerificationException {
        if(identityVerification == null){
            throw new IdentityVerificationException(IdentityMessage.IDENTITY_SHOULD_NOT_BE_NULL.getMessage());
        }
    }

}

