package initializer;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:34
 * System Time: 8:34 PM
 */
public interface IInitializer {

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
    public int[] initializer(int[][] instances, int clusterNum);
}
