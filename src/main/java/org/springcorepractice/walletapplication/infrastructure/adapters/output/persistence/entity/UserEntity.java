package org.springcorepractice.walletapplication.infrastructure.adapters.output.persistence.entity;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
//@AllArgsConstructor
//@NoArgsConstructor
@Document
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean emailVerified;
    private boolean enabled;
    private IdentityRole identityRole;
    private String createdAt;
    private String createdBy;
    private String imageUrl;
    private String nin;
    @DBRef
    private WalletEntity walletEntity;

}
