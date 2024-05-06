package com.generaSrpinglSecurity.spring.ExceptionHandler;

public class UserNameExistException extends Exception{
    public UserNameExistException() {
        super();
    }

    public UserNameExistException(String message) {
        super(message);
    }

    public UserNameExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameExistException(Throwable cause) {
        super(cause);
    }

    protected UserNameExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
