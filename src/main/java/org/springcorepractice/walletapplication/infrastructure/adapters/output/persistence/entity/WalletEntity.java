package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Builder
@Document(collection = "wallet")
public class WalletEntity {
    @Id
    private String id;
    private BigDecimal balance;
    private String createdAt;
    private String updatedAt;
    private boolean isWalletActive;
    private String userId;
    @DBRef
    private List<TransactionEntity> transactionEntity;
}
