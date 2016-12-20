package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Point;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Labels;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NJ {
    private static final Logger LOGGER = Log.getLogger();
    private Matrix distances;

    public NJ(Matrix initialDistances) {
        LOGGER.entering(this.getClass().getCanonicalName(), "NJ", initialDistances);
        this.distances = initialDistances;
    }

    /**
     * despite the fact that NJ returns unrooted tree, here it is rooted with the root on the last-added artificial node
     */
    public Tree run() {
        LOGGER.entering(this.getClass().getCanonicalName(), "run");
        int n = distances.getData().length;
        Tree[] clusters = new Tree[n];
        for (int i = 0; i < n; i++) {
            clusters[i] = new Tree(distances.getHeader()[i]);
        }

        while (n > 2) {
            double[][] oldDistances = distances.getData();

            double[][] qMatrix = calculateQMatrix(distances.getData(), clusters);
            Point point = Matrix.findLowestValuePoint(qMatrix);

            int[] closests = point.positionAsArray();
            int f = closests[0];
            int g = closests[1];

            clusters = updateClusters(oldDistances, clusters, f, g);

            n--;
            updateDistances(n, oldDistances, f, g);
        }
        Tree tree = generateFinalTree(clusters);
        LOGGER.exiting(this.getClass().getCanonicalName(), "run", tree);
        return tree;
    }

    private double[][] calculateQMatrix(double[][] d, Tree[] clusters) {
        LOGGER.entering(this.getClass().getCanonicalName(), "calculateQMatrix", new Object[]{Arrays.deepToString(d), Arrays.toString(clusters)});
        int n = d.length;
        double[][] q = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    q[i][j] = .0d;
                } else {
                    double s1 = 0, s2 = 0;
                    for (int k = 0; k < n; k++) {
                        s1 += d[i][k];
                    }
                    for (int k = 0; k < n; k++) {
                        s2 += d[j][k];
                    }
                    q[i][j] = (n - 2) * d[i][j] - s1 - s2;
                }
            }
        }
        LOGGER.exiting(this.getClass().getCanonicalName(), "calculateQMatrix", Arrays.deepToString(q));
        return q;
    }

    private Tree[] updateClusters(double[][] oldDistances, Tree[] clusters, int f, int g) {
        Node fNode = clusters[f].getRootNode();
        Node gNode = clusters[g].getRootNode();
        double[] distances = distancesToNewNode(oldDistances, f, g);
        fNode.setDistanceToParent(distances[0]);
        gNode.setDistanceToParent(distances[1]);

        List<Node> children = Arrays.asList(fNode, gNode);
        String newLabel = Labels.next(this.distances.getLastColumnName());
        this.distances.removeTwoAddOne(f, g, newLabel);
        Tree cluster = new Tree(newLabel, children);

        Tree[] newClusters = new Tree[clusters.length - 1];
        for (int i = 0, j = 0; i < clusters.length; i++) {
            if (f != i && g != i) {
                newClusters[j++] = clusters[i];
            }
        }
        clusters = newClusters;
        clusters[clusters.length - 1] = cluster;
        return clusters;
    }

    private void updateDistances(int n, double[][] oldDistances, int f, int g) {
        int min = Math.min(f, g);
        int max = Math.max(f, g);

        for (int i = 0; i < n - 1; i++) {
            double distance = Double.NaN;
            if (i < min) {
                distance = distanceFromNewNode(oldDistances, f, g, i);
            } else if (i + 1 > min && i + 1 < max) {
                distance = distanceFromNewNode(oldDistances, f, g, i + 1);
            } else if (i + 2 > max) {
                distance = distanceFromNewNode(oldDistances, f, g, i + 2);
            }
            distances.getData()[n - 1][i] = distance;
            distances.getData()[i][n - 1] = distance;
        }
        distances.getData()[n - 1][n - 1] = .0d;
    }

    private Tree generateFinalTree(Tree[] clusters) {
        Node node = clusters[1].getRootNode();
        node.setDistanceToParent(distances.getData()[0][1]);//only one distance is left
        clusters[0].getRootNode().getChildren().add(node);
        return clusters[0];
    }

    private double[] distancesToNewNode(double[][] d, int f, int g) {
        LOGGER.entering(this.getClass().getCanonicalName(), "distancesToNewNode", new Object[]{Arrays.deepToString(d), f, g});
        int n = d.length;
        double s1 = .0d, s2 = .0d;
        for (int k = 0; k < n; k++) {
            s1 += d[f][k];
        }
        for (int k = 0; k < n; k++) {
            s2 += d[g][k];
        }
        double fu = ((1.0d / 2) * d[f][g]) + (1.0d / (2.0d * (n - 2))) * (s1 - s2);
        double gu = d[f][g] - fu;
        double[] result = {fu, gu};

        LOGGER.exiting(this.getClass().getCanonicalName(), "distancesToNewNode", result);
        return result;
    }

    private double distanceFromNewNode(double[][] d, int f, int g, int k) {
        LOGGER.entering(this.getClass().getCanonicalName(), "distanceFromNewNode", new Object[]{Arrays.deepToString(d), f, g, k});
        double distance = (1.0d / 2.0d) * (d[f][k] + d[g][k] - d[f][g]);
        LOGGER.exiting(this.getClass().getCanonicalName(), "distanceFromNewNode", distance);
        return distance;
    }

}
