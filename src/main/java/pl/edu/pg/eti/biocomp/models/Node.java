package pl.edu.pg.eti.biocomp.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    private List<Node> children;
    private String label;

    public Node(String label) {
        this.label = label;
    }

    List<Node> getChidren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    @Override
    public String toString() {
        return label + (getChidren().size() > 0 ? ": " + Arrays.toString(getChidren().toArray()) : "");
    }
}
