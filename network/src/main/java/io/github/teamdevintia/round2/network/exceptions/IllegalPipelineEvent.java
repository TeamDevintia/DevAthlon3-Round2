package io.github.teamdevintia.round2.network.exceptions;

/**
 * @author Shad0wCore
 */
public class IllegalPipelineEvent extends Exception {

    public IllegalPipelineEvent() {
    }

    public IllegalPipelineEvent(String message) {
        super(message);
    }

    public IllegalPipelineEvent(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPipelineEvent(Throwable cause) {
        super(cause);
    }

    public IllegalPipelineEvent(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
