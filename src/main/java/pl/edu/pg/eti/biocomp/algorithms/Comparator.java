package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.logging.Logger;

public class Comparator {

    private static final Logger LOGGER = Log.getLogger();

    public static boolean areTopologicallyEqual(Tree a, Tree b) {
        LOGGER.entering(Comparator.class.getCanonicalName(), "areTopologicallyEqual", new Object[]{a, b});
        Node aRootNode = a.getRootNode();
        Node bRootNode = b.getRootNode();

        boolean equals = equals(aRootNode, bRootNode);
        LOGGER.exiting(Comparator.class.getCanonicalName(), "areTopologicallyEqual", equals);
        return equals;
    }

    private static boolean equals(Node aNode, Node bNode) {
        LOGGER.entering(Comparator.class.getCanonicalName(), "equals", new Node[]{aNode, bNode});
        if (aNode.getChildren().size() == bNode.getChildren().size()) {
            if (aNode.getChildren().size() == 0 && bNode.getChildren().size() == 0) {
                if (aNode.printableLable().equals(bNode.printableLable())) {
                    LOGGER.exiting(Comparator.class.getCanonicalName(), "equals", true);
                    return true;
                } else {
                    LOGGER.exiting(Comparator.class.getCanonicalName(), "equals", false);
                    return false;
                }
            } else {
                List<Boolean> comparisons = new ArrayList<>();
                for (Node aChild : aNode.getChildren()) {
                    List<Boolean> comparisons2 = new ArrayList<>();
                    for (Node bChild : bNode.getChildren()) {
                        boolean equals = equals(aChild, bChild);
                        LOGGER.info(equals + aChild.printableLable() + "<<<>>>" + bChild.printableLable());
                        comparisons2.add(equals);
                    }
                    comparisons.add(comparisons2.stream().reduce(or()).get());
                }
                Boolean result = comparisons.stream().reduce(and()).get();
                LOGGER.exiting(Comparator.class.getCanonicalName(), "equals", result);
                return result;
            }
        } else {
            return false;
        }
    }

    private static BinaryOperator<Boolean> or() {
        return (a, b) -> a || b;
    }

    private static BinaryOperator<Boolean> and() {
        return (a, b) -> a && b;
    }

}
