package initializer.initializers;

import Utilities.IOOperation;
import gui.ConsoleProgressGUI;
import initializer.dtws.IDTW;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:37
 * System Time: 8:37 PM
 */
public class AbstractInitializer {

    private static final Logger LOGGER = Logger.getLogger(AbstractInitializer.class.getName());

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @param idtw a type of dynamic time warping algorithm
     * @param ica a type of clustering algorithm
     * @return initial cluster guesses
     */
    public int[] initializer(List<List<Double>> instances, int clusterNum, IDTW idtw, IClusteringAlgorithm ica) {
        int[] clusterLabels = null;
        if (instances == null) {
            LOGGER.log(Level.INFO, "The instances are null!");
            return clusterLabels;
        }

        if (instances.size() == 0) {
            LOGGER.log(Level.INFO, "The instances are empty!");
            return clusterLabels;
        }

        int ROW = instances.size();
        double[][] distanceMatrix = null;

        LOGGER.info("Initializer: Compute Distance Matrix");


//        // -------------------------- GUI Distance Matrix Computation--------------------------- //
//        ConsoleProgressGUI cpg = new ConsoleProgressGUI("Compute Distance Matrix", ROW, instances, idtw);
//        synchronized (cpg) {
//            try {
//                cpg.wait();
//            } catch (InterruptedException e) {
//
//            }
//        }
//
//        distanceMatrix = cpg.getDistanceMatrix();

        // or we could call the subroutine function
//        distanceMatrix = compuateDistanceMatrix(ROW, instances, idtw);

        // save distance matrix
        IOOperation.writeFile(distanceMatrix, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/DistanceMatrix.txt");

        // ---------------------- Directly Read Distance Matrix From File -------------------- //
        distanceMatrix = IOOperation.readMatrix("/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/MatlabOriginalDTWDistanceMatrix.txt");


        LOGGER.info("Initializer: Compute Initial Cluster Labels");
        //  compute the initial cluster guesses in terms of the given type of clustering algorithm
        int[] clusterAssignments = ica.getClusterAssignment(clusterNum, distanceMatrix);

        // save cluster assignment
        IOOperation.writeFile(clusterAssignments, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/InitialClusteringAssignment.txt");

        return clusterAssignments;
    }

    /**
     * Compute the distance matrix in terms of the given type of dynamic time warping algorithm
     * @param ROW the number of instances
     * @param instances instance dataset
     * @param idtw the dynamic time warping algorithm
     * @return the distance matrix of the instances
     */
    private double[][] compuateDistanceMatrix(int ROW, List<List<Double>> instances, IDTW idtw) {
        double[][] distanceMatrix = new double[ROW][ROW];
        // compute distance matrix in terms of the given type of dynamic time warping algorithm
        for (int i = 0; i < ROW; i++) {
            for (int j = i + 1; j < ROW; j++) {

                // get the dynamic time warping distance between two sequences
                distanceMatrix[i][j] = idtw.computeDistance(instances.get(i), instances.get(j));

                // get the optimal warping path between two sequences
                // List<List<Integer>> optimalPath = idtw.computePath(null, null);

                // here assume it guarantees the symmetric feature for DTW
                if (j < ROW && i < ROW) distanceMatrix[j][i] = distanceMatrix[i][j];

            }

            System.out.println("The distances of instances [ " + i + " ] is finished.");
        }

        return distanceMatrix;
    }

}
