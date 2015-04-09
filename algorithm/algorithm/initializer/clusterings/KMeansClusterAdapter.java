package initializer.clusterings;

import initializer.clusterings.IClusteringAlgorithm;

/**
 * Project: DCDMC
 * Package: initializer.initializers
 * Date: 09/Apr/2015
 * Time: 10:54
 * System Time: 10:54 AM
 */
public class KMeansClusterAdapter implements IClusteringAlgorithm {

    @Override
    public int[] getClusterAssignment(int clusterNum, double[][] distanceMatrix) {
        return new int[0];
    }
}
