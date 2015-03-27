package initializer.initializers;

import initializer.dtws.FastOptimalDTW;
import initializer.dtws.IDTW;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.initializers
 * Date: 26/Mar/2015
 * Time: 23:02
 * System Time: 11:02 PM
 */
public class FastOptimalDTWInitializer extends AbstractInitializer implements IInitializer {
    private static final Logger LOGGER = Logger.getLogger(FastOptimalDTWInitializer.class.getName());

    /**
     * class constructor
     */
    public FastOptimalDTWInitializer() {
        super();
    }

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
    @Override
    public int[] initializer(double[][] instances, int clusterNum) {

        int[] clusterLabels = null;
        if (instances == null) {
            LOGGER.log(Level.INFO, "The instances are null!");
            return clusterLabels;
        }

        if (instances.length == 0 || instances[0].length == 0) {
            LOGGER.log(Level.INFO, "The instances are empty!");
            return clusterLabels;
        }

        // initialize a dynamic time warping instance
        IDTW idtw = new FastOptimalDTW("BinaryDistance");

        // do hierarchical cluster to provide initial cluster guesses
        IClusteringAlgorithm ica = new HierarchicalClusterAdapter();

        // call super method to do initialize cluster guesses
        return super.initializer(instances, clusterNum, idtw, ica);
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        FastOptimalDTWInitializer test = new FastOptimalDTWInitializer();

    }
}
