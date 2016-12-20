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

    public static Matrix load(String fileName) {
        LOGGER.entering(CSV.class.getCanonicalName(), "load", fileName);
        try {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            String[] header = reader.readNext();
            List<String[]> entries = reader.readAll();
            String[][] matrix = new String[entries.size()][];
            matrix = entries.toArray(matrix);
            LOGGER.exiting(CSV.class.getCanonicalName(), "load", Arrays.deepToString(matrix));
            return new Matrix(header, matrix);
        } catch (IOException e) {
            LOGGER.log(Level.FINEST, "Exception when loading csv data", e);
            throw new RuntimeException(e);
        }
    }
}
