package com.lovesoft.bitpump.calculation;

import com.lovesoft.bitpump.commons.BitPumpRuntimeException;

/**
 * Created 06.03.2018 20:41.
 */
public class BadParametersException extends BitPumpRuntimeException {
    private String paramName;

    public BadParametersException(String paramName, String message) {
        super(message +  " For parameter " + paramName);
        this.paramName = paramName;
    }

    public BadParametersException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadParametersException(Throwable cause) {
        super(cause);
    }

    public BadParametersException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
