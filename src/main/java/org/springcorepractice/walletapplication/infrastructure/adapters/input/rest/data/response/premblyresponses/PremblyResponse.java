package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class PremblyResponse {
    @JsonProperty("status")
    private boolean verificationCallSuccessful;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("response_code")
    private String responseCode;

    @JsonProperty("verification")
    private Verification verification;

    @JsonProperty("session")
    private Object session;

    @JsonProperty("widget_info")
    private Object wiget;

    @JsonProperty("endpoint_name")
    private String endpointName;

}
