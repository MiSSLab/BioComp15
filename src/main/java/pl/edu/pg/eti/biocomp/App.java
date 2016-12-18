package pl.edu.pg.eti.biocomp;

import pl.edu.pg.eti.biocomp.algorithms.nj.NJ;
import pl.edu.pg.eti.biocomp.algorithms.upgma.UPGMA;
import pl.edu.pg.eti.biocomp.models.Cluster;
import pl.edu.pg.eti.biocomp.utils.CSV;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Log.getLogger();

    public static void main(String[] args) {
        LOGGER.log(Level.CONFIG, "CLI arguments", args);

        String[][] data = CSV.load("resources/data1.matrix");
        double[][] matrix = Matrix.format(data);
        LOGGER.info(Arrays.deepToString(matrix));

        UPGMA upgma = new UPGMA(matrix);
        Cluster cluster1 = upgma.run();

        NJ nj = new NJ(matrix);
        Cluster cluster2 = nj.run();

        LOGGER.info(cluster1.toString());
        LOGGER.info(cluster2.toString());

    }
}
