package pl.edu.pg.eti.biocomp.models;


import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Tree {

    private static final Logger LOGGER = Log.getLogger();

    private Node rootNode;
    private List<String> labels;

    public Tree(String name) {
        rootNode = new Node(name);
        labels = new ArrayList<>(Collections.singletonList(name));
    }

    public Tree(String name, List<Node> children) {
        this.rootNode = new Node(name);
        rootNode.getChildren().addAll(children);
        labels = new ArrayList<>(Collections.singletonList(name));
    }

    private Tree(Node rootNode, List<String> labels) {
        this.rootNode = rootNode;
        this.labels = labels;
    }

    public Tree merge(Tree b, double distance) {
        LOGGER.info("Merging clusters: " + this + " and " + b);
        List<String> labels = new ArrayList<>(this.getLabels());
        labels.addAll(b.getLabels());
        Node node = new Node(String.valueOf(distance));
        node.getChildren().add(this.getRootNode());
        node.getChildren().add(b.getRootNode());
        Tree tree = new Tree(node, labels);
        LOGGER.info("New tree: " + tree);
        return tree;
    }

    public Node getRootNode() {
        return rootNode;
    }

    public List<String> getLabels() {
        return labels;
    }

    public int getSize() {
        return labels.size();
    }

    public double[][] getAdjacencyMatrix() {
        int n = rootNode.getNumberOfNodes();

        double[][] matrix = Matrix.initQuadraticWithValue(n, .0d);
        return null;
    }

    @Override
    public String toString() {
        return rootNode.toString();
    }
}