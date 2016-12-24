package org.suzuki.config.exception;

public class ConfigParseException extends RuntimeException {

    public ConfigParseException() {
    }

    public ConfigParseException(String message) {
        super(message);
    }

    public ConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigParseException(Throwable cause) {
        super(cause);
    }

    public ConfigParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
