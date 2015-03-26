package initializer;

import Utilities.Utilities;
import hierarchicalclustering.*;

import java.util.ArrayList;
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
    Dynamic time warping computation class
    It transfered from matlab program from: http://www.ee.columbia.edu/ln/rosa/matlab/dtw/ to java program
 */

public class DTWInitializer extends AbstractInitializer implements IInitializer{

    private static final Logger LOGGER = Logger.getLogger(DTWInitializer.class.getName());

    private int[][] DPMatrix; // dynamic programming of time warping matrix
    private List<Integer> p; // x coordinates in the optimal path
    private List<Integer> q; // y coordinates in the optimal path
    private HierarchicalClusterAdapter hcAdapter; // hierarchical clustering


    /**
     * class constructor
     */
    public DTWInitializer() {
        super();
        DPMatrix = null;
        p = null;
        p = null;
        hcAdapter = null;
    }

    /**
     * Calculate initial cluster labels for input data
     * @param instances input data
     * @param clusterNum the maximum number of clusters
     * @return initial cluster guesses
     */
    public int[] initializer(int[][] instances, int clusterNum) {

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

        for (int i = 0; i < ROW; i++) {
            for (int j = i + 1; j < ROW; j++) {
                computeDTW(instances[i], instances[j]);

                // get the dynamic time warping distance between two sequences
                distanceMatrix[i][j] = this.DPMatrix[instances[i].length - 1][instances[j].length - 1];

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
     * Compute the cost matrix with the minimum cost path between these two sequences
     * @param seq1 the first sequence
     * @param seq2 the second sequence
     * @return local cost matrix
     */
    private int[][] computeDTW(int[] seq1, int[] seq2) {
        int length1 = seq1.length;
        int length2 = seq2.length;

        int[][] localCostMatrix = new int[length1][length2];

        // compute the local cost matrix by two sequences
        for (int i = 0; i < length1; i++) {
            for (int j = 0; j < length2; j++) {
                localCostMatrix[i][j] = getLocalCostMeasure(seq1[i], seq2[j]);
            }
        }

        // compute the dynamic time warping
        int[][] traceback = dpDTW(localCostMatrix);

        // add the starting point (0, 0) into the minimum cost optimal path
        this.p.add(0, 0);
        this.q.add(0, 0);

        return localCostMatrix;
    }

    /**
     * Calculate dynamic time warping matrix
     * @param costMatrix cost matrix constructed by two sequences
     * @return a dynamic time warping matrix
     */
    private int[][] dpDTW(int[][] costMatrix) {

        int[][] DP = null; // dynamic programming
        int[][] phi = null; // trace back

        clearAll(); // clear all member variables

        if (costMatrix == null) {
            LOGGER.log(Level.INFO, "The cost matrix is null!");
            return DP;
        }

        if (costMatrix.length == 0 || costMatrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The cost matrix is empty!");
            return DP;
        }

        int ROW = costMatrix.length; // The length of the first sequence
        int COLUMN = costMatrix[0].length; // The length of the second sequence
        DP = new int[ROW + 1][COLUMN + 1];
        phi = new int[ROW][COLUMN]; // store the optimal path info

        // initialize the first row and the first column
        for (int i = 0; i <= ROW; i++) {
            DP[i][0] = Integer.MAX_VALUE;
        }

        for (int i = 0; i <= COLUMN; i++) {
            DP[0][i] = Integer.MAX_VALUE;
        }

        //----------------- very important initialization ------------------//
        DP[0][0] = 0;
        // copy cost matrix into the DP
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                DP[i + 1][j + 1] = costMatrix[i][j];
            }
        }

        //--------- dynamic programming for calculating DP matrix ----------//
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                int min = DP[i][j];
                int index = 0;

                // compare DP[i][j] with DP[i][j + 1]
                min = Math.min(DP[i][j], DP[i][j + 1]);
                index = min == DP[i][j] ? 0 : 1;

                // compare the larger one with DP[i + 1][j]
                int mincopy = min; // only to be consistent with the algorithm in matlab
                min = Math.min(min, DP[i + 1][j]);
                index = min == mincopy ? index : 2;

                // update DP[i + 1][j + 1]
                DP[i + 1][j + 1] += min;
                phi[i][j] = index;
            }
        }

        //---------------------- trace back from top left -------------------//
        int i = ROW - 1;
        int j = COLUMN - 1;
        this.p = new ArrayList<Integer>();
        this.q = new ArrayList<Integer>();
        p.add(i);
        q.add(j);

        while (i > 0 && j > 0) {
            int tb = phi[i][j];
            if (tb == 0) {
                i--;
                j--;
            } else if (tb == 1) {
                i--;
            } else if (tb == 2) {
                j--;
            } else {
                LOGGER.log(Level.INFO, "The trace back error happens!");
            }
            p.add(0, i);
            q.add(0, j);

        }

        // strip off the edges of the DP matrix before returning
        int[][] cache = new int[ROW][COLUMN];
        for (i = 1; i <= ROW; i++) {
            for (j = 1; j <= COLUMN; j++) {
                cache[i-1][j-1] = DP[i][j];
            }
        }

        // store the final results
        this.DPMatrix = cache;

        return phi; // trace back path
    }

    /**
     * Calculate the cost of two elements in two sequences.
     * @param element1 a discrete value in sequence 1
     * @param element2 a discrete value in sequence 2
     * @return if they are the same, return 0; otherwise return 1.
     */
    private int getLocalCostMeasure(int element1, int element2) {
        if (element1 == element2) return 0;
        else return 1;
    }

    /**
     * clear all member variables
     */
    private void clearAll() {
        this.DPMatrix = null;
        this.p = null;
        this.q = null;
    }

    /**
     * Getter function for DPMatrix
     * @return member variable DPMatrix
     */
    public int[][] getDPMatrix() {
        return this.DPMatrix;
    }

    /**
     * Getter function for p
     * @return memeber variable p
     */
    public List<Integer> getXOptimalPath() {
        return this.p;
    }

    /**
     * Getter function for q
     * @return memeber variable q
     */
    public List<Integer> getYOptimalPath() {
        return this.q;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        DTWInitializer test = new DTWInitializer();
        int[] a = new int[]{1, 1, 0, 1};
        int[] b = new int[]{1, 0, 1, 0};
        int[][] localCostMatrix = test.computeDTW(a ,b);

        // print out local cost matrix
        Utilities.printMatrix(localCostMatrix);

        // print out DP matrix
        Utilities.printMatrix(test.getDPMatrix());

        // print out p path
        Utilities.printArray(test.getXOptimalPath());

        // print out q path
        Utilities.printArray(test.getYOptimalPath());
    }
}
