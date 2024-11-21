package org.springcorepractice.walletapplication.infrastructure.exceptions;

import org.springcorepractice.walletapplication.domain.exceptions.WalletException;

public class ImageConverterException extends WalletException {
    public ImageConverterException(String message) {
        super(message);
    }

    public ImageConverterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageConverterException(Throwable cause) {
        super(cause);
    }
}
