package pl.edu.pg.eti.biocomp;

import pl.edu.pg.eti.biocomp.algorithms.upgma.UPGMA;
import pl.edu.pg.eti.biocomp.models.Cluster;
import pl.edu.pg.eti.biocomp.utils.CSV;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Log.getLogger();

    public static void main(String[] args) {
        LOGGER.log(Level.CONFIG, "CLI arguments", args);

        String[][] data = CSV.load("resources/data2.matrix");
        double[][] matrix = Matrix.format(data);
        UPGMA upgma = new UPGMA(matrix);
        Cluster cluster = upgma.run();
        LOGGER.info(matrix.toString());
    }
}
