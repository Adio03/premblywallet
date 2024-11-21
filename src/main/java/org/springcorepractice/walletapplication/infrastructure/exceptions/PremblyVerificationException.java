package org.springcorepractice.walletapplication.infrastructure.exceptions;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;

public class PremblyVerificationException extends IdentityManagerException {
    public PremblyVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PremblyVerificationException(Throwable cause) {
        super(cause);
    }

    public PremblyVerificationException(String message) {
        super(message);
    }
}
