package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Node;
import pl.edu.pg.eti.biocomp.models.Point;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class NJ {
    private static final Logger LOGGER = Log.getLogger();
    private double[][] distances;

    public NJ(double[][] initialDistances) {
        LOGGER.entering(this.getClass().getCanonicalName(), "NJ", initialDistances);
        this.distances = initialDistances;
    }


    /**
     * despite the fact that NJ returns unrooted tree, here it is rooted with the root on the last-added artificial node
     */
    public Tree run() {
        LOGGER.entering(this.getClass().getCanonicalName(), "run");
        int n = distances.length;
        Tree[] clusters = new Tree[n];
        for (int i = 0; i < n; i++) {
            clusters[i] = new Tree(String.valueOf((char) ('a' + i)));
        }
        int newTaxas = 0;
        int l = n;
        while (l > 3) {
            double[][] qMatrix = calculateQMatrix(distances, clusters, l);
            LOGGER.info("qMatrix= " + Arrays.deepToString(qMatrix));
            Point point = Matrix.findLowestValuePoint(qMatrix);
            LOGGER.info("point= " + point.toString());
            int[] closests = point.positionAsArray();
            int f = closests[0], g = closests[1];
            LOGGER.info("closest= [f:" + f + ", g:" + g + "]");
            List<Node> children = Arrays.asList(clusters[f].getRootNode(), clusters[g].getRootNode());
            Tree cluster = new Tree(String.valueOf((char) ('u' + newTaxas++)), children);
            LOGGER.info("cluster= " + cluster.toString());
            clusters[f] = cluster;
            clusters[g] = null;
            LOGGER.info("clusters= " + Arrays.toString(clusters));

            double[][] tmpDistances = Matrix.copy(distances);
            for (int i = 0; i < n; i++) {
                if (clusters[i] != null) {
                    double distance = distanceFromNewNode(tmpDistances, f, g, i);
                    distances[i][f] = distance;
                    distances[f][i] = distance;
                } else {
                    for (int j = 0; j < n; j++) {
                        distances[j][g] = Double.NaN;
                        distances[g][j] = Double.NaN;
                    }
                }
            }
            LOGGER.info("distances= " + Arrays.deepToString(distances));
            l--;
        }
        List<Node> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (clusters[i] != null) {
                children.add(clusters[i].getRootNode());
            }
        }
        Tree tree = new Tree(String.valueOf((char) ('u' + newTaxas)), children);
        LOGGER.exiting(this.getClass().getCanonicalName(), "run", tree);
        return tree;
    }

    private double[][] calculateQMatrix(double[][] d, Tree[] clusters, int l) {
        LOGGER.entering(this.getClass().getCanonicalName(), "calculateQMatrix", new Object[]{Arrays.deepToString(d), Arrays.toString(clusters), l});
        int n = d.length;
        double[][] q = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (clusters[i] != null && clusters[j] != null) {
                    double s1 = 0, s2 = 0;
                    for (int k = 0; k < n; k++) {
                        if (clusters[i] != null && clusters[k] != null) {
                            s1 += d[i][k];
                        }
                    }
                    for (int k = 0; k < n; k++) {
                        if (clusters[j] != null && clusters[k] != null) {
                            s2 += d[j][k];
                        }
                    }
                    q[i][j] = (l - 2) * d[i][j] - s1 - s2;
                } else {
                    q[i][j] = Double.NaN;
                }
            }
        }
        LOGGER.exiting(this.getClass().getCanonicalName(), "calculateQMatrix", Arrays.deepToString(q));
        return q;
    }

    @SuppressWarnings("unused")
    private double[] distancesToNewNode(double[][] d, int f, int g) {
        LOGGER.entering(this.getClass().getCanonicalName(), "distanceFromNewNode", new Object[]{Arrays.deepToString(d), f, g});
        int n = d.length;
        double s1 = 0, s2 = 0;
        for (int k = 0; k < n; k++) {
            s1 += d[f][k];
        }
        for (int k = 0; k < n; k++) {
            s2 += d[g][k];
        }
        double fu = ((1 / 2) * d[f][g]) + (1 / (2 * (n - 2))) * (s1 - s2);
        double gu = d[f][g] - fu;
        double[] result = {fu, gu};

        LOGGER.entering(this.getClass().getCanonicalName(), "distanceFromNewNode", result);
        return result;
    }

    private double distanceFromNewNode(double[][] d, int f, int g, int k) {
        LOGGER.entering(this.getClass().getCanonicalName(), "distanceFromNewNode", new Object[]{f, g, k});
        double distance = (1.0d / 2.0d) * (d[f][k] + d[g][k] - d[f][g]);
        LOGGER.exiting(this.getClass().getCanonicalName(), "distanceFromNewNode", distance);
        return distance;
    }

}
