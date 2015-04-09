package initializer.initializers;

import initializer.clusterings.HierarchicalClusterAdapter;
import initializer.clusterings.IClusteringAlgorithm;
import initializer.dtws.IDTW;
import initializer.dtws.ItakuraParallelogramDTW;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.initializers
 * Date: 26/Mar/2015
 * Time: 23:02
 * System Time: 11:02 PM
 */

/*
    Apply ItakuraParallelogram dynamic time warping to do cluster initialization
 */
public class ItakuraParallelogramDTWInitializer extends AbstractInitializer implements IInitializer{
    private static final Logger LOGGER = Logger.getLogger(ItakuraParallelogramDTWInitializer.class.getName());

    /**
     * class constructor
     */
    public ItakuraParallelogramDTWInitializer() {
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
        int searchRadius = instances.get(0).size() / 5;
        IDTW idtw = new ItakuraParallelogramDTW("BinaryDistance", searchRadius);

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
        ItakuraParallelogramDTWInitializer test = new ItakuraParallelogramDTWInitializer();

    }
}
