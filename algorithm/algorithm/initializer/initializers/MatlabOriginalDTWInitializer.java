package initializer.initializers;

import initializer.clusterings.HierarchicalClusterAdapter;
import initializer.clusterings.IClusteringAlgorithm;
import initializer.dtws.MatlabOriginalDTW;
import initializer.dtws.IDTW;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:44
 * System Time: 8:44 PM
 */

/*
    Apply original dynamic time warping to do cluster initialization
 */

public class MatlabOriginalDTWInitializer extends AbstractInitializer implements IInitializer{

    private static final Logger LOGGER = Logger.getLogger(MatlabOriginalDTWInitializer.class.getName());

    /**
     * class constructor
     */
    public MatlabOriginalDTWInitializer() {
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
        IDTW idtw = new MatlabOriginalDTW();

        // do hierarchical cluster to provide initial cluster guesses
        // IClusteringAlgorithm ica = new HierarchicalClusterAdapter();

        // call super method to do initialize cluster guesses
        return super.initializer(instances, clusterNum, idtw, null);
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        MatlabOriginalDTWInitializer test = new MatlabOriginalDTWInitializer();

    }
}
