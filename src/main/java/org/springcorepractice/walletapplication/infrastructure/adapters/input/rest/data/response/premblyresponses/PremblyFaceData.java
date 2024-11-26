package org.springcorepractice.walletapplication.infrastructure.adapters.input.rest.data.response.premblyresponses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class PremblyFaceData {
        @JsonProperty("status")
        private boolean faceVerified;

        @JsonProperty("message")
        private String message;

        @JsonProperty("confidence")
        private String confidence;

        @JsonProperty("response_code")
        private String responseCode;
    }
