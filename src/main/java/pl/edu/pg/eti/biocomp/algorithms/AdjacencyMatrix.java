package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class AdjacencyMatrix {
    private static final Logger LOGGER = Log.getLogger();
    private final Matrix adjacencyMatrix;

    private HashSet<String> uniqueLabels = new HashSet<>();
    private int i = 0;

    public AdjacencyMatrix(Tree tree, String[] initialHeader) {
        Node rootNode = tree.getRootNode();
        int size = rootNode.getNumberOfNodes();
        adjacencyMatrix = new Matrix(size);
        String[] header = adjacencyMatrix.getHeader();
        System.arraycopy(initialHeader, 0, header, 0, initialHeader.length);
        makeLabelsUnique(rootNode);
        walk(rootNode);
    }

    private void makeLabelsUnique(Node rootNode) {
        if (uniqueLabels.contains(rootNode.getLabel())) {
            rootNode.setLabel(rootNode.getLabel() + (char) ('a' + i));
        }
        uniqueLabels.add(rootNode.getLabel());
        rootNode.getChildren().forEach(this::makeLabelsUnique);
    }

    private void walk(Node rootNode) {
        double[][] data = adjacencyMatrix.getData();
        int position = adjacencyMatrix.establishPosition(rootNode.getLabel());
        for (Node node : rootNode.getChildren()) {
            int position2 = adjacencyMatrix.establishPosition(node.getLabel());
            data[position][position2] = 1;
            data[position2][position] = 1;
            walk(node);
        }
    }

    public void print() {
        adjacencyMatrix.print();
    }

    public Tree toCanonicalTree() {
        List<Integer> visited = new ArrayList<>();
        int i = 0;
        Node rootNode = createNodes(i, visited);
        return new Tree(rootNode);
    }

    private Node createNodes(int i, List<Integer> visited) {
        String[] header = adjacencyMatrix.getHeader();
        double[][] data = adjacencyMatrix.getData();
        visited.add(i);
        Node node = new Node(header[i]);
        LOGGER.info("header[i]=" + header[i]);
        for (int j = 0; j < header.length; j++) {
            if (!visited.contains(j) && data[i][j] == 1.0d) {
                node.getChildren().add(createNodes(j, visited));
            }
        }
        return node;
    }
}
