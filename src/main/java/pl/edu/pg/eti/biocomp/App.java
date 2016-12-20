package pl.edu.pg.eti.biocomp;

import pl.edu.pg.eti.biocomp.algorithms.Comparator;
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

class App {
    private static final Logger LOGGER = Log.getLogger();

    public static void main(String[] args) {
        LOGGER.log(Level.CONFIG, "CLI arguments", args);

        String fileName = System.getProperty("filename");
        Matrix matrix = CSV.load(fileName);
        LOGGER.info(Arrays.deepToString(matrix.getData()));


        UPGMA upgma = new UPGMA(matrix);
        Tree upgmaTree = upgma.run();

        NJ nj = new NJ(matrix);
        Tree njTree = nj.run();

        LOGGER.info(upgmaTree.toString());
        TreePrinter.print("\nUPGMA(" + fileName + ")", upgmaTree.getRootNode());
        LOGGER.info(njTree.toString());
        TreePrinter.print("\nNJ(" + fileName + ")", njTree.getRootNode());

        boolean areEqual = Comparator.areTopologicallyEqual(upgmaTree, njTree);
        System.out.printf("Results are%s topologically equal", areEqual ? "" : " not");
    }
}
