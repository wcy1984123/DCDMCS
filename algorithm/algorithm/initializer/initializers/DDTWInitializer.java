package initializer.initializers;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:45
 * System Time: 8:45 PM
 */

/*
    Apply Deviated dynamic time warping to do cluster initialization
 */

public class DDTWInitializer extends AbstractInitializer implements IInitializer {

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
    public int[] initializer(double[][] instances, int clusterNum) {
        //TODO
        return new int[0];
    }

}
