package pl.edu.pg.eti.biocomp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {
    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger LOGGER = Logger.getLogger("logger");

    static {
        try {
            InputStream inputStream = Log.class.getClassLoader().getResourceAsStream("logging/props.properties");
            logManager.readConfiguration(inputStream);
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Error in loading configuration", exception);
        }
    }

    public static java.util.logging.Logger getLogger() {
        return LOGGER;
    }
}
