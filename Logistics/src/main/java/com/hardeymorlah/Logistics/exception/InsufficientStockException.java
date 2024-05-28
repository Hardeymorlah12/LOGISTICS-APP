package com.hardeymorlah.Logistics.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException() {
    }

    public InsufficientStockException(String message) {
        super(message);
    }

    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientStockException(Throwable cause) {
        super(cause);
    }

    public InsufficientStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
