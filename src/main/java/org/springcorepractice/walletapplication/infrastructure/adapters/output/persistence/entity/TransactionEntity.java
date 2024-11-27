package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import org.springcorepractice.walletapplication.domain.enums.TransactionStatus;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;
import org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.PaystackVerificationResponse;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Document(collection = "transactions")
public class TransactionEntity {
    @Id
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
    private PaystackVerificationResponse paystackVerificationResponse;
}
