package org.springcorepractice.walletapplication.infrastructure.exceptions;

import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;

public class InvalidWebhookException extends IdentityManagerException {
    public InvalidWebhookException(String message) {
        super(message);
    }

    public InvalidWebhookException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidWebhookException(Throwable cause) {
        super(cause);
    }
}
