package org.springcorepractice.walletapplication.domain.exceptions;

public class IdentityVerificationException extends Exception{
    public IdentityVerificationException(String message) {
        super(message);
    }

    public IdentityVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityVerificationException(Throwable cause) {
        super(cause);
    }
}
