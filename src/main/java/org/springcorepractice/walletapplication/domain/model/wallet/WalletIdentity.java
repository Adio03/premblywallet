package org.springcorepractice.walletapplication.domain.model.wallet;

import lombok.*;
import org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity.TransactionEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WalletIdentity {
    private String id;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
    private boolean isWalletActive;
    private String userId;
    private BigDecimal amount;
    private String decription;
    private List<TransactionIdentity> transactionIdentities = new ArrayList<>();
}

