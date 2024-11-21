package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaystackRequest {
    private String email;
    private BigDecimal amount;
}
