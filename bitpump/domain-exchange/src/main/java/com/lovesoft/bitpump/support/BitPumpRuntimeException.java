package com.lovesoft.bitpump.support;

public class BitPumpRuntimeException extends RuntimeException {

    public BitPumpRuntimeException(String message) {
        super(message);
    }

    public BitPumpRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BitPumpRuntimeException(Throwable cause) {
        super(cause);
    }

    public BitPumpRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
