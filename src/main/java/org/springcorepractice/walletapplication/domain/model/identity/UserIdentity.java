package org.springcorepractice.walletapplication.domain.model.identity;


import lombok.*;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;
import org.springcorepractice.walletapplication.domain.model.wallet.WalletIdentity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserIdentity {
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
    private String accessToken;
    private String refreshToken;
    private String nin;
    private WalletIdentity walletIdentity;

}
