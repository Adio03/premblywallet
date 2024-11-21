package org.springcorepractice.walletapplication.domain.exceptions;

public class IdentityManagerException extends Exception{
    public IdentityManagerException(String message){
        super(message);
    }
    public IdentityManagerException (String message, Throwable cause){
        super(message,cause);
    }
    public IdentityManagerException(Throwable cause){
        super(cause);
    }
}
