package com.mpp.commons.Exception;

public class InvalidDniException extends Exception {

    public InvalidDniException(String msg) {
        super(msg);
    }

    public InvalidDniException(String msg, Throwable t) {
        super(msg, t);
    }
}
