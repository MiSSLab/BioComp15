package pl.edu.pg.eti.biocomp.utils;

import java.util.Arrays;
import java.util.logging.Logger;

public class Matrix {
    private static final Logger LOGGER = Log.getLogger();

    public static double[][] format(String[][] matrix) {
        double[][] doubleMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                doubleMatrix[i][j] = Double.parseDouble(matrix[i][j]);
            }
        }
        LOGGER.info("Converted data: " + Arrays.deepToString(doubleMatrix));
        return doubleMatrix;
    }

    public static double[][] initQuadraticWithValue(int size, double value) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = value;
            }
        }
        return matrix;
    }
}
