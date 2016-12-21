package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.ArrayList;
import java.util.List;

public class AdjacencyMatrix {
    private final Matrix adjacencyMatrix;

    public AdjacencyMatrix(Tree tree, String[] initialHeader) {
        Node rootNode = tree.getRootNode();
        int size = rootNode.getNumberOfNodes();
        adjacencyMatrix = new Matrix(size);
        String[] header = adjacencyMatrix.getHeader();
        double[][] data = adjacencyMatrix.getData();
        System.arraycopy(initialHeader, 0, header, 0, initialHeader.length);
        walk(rootNode);
    }

    public Matrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    private void walk(Node rootNode) {
        double[][] data = adjacencyMatrix.getData();
        int position = adjacencyMatrix.establishPosition(rootNode.rawLabel());
        for (Node node : rootNode.getChildren()) {
            int position2 = adjacencyMatrix.establishPosition(node.rawLabel());
            data[position][position2] = 1;
            data[position2][position] = 1;
            walk(node);
        }
    }

    public void print() {
        adjacencyMatrix.print();
    }

    public Tree toCanonicalTree() {
        String[] header = this.adjacencyMatrix.getHeader();
        double[][] data = adjacencyMatrix.getData();
        List<Integer> visited = new ArrayList<>();
        int i = 0;
        Node rootNode = createNodes(i, visited);
        return new Tree(rootNode);
    }

    private Node createNodes(int i, List<Integer> visited) {
        String[] header = this.adjacencyMatrix.getHeader();
        double[][] data = adjacencyMatrix.getData();
        visited.add(i);
        Node node = new Node(header[i]);
        for (int j = 0; j < header.length; j++) {
            if (!visited.contains(j) && data[i][j] == 1.0d) {
                node.getChildren().add(createNodes(j, visited));
            }
        }
        return node;
    }
}
