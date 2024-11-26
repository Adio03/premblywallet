package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class PremblyNinsResponse {
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String imageUrl;
    private String nin;
    private boolean verified;

}
