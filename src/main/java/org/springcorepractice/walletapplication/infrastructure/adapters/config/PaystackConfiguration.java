package org.springcorepractice.walletapplication.infrastructure.adapters.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PaystackConfiguration {
    @Value("${payStack_authorization_key}")
    private String authorization_key;
    @Value("${payStack_paymentInitialise_url}")
    private String payInitialise_url;
    @Value("${paystack_verifyPayment_url}")
    private String verifyPayment_url;
}
