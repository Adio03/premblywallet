package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response;

import lombok.*;

@Getter
@Setter
@ToString
public class PaystackResponse {
    private boolean status;
    private String message;
    private PaystackResponses data;


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @lombok.ToString
    public static class PaystackResponses{
        private String authorization_url;
        private String access_code;
        private String reference;
    }
}
