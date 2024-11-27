package org.springcorepractice.walletapplication.domain.model.wallet;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springcorepractice.walletapplication.domain.enums.TransactionStatus;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.domain.enums.constants.TransactionMessage;
import org.springcorepractice.walletapplication.domain.exceptions.TransactionException;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionIdentity {
    private String id;
    private BigDecimal amount;
    private TransactionType type;
    private String transactionStatus;
    private String paystackReference;
    private LocalDateTime timestamp;
    private String timeInitiated;
    private String description;
    private String walletId;
    private String authorizationUrl;
    private String userEmail;
    private PaystackVerificationResponse paystackVerificationResponse;

}
