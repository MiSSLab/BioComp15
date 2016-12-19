package pl.edu.pg.eti.biocomp;

import pl.edu.pg.eti.biocomp.algorithms.NJ;
import pl.edu.pg.eti.biocomp.algorithms.UPGMA;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.CSV;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;
import pl.edu.pg.eti.biocomp.utils.TreePrinter;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final Logger LOGGER = Log.getLogger();

    public static void main(String[] args) {
        LOGGER.log(Level.CONFIG, "CLI arguments", args);

        String fileName = System.getProperty("filename");
        String[][] data = CSV.load(fileName);
        double[][] matrix = Matrix.format(data);
        LOGGER.info(Arrays.deepToString(matrix));

        UPGMA upgma = new UPGMA(matrix);
        Tree cluster1 = upgma.run();

        NJ nj = new NJ(matrix);
        Tree cluster2 = nj.run();

        LOGGER.info(cluster1.toString());
        TreePrinter.print("\nUPGMA(" + fileName + ")", cluster1.getRootNode());
        LOGGER.info(cluster2.toString());
        TreePrinter.print("\nNJ(" + fileName + ")", cluster2.getRootNode());
    }
}
