package pl.edu.pg.eti.biocomp.utils;

import pl.edu.pg.eti.biocomp.models.Point;

import java.util.Arrays;
import java.util.logging.Logger;

public class Matrix {
    private static final Logger LOGGER = Log.getLogger();

    public static double[][] format(String[][] matrix) {
        LOGGER.entering(Matrix.class.getCanonicalName(), "format", Arrays.deepToString(matrix));
        double[][] doubleMatrix = new double[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                doubleMatrix[i][j] = Double.parseDouble(matrix[i][j]);
            }
        }
        LOGGER.exiting(Matrix.class.getCanonicalName(), "format", Arrays.deepToString(doubleMatrix));
        return doubleMatrix;
    }

    public static double[][] initQuadraticWithValue(int size, double value) {
        LOGGER.entering(Matrix.class.getCanonicalName(), "initQuadraticWithValue", new Object[]{size, value});
        double[][] matrix = new double[size][size];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = value;
            }
        }
        LOGGER.exiting(Matrix.class.getCanonicalName(), "initQuadraticWithValue", Arrays.deepToString(matrix));
        return matrix;
    }

    public static Point findLowestValuePoint(double[][] matrix) {
        LOGGER.entering(Matrix.class.getCanonicalName(), "findLowestValuePoint", matrix);
        int n = matrix.length;
        Point minPoint = new Point(0, 0, Double.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = matrix[i][j];
                if (distance < minPoint.getValue()) {
                    minPoint = new Point(i, j, distance);
                }
            }
        }
        LOGGER.exiting(Matrix.class.getCanonicalName(), "findLowestValuePoint", minPoint);
        return minPoint;
    }

    public static double[][] copy(double[][] matrix) {
        LOGGER.entering(Matrix.class.getCanonicalName(), "copy", matrix);
        double[][] newCopy = new double[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            newCopy[i] = matrix[i].clone();
        }
        LOGGER.exiting(Matrix.class.getCanonicalName(), "copy", newCopy);
        return newCopy;
    }
}
