package initializer.initializers;

import initializer.dtws.GlobalWeightedDTW;
import initializer.dtws.IDTW;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.initializers
 * Date: 08/Apr/2015
 * Time: 09:32
 * System Time: 9:32 AM
 */
/*
    Apply Global weighted dynamic time warping to do cluster initialization
 */

public class GlobalWeightedDTWInitializer extends AbstractInitializer implements IInitializer {

    private static final Logger LOGGER = Logger.getLogger(MatlabOriginalDTWInitializer.class.getName());

    /**
     * class constructor
     */
    public GlobalWeightedDTWInitializer() {
        super();
    }

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
    @Override
    public int[] initializer(List<List<Double>> instances, int clusterNum) {

        int[] clusterLabels = null;
        if (instances == null) {
            LOGGER.log(Level.INFO, "The instances are null!");
            return clusterLabels;
        }

        if (instances.size() == 0) {
            LOGGER.log(Level.INFO, "The instances are empty!");
            return clusterLabels;
        }

        // initialize a dynamic time warping instance
        IDTW idtw = new GlobalWeightedDTW();

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
        GlobalWeightedDTWInitializer test = new GlobalWeightedDTWInitializer();

    }

}
