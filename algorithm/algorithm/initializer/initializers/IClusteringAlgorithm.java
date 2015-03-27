package initializer.initializers;

/**
 * Project: DCDMC
 * Package: initializer.initializers
 * Date: 26/Mar/2015
 * Time: 21:19
 * System Time: 9:19 PM
 */
public interface IClusteringAlgorithm {

    /**
     * Get cluster assignment starting with 0 as class labels
     * @param clusterNum the maximum of clusters
     * @param distanceMatrix distance matrix of sequences
     * @return an array of cluster assignments
     */
    public int[] getClusterAssignment(int clusterNum, double[][] distanceMatrix);
}
