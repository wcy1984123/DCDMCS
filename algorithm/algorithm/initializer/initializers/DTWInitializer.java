package initializer.initializers;

import initializer.dtws.MatlabOriginalDTW;
import initializer.dtws.IDTW;

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

public class DTWInitializer extends AbstractInitializer implements IInitializer{

    private static final Logger LOGGER = Logger.getLogger(DTWInitializer.class.getName());

    private HierarchicalClusterAdapter hcAdapter; // hierarchical clustering


    /**
     * class constructor
     */
    public DTWInitializer() {
        super();
        hcAdapter = null;
    }

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
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

        int ROW = instances.length;
        int COLUMN = instances[0].length;
        double[][] distanceMatrix = new double[ROW][COLUMN];

        // initialize a dynamic time warping instance
        IDTW idtw = new MatlabOriginalDTW();


        // compute distance matrix
        for (int i = 0; i < ROW; i++) {
            for (int j = i + 1; j < ROW; j++) {

                // get the dynamic time warping distance between two sequences
                distanceMatrix[i][j] = idtw.computeDistance(instances[i], instances[j]);

                // get the optimal warping path between two sequences
                // List<List<Integer>> optimalPath = idtw.computePath(null, null);

                // here assume it guarantees the symmetric feature for DTW
                if (j < ROW && i < COLUMN) distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }

        // do hierarchical cluster to provide initial cluster guesses
        hcAdapter = new HierarchicalClusterAdapter(distanceMatrix);

        // set a new linkage strategy
        // hcAdapter.setLinkageStrategy(new CompleteLinkageStrategy());
        int[] clusterAssignments = hcAdapter.getClusterAssignment(clusterNum);

        return clusterAssignments;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        DTWInitializer test = new DTWInitializer();

    }
}
