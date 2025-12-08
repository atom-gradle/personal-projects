package com.atom.personnel.util;

import org.slf4j.Logger;

public class Log {
    private static Logger logger;
    private Log() {}
    public static void setLogger(Logger logger) {
        Log.logger = logger;
    }

    public static void d(String message) {
        if(logger != null) {
            logger.debug(message);
        }
    }

    public static void e(String message) {
        if(logger != null) {
            logger.error(message);
        }
    }

    public static void i(String message) {
        if(logger != null) {
            logger.info(message);
        }
    }
}
