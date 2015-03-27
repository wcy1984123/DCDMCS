package initializer.initializers;

import initializer.dtws.IDTW;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:37
 * System Time: 8:37 PM
 */
public class AbstractInitializer {

    private static final Logger LOGGER = Logger.getLogger(AbstractInitializer.class.getName());

    /**
     * Convert an arraylist to a two-dimensional data
     * @param instances input data
     * @return a two-dimensional integer array
     */
    protected int[][] formatInstances(List<List<Integer>> instances) {
        //TODO
        return new int[0][];
    }

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @param idtw a type of dynamic time warping algorithm
     * @param ica a type of clustering algorithm
     * @return initial cluster guesses
     */
    public int[] initializer(double[][] instances, int clusterNum, IDTW idtw, IClusteringAlgorithm ica) {
        int[] clusterLabels = null;
        if (instances == null) {
            LOGGER.log(Level.INFO, "The instances are null!");
            return clusterLabels;
        }

        if (instances.length == 0 || instances[0].length == 0) {
            LOGGER.log(Level.INFO, "The instances are empty!");
            return clusterLabels;
        }

        int ROW = instances.length;
        int COLUMN = instances[0].length;
        double[][] distanceMatrix = new double[ROW][COLUMN];

        // compute distance matrix in terms of the given type of dynamic time warping algorithm
        for (int i = 0; i < ROW; i++) {
            for (int j = i + 1; j < ROW; j++) {

                // get the dynamic time warping distance between two sequences
                distanceMatrix[i][j] = idtw.computeDistance(instances[i], instances[j]);

                // get the optimal warping path between two sequences
                // List<List<Integer>> optimalPath = idtw.computePath(null, null);

                // here assume it guarantees the symmetric feature for DTW
                if (j < ROW && i < COLUMN) distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }

        //  compute the initial cluster guesses in terms of the given type of clustering algorithm
        int[] clusterAssignments = ica.getClusterAssignment(clusterNum, distanceMatrix);

        return clusterAssignments;
    }
}
