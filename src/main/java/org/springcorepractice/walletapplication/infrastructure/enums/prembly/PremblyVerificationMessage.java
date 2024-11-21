package org.springcorepractice.walletapplication.infrastructure.enums.prembly;

import lombok.Getter;

@Getter
public enum PremblyVerificationMessage {

        SERVICE_UNAVAILABLE("This Service is Unavailable. Please try again in a few minutes."),
        NIN_NOT_FOUND("This NIN cannot be found. Please provide a correct NIN."),
        VERIFICATION_SUCCESSFUL("Verification Successful"),
        NIN_VERIFIED("Verified"),
        VERIFICATION_UNSUCCESSFUL("Verification Unsuccessful"),
        PREMBLY_UNAVAILABLE("Prembly server error."),
        INSUFFICIENT_WALLET_BALANCE("Insufficient wallet balance"),
        PREMBLY_FACE_CONFIRMATION("Liveliness check failed: Face Occluded.... kindly try better positioning"),
        PREMBY_FACE_DOES_NOT_MATCH("Face does not match");


        private final String message;

        PremblyVerificationMessage(String message){
            this.message = message;
        }
}