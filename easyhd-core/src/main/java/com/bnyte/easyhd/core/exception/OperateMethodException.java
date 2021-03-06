package com.bnyte.easyhd.core.exception;

/**
 * @author bnyte
 * @since 1.0.0
 */
public class OperateMethodException extends RuntimeException {
    public OperateMethodException() {
    }

    public OperateMethodException(String message) {
        super(message);
    }

    public OperateMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperateMethodException(Throwable cause) {
        super(cause);
    }

    public OperateMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
