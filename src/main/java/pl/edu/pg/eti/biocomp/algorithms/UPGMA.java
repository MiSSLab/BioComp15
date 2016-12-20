package pl.edu.pg.eti.biocomp.algorithms;

import pl.edu.pg.eti.biocomp.models.Point;
import pl.edu.pg.eti.biocomp.models.Tree;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class UPGMA {
    private static final Logger LOGGER = Log.getLogger();
    private final Matrix distances;

    public UPGMA(Matrix initialDistances) {
        LOGGER.entering(this.getClass().getCanonicalName(), "UPGMA", initialDistances);
        this.distances = initialDistances;
    }

    public Tree run() {
        LOGGER.entering(this.getClass().getCanonicalName(), "run");
        int n = distances.getData().length;
        LOGGER.info("n= " + n);
        Tree[] clusters = new Tree[n];
        for (int i = 0; i < n; i++) {
            clusters[i] = new Tree(String.valueOf((char) ('a' + i)));
        }

        while (clusters.length > 1) {
            double[][] clusterDistances = getClusterDistances(clusters);
            LOGGER.info("cluster distances= " + Arrays.deepToString(clusterDistances));
            Point minDistancePoint = Matrix.findLowestValuePoint(clusterDistances);
            LOGGER.info("clusters to merge= " + Arrays.toString(minDistancePoint.positionAsArray()) + " min Distance = " + minDistancePoint.getValue());
            clusters = mergeClusters(clusters, minDistancePoint);
            LOGGER.info("new clusters= " + Arrays.toString(clusters));
        }
        assert clusters.length == 1;
        LOGGER.exiting(this.getClass().getCanonicalName(), "run", clusters[0]);
        return clusters[0];
    }


    private double[][] getClusterDistances(Tree[] clusters) {
        LOGGER.entering(this.getClass().getCanonicalName(), "getClusterDistances", Arrays.toString(clusters));
        int n = clusters.length;
        double[][] clusterDistances = Matrix.initQuadraticWithValue(n, Double.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                clusterDistances[i][j] = distance(clusters[i], clusters[j]);
            }
        }
        LOGGER.exiting(this.getClass().getCanonicalName(), "getClusterDistances", Arrays.deepToString(clusterDistances));
        return clusterDistances;
    }


    private double distance(Tree a, Tree b) {
        LOGGER.entering(this.getClass().getCanonicalName(), "distance", new Object[]{a, b});
        int aCount = a.getSize();
        int bCount = b.getSize();
        double distance = 0;
        for (String aLabel : a.getLabels()) {
            for (String bLabel : b.getLabels()) {
                distance += distances.getData()[labelToPosition(aLabel)][labelToPosition(bLabel)];
            }
        }
        distance /= (aCount * bCount);
        LOGGER.exiting(this.getClass().getCanonicalName(), "distance", distance);
        return distance;

    }


    private int labelToPosition(String label) {
        String[] header = distances.getHeader();
        for (int i = 0; i < header.length; i++) {
            String colName = header[i];
            if (colName.equals(label)) {
                return i;
            }
        }
        throw new RuntimeException("Something went wrong with header");
    }

    private Tree[] mergeClusters(Tree[] clusters, Point minPoint) {
        LOGGER.entering(this.getClass().getCanonicalName(), "mergeClusters", new Object[]{Arrays.toString(clusters), minPoint});
        int n = clusters.length;
        List<Integer> toMerge = minPoint.positionAsList();
        Tree[] newClusters = new Tree[n - 1];
        newClusters[0] = clusters[toMerge.get(0)].merge(clusters[toMerge.get(1)], minPoint.getValue());
        for (int i = 0, j = 1; i < n; i++) {
            if (!toMerge.contains(i)) {
                newClusters[j++] = clusters[i];
            }
        }
        LOGGER.entering(this.getClass().getCanonicalName(), "mergeClusters", Arrays.toString(newClusters));
        return newClusters;
    }
}
