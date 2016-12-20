package pl.edu.pg.eti.biocomp.utils;

import pl.edu.pg.eti.biocomp.models.Node;

import java.util.List;
import java.util.logging.Logger;

public class TreePrinter {

    private static final Logger LOGGER = Log.getLogger();

    public static void print(String header, Node rootNode) {
        LOGGER.entering(TreePrinter.class.getCanonicalName(), "print", rootNode);
        StringBuilder sb = new StringBuilder();
        sb = renderNode(rootNode, 0, sb, false);
        char[][] matrix = clearUp(sb);
        System.out.println(header);
        for (char[] aMatrix : matrix) {
            for (char anAMatrix : aMatrix) {
                System.out.print(anAMatrix);
            }
        }
    }

    private static StringBuilder renderNode(Node node, int level, StringBuilder sb, boolean isLast) {
        indent(sb, level, isLast).append("[").append(node.getLabel()).append("]\n");
        List<Node> list = node.getChildren();
        for (int i = 0; i < list.size(); i++) {
            boolean last = ((i + 1) == list.size());
            if (list.get(i).getChildren().size() > 0) {
                renderNode(list.get(i), level + 1, sb, last);
            } else {
                renderLeaf(list.get(i), level + 1, sb, last);
            }
        }
        return sb;
    }

    private static StringBuilder renderLeaf(Node node, int level, StringBuilder sb, boolean isLast) {
        return indent(sb, level, isLast).append("--- [").append(node.getLabel()).append("]\n");
    }

    private static StringBuilder indent(StringBuilder sb, int level, boolean isLast) {
        for (int i = 1; i < level; i++) {
            sb.append("|  ");
        }
        if (level > 0) {
            sb.append(isLast ? "`-" : "|-");
        }
        return sb;
    }

    private static char[][] clearUp(StringBuilder sb) {
        final String[] rows = sb.toString().split("\n");
        final int totalRows = rows.length;
        int totalColumns = 0;
        for (String r : rows) {
            if (r.length() > totalColumns) {
                totalColumns = r.length() + 2;
            }
        }
        final char[][] matrix = new char[totalRows][totalColumns];
        int i = 0, j = 0;
        for (String row : rows) {
            row = row + "\n";
            final char[] elements = row.toCharArray();
            for (final char element : elements) {
                matrix[i][j] = element;
                j++;
            }
            i++;
            j = 0;
        }
        for (i = 0; i < totalRows; i++) {
            for (j = 0; j < totalColumns; j++) {
                if (matrix[i][j] == '`') {
                    int k = 1;
                    while (k + i < totalRows && matrix[k + i][j] == '|') {
                        matrix[k + i][j] = ' ';
                        k++;
                    }
                }
            }
        }
        return matrix;
    }
}
