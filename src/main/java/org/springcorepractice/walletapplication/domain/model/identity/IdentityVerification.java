package org.springcorepractice.walletapplication.domain.model.identity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IdentityVerification {
    private String nin;
    private String bvn;
    private String imageUrl;
}
