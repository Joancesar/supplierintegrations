package com.hotelbeds.supplierintegrations.timecalculation.exception;

public class TimestampParseException extends RuntimeException {
    public TimestampParseException(String message) {
        super(message);
    }

    public TimestampParseException(String message, Throwable ex) {
        super(message, ex);
    }
}
