package org.example.Log;

import java.util.logging.Level;

public class Logger {

    private static Logger instance;
    private final java.util.logging.Logger logger;

    private Logger() {
        logger = java.util.logging.Logger.getLogger("MyLogger");
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logDebug(String message) {
        logger.log(Level.INFO, "[DEBUG] " + message);
    }

    public void logError(String message) {
        logger.log(Level.SEVERE, "[ERROR] " + message);
    }


}

