package cluster;

/**
 * Project: DCDMC
 * Package: cluster
 * Date: 23/Mar/2015
 * Time: 10:25
 * System Time: 10:25 AM
 */
public interface ICluster {

    /**
     * Assign instances into clusters in terms of input
     * @param data data matrix
     * @return cluster labels
     */
    public int[] assignClusterLabels(int[][] data);
}
