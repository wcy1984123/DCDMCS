package cluster;

/**
 * Project: DCDMC
 * Package: cluster
 * Date: 23/Mar/2015
 * Time: 10:36
 * System Time: 10:36 AM
 */
public abstract class AbstractCluster implements ICluster{

    /**
     * Assign instances into clusters in terms of input
     * @param tranMatrix transition matrix
     * @param emitMatrix emission matrix
     * @param args other arguments
     * @return cluster labels
     */
    public int[] assignClusterLabels(int[][] tranMatrix, int[][] emitMatrix, int[] args) {
        //TODO
        return new int[0];
    }
}
