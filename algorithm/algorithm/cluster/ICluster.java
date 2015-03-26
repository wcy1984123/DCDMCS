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
     * @param tranMatrix transition matrix
     * @param emitMatrix emission matrix
     * @param args other arguments
     * @return cluster labels
     */
    public int[] assignClusterLabels(int[][] data, int[][] tranMatrix, int[][] emitMatrix, int[] args);
}
