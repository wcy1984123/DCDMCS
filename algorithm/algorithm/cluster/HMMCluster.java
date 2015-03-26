package cluster;

/**
 * Project: DCDMC
 * Package: cluster
 * Date: 23/Mar/2015
 * Time: 10:38
 * System Time: 10:38 AM
 */
public class HMMCluster extends AbstractCluster {
    /**
     * Assign instances into clusters in terms of input
     * @param tranMatrix transition matrix
     * @param emitMatrix emission matrix
     * @param args other arguments
     * @return cluster labels
     */
    public int[] assignClusterLabels(int[][] data, int[][] tranMatrix, int[][] emitMatrix, int[] args) {
        //TODO
        return new int[0];
    }

    private int[] decodeHMM(int[][] data, int[][] tranMatrix, int[][] emitMatrix) {
        // compute the posterior probability of the given data in terms of tranMatrix and emitMatrix
        return new int[0];
    }


}
