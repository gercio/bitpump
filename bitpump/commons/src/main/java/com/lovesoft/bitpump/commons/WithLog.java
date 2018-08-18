package com.lovesoft.bitpump.commons;

import org.slf4j.Logger;

public interface WithLog {

    default void logDebug(Logger log, String s, Object ... objects) {
        if(log.isDebugEnabled()) {
            log.debug(s, objects);
        }
    }

    default void logInfo(Logger log, String s, Object ... objects) {
        if(log.isInfoEnabled()) {
            log.info(s, objects);
        }
    }

    default void logWarn(Logger log, String s, Object ... objects) {
        if(log.isWarnEnabled()) {
            log.warn(s, objects);
        }
    }

    default void logError(Logger log, String s, Object ... objects) {
        if(log.isErrorEnabled()) {
            log.error(s, objects);
        }
    }
}
