package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class PaystackVerificationResponse {
    private boolean status;
    private String message;
    private PaystackData data;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class PaystackData {
        private Long id;
        private String domain;
        private String status;
        private String reference;
        private String receipt_number;
        private BigDecimal amount;  // Amount in kobo
        private String message;
        private String gateway_response;
        private String paid_at;
        private String created_at;
        private String channel;
        private String currency;
        private String ip_address;
        private String metadata;
        private PaystackLog log;
        private Long fees;
        private PaystackAuthorization authorization;
        private PaystackCustomer customer;
        private String plan;
        private String order_id;
        private String paidAt;
        private String createdAt;
        private Long requested_amount;
        private String transaction_date;

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class PaystackLog {
            private Long start_time;
            private Long time_spent;
            private int attempts;
            private int errors;
            private boolean success;
            private boolean mobile;
            private List<PaystackLogHistory> history;

            @Getter
            @Setter
            @AllArgsConstructor
            @NoArgsConstructor
            public static class PaystackLogHistory {
                private String type;
                private String message;
                private Long time;
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class PaystackAuthorization {
            private String authorization_code;
            private String bin;
            private String last4;
            private String exp_month;
            private String exp_year;
            private String channel;
            private String card_type;
            private String bank;
            private String country_code;
            private String brand;
            private boolean reusable;
            private String signature;
            private String account_name;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        public static class PaystackCustomer {
            private Long id;
            private String first_name;
            private String last_name;
            private String email;
            private String customer_code;
            private String phone;
            private String metadata;
            private String risk_action;
            private String international_format_phone;
        }
    }
}
