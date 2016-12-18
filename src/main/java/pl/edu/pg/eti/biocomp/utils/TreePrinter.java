package pl.edu.pg.eti.biocomp.utils;

import pl.edu.pg.eti.biocomp.models.Node;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreePrinter {

    private static PrintStream printStream = System.out;

    public static void printNode(Node root) {
        int maxLevel = TreePrinter.maxLevel(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || TreePrinter.isAllElementsNull(nodes))
            return;
        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
        TreePrinter.printWhitespaces(firstSpaces);
        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                printStream.print(node.getLabel());
                List<Node> children = node.getChidren();
                switch (children.size()) {
                    case 3:
                        newNodes.add(node.getChidren().get(0));
                        newNodes.add(node.getChidren().get(1));
                        newNodes.add(node.getChidren().get(2));
                        break;
                    case 2:
                        newNodes.add(node.getChidren().get(0));
                        newNodes.add(node.getChidren().get(1));
                        break;
                    case 1:
                        newNodes.add(node.getChidren().get(0));
                        break;
                    default:
                        break;
                }
            } else {
                newNodes.add(null);
                newNodes.add(null);
                printStream.print(" ");
            }
            TreePrinter.printWhitespaces(betweenSpaces);
        }
        printStream.println("");
        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                TreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    TreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }
                if (nodes.get(j).getChidren().size() > 0 && nodes.get(j).getChidren().get(0) != null) {
                    printStream.print("/");
                } else {
                    TreePrinter.printWhitespaces(1);
                }
                TreePrinter.printWhitespaces(i + i - 1);
                if (nodes.get(j).getChidren().size() > 1 && nodes.get(j).getChidren().get(1) != null) {
                    printStream.print("\\");
                } else {
                    TreePrinter.printWhitespaces(1);
                }
                TreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                if (nodes.get(j).getChidren().size() > 2 && nodes.get(j).getChidren().get(2) != null) {
                    printStream.print("\\");
                }
            }
            printStream.println("");
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            printStream.print(" ");
    }

    private static int maxLevel(Node node) {
        if (node == null)
            return 0;
        List<Node> children = node.getChidren();
        switch (children.size()) {
            case 0:
                return 1;
            case 1:
                return TreePrinter.maxLevel(children.get(0)) + 1;
            case 2:
                return Math.max(TreePrinter.maxLevel(children.get(0)), TreePrinter.maxLevel(children.get(1))) + 1;
            case 3:
                return Math.max(Math.max(TreePrinter.maxLevel(children.get(0)), TreePrinter.maxLevel(children.get(1))), TreePrinter.maxLevel(children.get(2))) + 1;
            default:
                return 1;
        }
    }

    private static boolean isAllElementsNull(List list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }
        return true;
    }

}