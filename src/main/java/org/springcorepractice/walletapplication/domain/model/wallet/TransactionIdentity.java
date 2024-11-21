package org.springcorepractice.walletapplication.domain.model.wallet;

import lombok.*;
import org.springcorepractice.walletapplication.domain.enums.TransactionStatus;
import org.springcorepractice.walletapplication.domain.enums.TransactionType;

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
    private TransactionStatus transactionStatus;
    private String paystackReference;
    private LocalDateTime timestamp;
    private String description;
    private String walletId;
    private String authorizationUrl;
}
