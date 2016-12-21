package pl.edu.pg.eti.biocomp.algorithms;

import junit.framework.Assert;
import junit.framework.TestCase;
import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.TreePrinter;

public class ComparatorTest extends TestCase {

    public void testTopologicallyEqual() throws Exception {
        Node aNode1 = new Node("a");
        Node aNode2 = new Node("b");
        Node aNode3 = new Node("c");
        aNode3.getChildren().add(aNode1);
        aNode3.getChildren().add(aNode2);
        Node aNode4 = new Node("d");
        aNode4.getChildren().add(aNode3);
        Node aNode5 = new Node("e");
        Node aNode6 = new Node("f");
        Node aNode7 = new Node("g");
        aNode7.getChildren().add(aNode5);
        aNode7.getChildren().add(aNode6);
        Node aNode8 = new Node("h");
        aNode8.getChildren().add(aNode4);
        aNode8.getChildren().add(aNode7);
        Tree a = new Tree(aNode8);
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(a, new String[]{"a", "b", "c", "d"});
        Tree canonicalTreeA = adjacencyMatrix.toCanonicalTree();
        TreePrinter.print("a", canonicalTreeA.getRootNode());
        Tree canonicalTreeB = adjacencyMatrix.toCanonicalTree();
        TreePrinter.print("b", canonicalTreeB.getRootNode());

        Assert.assertTrue(Comparator.areTopologicallyEqual(canonicalTreeA, canonicalTreeB));
    }
    public void testTopologicallyNotEqual() throws Exception {
        Node aNode1 = new Node("a");
        Node aNode2 = new Node("b");
        Node aNode3 = new Node("c");
        aNode3.getChildren().add(aNode1);
        aNode3.getChildren().add(aNode2);
        Node aNode4 = new Node("d");
        aNode4.getChildren().add(aNode3);
        Node aNode5 = new Node("e");
        Node aNode6 = new Node("f");
        Node aNode7 = new Node("g");
        aNode7.getChildren().add(aNode5);
        aNode7.getChildren().add(aNode6);
        Node aNode8 = new Node("h");
        aNode8.getChildren().add(aNode4);
        aNode8.getChildren().add(aNode7);
        Tree a = new Tree(aNode8);
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(a, new String[]{"a", "b", "c", "d"});


        Node bNode1 = new Node("a");
        Node bNode2 = new Node("b");
        Node bNode3 = new Node("c");
        bNode3.getChildren().add(bNode1);
        bNode3.getChildren().add(bNode2);
        Node bNode4 = new Node("d");
        bNode4.getChildren().add(bNode3);
        Node bNode5 = new Node("e");
        Node bNode6 = new Node("f");
        Node bNode7 = new Node("g");
        bNode7.getChildren().add(bNode5);
        Node bNode8 = new Node("h");
        bNode8.getChildren().add(bNode4);
        bNode8.getChildren().add(bNode7);
        Tree b = new Tree(bNode8);

        AdjacencyMatrix adjacencyMatrixB = new AdjacencyMatrix(b, new String[]{"a", "b", "c", "d"});
        Tree canonicalTreeA = adjacencyMatrix.toCanonicalTree();
        TreePrinter.print("a", canonicalTreeA.getRootNode());
        Tree canonicalTreeB = adjacencyMatrixB.toCanonicalTree();
        TreePrinter.print("b", canonicalTreeB.getRootNode());

        Assert.assertFalse(Comparator.areTopologicallyEqual(canonicalTreeA, canonicalTreeB));
    }
}