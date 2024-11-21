package org.springcorepractice.walletapplication.infrastructure.adapters.output.paymentmanager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springcorepractice.walletapplication.application.output.paymentmanager.PaymentPayManagerOutPutPort;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springcorepractice.walletapplication.infrastructure.adapters.config.PaystackConfiguration;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request.PaystackRequest;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackResponse;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaystackAdapter implements PaymentPayManagerOutPutPort {

    private final PaystackConfiguration paystackConfiguration;
    private final RestTemplate restTemplate;


    @Override
    public PaystackResponse initialisePayment(PaystackRequest paystackRequest) throws IdentityManagerException {
        IdentityValidator.validatePaystackInput(paystackRequest);
        String URL = paystackConfiguration.getPayInitialise_url();
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity<PaystackRequest> requestHttpEntity = new HttpEntity<>(paystackRequest, httpHeaders);
        ResponseEntity<PaystackResponse> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestHttpEntity,
                PaystackResponse.class
        );
        return responseEntity.getBody();
    }


    @Override
    public PaystackVerificationResponse verifyPayment(String reference) throws IdentityManagerException {
        IdentityValidator.validatePaystackReference(reference);
        HttpHeaders headers = getHttpHeaders();
        String verifyUrl = paystackConfiguration.getVerifyPayment_url() + reference;
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<PaystackVerificationResponse> response = restTemplate.exchange(
                verifyUrl,
                HttpMethod.GET,
                requestEntity,
                PaystackVerificationResponse.class
        );
        log.info("RequestEntity... {}", requestEntity);
        log.info("ResponseBody... {}", response.getBody());
        return response.getBody();
    }


    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", APPLICATION_JSON_VALUE);
        httpHeaders.set("Accept", APPLICATION_JSON_VALUE);
        httpHeaders.set("Authorization", "Bearer " + paystackConfiguration.getAuthorization_key());
        return httpHeaders;
    }


}
