package pl.edu.pg.eti.biocomp.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("logger");

    static {
        try {
            logManager.readConfiguration(new FileInputStream("resources/logging/props.properties"));
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration", exception);
        }
    }

    public static java.util.logging.Logger getLogger() {
        return LOGGER;
    }
}
