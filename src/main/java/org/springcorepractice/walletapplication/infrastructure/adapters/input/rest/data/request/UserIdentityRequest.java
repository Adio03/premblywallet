package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.request;

import lombok.*;
import org.springcorepractice.walletapplication.domain.enums.IdentityRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserIdentityRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private IdentityRole role;
}
