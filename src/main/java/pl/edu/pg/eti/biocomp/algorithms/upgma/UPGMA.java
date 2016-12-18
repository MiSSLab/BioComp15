package pl.edu.pg.eti.biocomp.algorithms.upgma;

import pl.edu.pg.eti.biocomp.models.Cluster;
import pl.edu.pg.eti.biocomp.models.Point;
import pl.edu.pg.eti.biocomp.utils.Log;
import pl.edu.pg.eti.biocomp.utils.Matrix;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class UPGMA {
    private static final Logger LOGGER = Log.getLogger();
    private double[][] distances;

    public UPGMA(double[][] initialDistances) {
        this.distances = initialDistances;
    }

    public Cluster run() {
        int n = distances.length;
        LOGGER.info("n= " + n);
        Cluster[] clusters = new Cluster[n];
        for (int i = 0; i < n; i++) {
            clusters[i] = new Cluster(String.valueOf((char) ('a' + i)), .0d);
        }

        while (clusters.length > 1) {
            double[][] clusterDistances = getClusterDistances(clusters);
            LOGGER.info("cluster distances= " + Arrays.deepToString(clusterDistances));
            Point minDistancePoint = findClosestClusters(clusterDistances);
            LOGGER.info("clusters to merge= " + Arrays.toString(minDistancePoint.positionAsArray()) + " min Distance = " + minDistancePoint.getValue());
            clusters = mergeClusters(clusters, minDistancePoint);
            LOGGER.info("new clusters= " + Arrays.toString(clusters));
        }
        assert clusters.length == 1;
        LOGGER.exiting(this.getClass().getCanonicalName(), "run", clusters[0]);
        return clusters[0];
    }


    private double[][] getClusterDistances(Cluster[] clusters) {
        int n = clusters.length;
        double[][] clusterDistances = Matrix.initQuadraticWithValue(n, Double.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                clusterDistances[i][j] = distance(clusters[i], clusters[j]);
            }
        }
        LOGGER.info("cluster distances(n=" + n + ")= " + Arrays.deepToString(clusterDistances));
        return clusterDistances;
    }

    private Point findClosestClusters(double[][] clusterDistances) {
        int n = clusterDistances.length;
        Point minPoint = new Point(0, 0, Double.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double distance = clusterDistances[i][j];
                if (distance < minPoint.getValue()) {
                    minPoint = new Point(i, j, distance);
                }
            }
        }
        LOGGER.info("min point= " + minPoint);
        return minPoint;
    }

    private double distance(Cluster a, Cluster b) {
        int aCount = a.getSize();
        int bCount = b.getSize();
        double distance = 0;
        for (String aLabel : a.getLabels()) {
            for (String bLabel : b.getLabels()) {
                distance += distances[charToPosition(aLabel)][charToPosition(bLabel)];
            }
        }
        distance /= (aCount * bCount);
        LOGGER.info("Distance between cluster " + a.stringLabels() + " and " + b.stringLabels() + "= " + distance);
        return distance;

    }


    private int charToPosition(String label) {
        char c = label.charAt(0);
        return c - 'a';
    }

    private Cluster[] mergeClusters(Cluster[] clusters, Point minPoint) {
        int n = clusters.length;
        List<Integer> toMerge = minPoint.positionAsList();
        Cluster[] newClusters = new Cluster[n - 1];
        newClusters[0] = clusters[toMerge.get(0)].merge(clusters[toMerge.get(1)], minPoint.getValue());
        for (int i = 0, j = 1; i < n; i++) {
            if (!toMerge.contains(i)) {
                newClusters[j++] = clusters[i];
            }
        }
        return newClusters;
    }
}
