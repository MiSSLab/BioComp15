package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ComparableRepresentation {

    List<String> allNodeNames = new ArrayList<>();
    List<String> result = new ArrayList<>();

    public ComparableRepresentation(Tree tree, int leaves) {
        Node node = tree.getRootNode();

        List<Integer> visited = new ArrayList<>();
        visited.add(0);

        List<String> walk = walk(1, node, visited);
        walk.sort(String::compareToIgnoreCase);
        String names = join(walk);
        allNodeNames.add(names);

        while (leaves-- > 0) {

            int i = 0;
            while (!allNodeNames.get(i).matches("^\\[[a-z]+\\]$")) {
                i++;
            }
            String leave = allNodeNames.get(i);
            i = 0;
            while (!allNodeNames.get(i).matches("^\\[[a-z]+\\]$")) {
                i++;
            }


        }
    }

    public List<String> walk(int minIterator, Node node, List<Integer> visited) {
        if (node.getChildren().size() == 0) {
            return Collections.singletonList(node.toString());
        } else {
            return node.getChildren().stream().map(n -> {
                List<Integer> myVisited = new ArrayList<>(visited);
                myVisited.add(minIterator);
                List<String> walk = walk(minIterator, n, myVisited);
                walk.sort(String::compareToIgnoreCase);
                String names = join(walk);
                allNodeNames.add(names);
                return names;
            }).collect(Collectors.toList());
        }
    }

    private String join(List<String> stringList) {
        StringJoiner sj = new StringJoiner("");
        stringList.forEach(sj::add);
        return sj.toString();
    }
}
