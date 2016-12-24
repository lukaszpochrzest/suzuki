package org.suzuki.communication.tcp.server.exception;

public class ServerStartException extends RuntimeException {
    public ServerStartException() {
    }

    public ServerStartException(String message) {
        super(message);
    }

    public ServerStartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerStartException(Throwable cause) {
        super(cause);
    }

    public ServerStartException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}