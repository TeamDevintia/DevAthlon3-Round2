package io.github.teamdevintia.round2.network.exceptions;

/**
 * @author Shad0wCore
 */
public class IllegalUsageException extends Exception {

    public IllegalUsageException() {
        super();
    }

    public IllegalUsageException(String message) {
        super(message);
    }

    public IllegalUsageException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalUsageException(Throwable cause) {
        super(cause);
    }

    protected IllegalUsageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
