package org.example.Log;

public class Logger {

    private static Logger instance;

    private Logger() {
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void logDebug(String message) {
        System.out.println("[DEBUG] " + message);
    }

    public void logError(String message) {
        System.err.println("[ERROR] " + message);
    }


}

