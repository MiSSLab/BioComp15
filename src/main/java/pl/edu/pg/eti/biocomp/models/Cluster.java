package pl.edu.pg.eti.biocomp.models;


import pl.edu.pg.eti.biocomp.utils.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Cluster {

    private static final Logger LOGGER = Log.getLogger();

    private Node node;
    private List<String> labels;
    private double selfDistance;

    public Cluster(String name, double selfDistance) {
        node = new Node(name);
        labels = new ArrayList<>(Collections.singletonList(name));
        this.selfDistance = selfDistance;
    }

    private Cluster(Node node, List<String> labels, double selfDistance) {
        this.node = node;
        this.labels = labels;
        this.selfDistance = selfDistance;
    }

    public Cluster merge(Cluster b, double distance) {
        LOGGER.info("Merging clusters [" + this.toString() + "] and [" + b.toString() + "]");
        List<String> labels = new ArrayList<>(this.getLabels());
        labels.addAll(b.getLabels());
        Node node = new Node(String.valueOf(distance));
        node.getChidren().add(this.getNode());
        node.getChidren().add(b.getNode());
        Cluster cluster = new Cluster(node, labels, distance);
        LOGGER.info("New merged cluster: [" + cluster + "]");
        return cluster;
    }

    private Node getNode() {
        return node;
    }

    public List<String> getLabels() {
        return labels;
    }

    public int getSize() {
        return labels.size();
    }

    public double getSelfDistance() {
        return selfDistance;
    }

    @Override
    public String toString() {
        return "[Labels=[" + Arrays.toString(labels.toArray()) + "]" +
                "Nodes=[" + node + "]]";
    }
}