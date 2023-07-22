package com.phmc.bmapper.exceptions;

public class LoadConfigException extends RuntimeException {

    public LoadConfigException (String message, Exception exception) {
        super(message, exception);
    }
}
