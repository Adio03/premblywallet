package org.springcorepractice.walletapplication.domain.exceptions;

public class InvalidInputException extends WalletException {
    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(Throwable cause) {
        super(cause);
    }
}
