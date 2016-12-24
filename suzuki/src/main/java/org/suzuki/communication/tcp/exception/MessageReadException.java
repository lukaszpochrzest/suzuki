package org.suzuki.communication.tcp.exception;

public class MessageReadException extends RuntimeException {
    public MessageReadException() {
    }

    public MessageReadException(String message) {
        super(message);
    }

    public MessageReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageReadException(Throwable cause) {
        super(cause);
    }

    public MessageReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
