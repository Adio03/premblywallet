package org.springcorepractice.walletapplication.domain.exceptions;

public class WalletException extends RuntimeException {
    public WalletException(String message) {
        super(message);
    }


    public WalletException(String message, Throwable cause) {
        super(message, cause);
    }

    public WalletException(Throwable cause) {
        super(cause);
    }
}