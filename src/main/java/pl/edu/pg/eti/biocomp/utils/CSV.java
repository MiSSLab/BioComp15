package pl.edu.pg.eti.biocomp.utils;


import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSV {
    private static final Logger LOGGER = Log.getLogger();

    public static String[][] load(String fileName) {
        LOGGER.info("Loading file: " + fileName);
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            List<String[]> entries = reader.readAll();
            String[][] matrix = new String[entries.size()][];
            matrix = entries.toArray(matrix);
            LOGGER.info("Loaded data: " + Arrays.deepToString(matrix));
            return matrix;
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Exception when loading csv data", e);
            throw new RuntimeException(e);
        }
    }
}
