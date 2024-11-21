package org.springcorepractice.walletapplication.infrastructure.exceptions;

import org.springcorepractice.walletapplication.WalletApplication;
import org.springcorepractice.walletapplication.domain.exceptions.WalletException;

public class FileException extends WalletException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}
