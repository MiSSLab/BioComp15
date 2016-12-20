package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Log;

import java.util.logging.Logger;

public class Comparator {

    private static final Logger LOGGER = Log.getLogger();

    public static boolean areTopologicallyEqual(Tree a, Tree b) {
        LOGGER.entering(Comparator.class.getCanonicalName(), "areTopologicallyEqual", new Object[]{a, b});

        int aNodesCount = a.getRootNode().getNumberOfNodes();
        int bNodesCount = b.getRootNode().getNumberOfNodes();
        if (aNodesCount != bNodesCount) {
            LOGGER.info("aNodesCount= " + aNodesCount + ", bNodesCount= " + bNodesCount);
            LOGGER.exiting(Comparator.class.getCanonicalName(), "areTopologicallyEqual", false);
            return false;
        }

        LOGGER.exiting(Comparator.class.getCanonicalName(), "areTopologicallyEqual", false);
        return false;
    }

}
