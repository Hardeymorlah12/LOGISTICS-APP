package com.hardeymorlah.Logistics.exception;

public class MessagingException extends Exception {
    public MessagingException(String msg) {
        super(msg);
    }

    public MessagingException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
