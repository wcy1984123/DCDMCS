package initializer.dtws;

import Utilities.Utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.dtws
 * Date: 26/Mar/2015
 * Time: 15:37
 * System Time: 3:37 PM
 */

/*
    Dynamic time warping computation class by Alex Wong
    It transfered from matlab program from: http://www.ee.columbia.edu/ln/rosa/matlab/dtw/ to java program
 */

public class DTW implements IDTW{

    private static final Logger LOGGER = Logger.getLogger(DTW.class.getName());

    private double[][] DPMatrix; // dynamic programming of time warping matrix
    private List<Integer> p; // x coordinates in the optimal path
    private List<Integer> q; // y coordinates in the optimal path


    /**
     * class constructor
     */
    public DTW() {
        DPMatrix = null;
        p = null;
        p = null;
    }

    /**
     * Compute the DTW-related distance between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the distance between two time series
     */
    public double computeDistance(double[] timeseries1, double[] timeseries2) {

        // two parameters could be null

        double distance = Integer.MIN_VALUE;

        if (timeseries1 == null || timeseries2 == null) {
            LOGGER.log(Level.INFO, "The time series are null!");
            return distance;
        }

        if (timeseries1.length == 0 || timeseries2.length == 0) {
            LOGGER.log(Level.INFO, "The time series are empty!");
            return distance;
        }

        // compute the DPMatrix two-dimensional matrix
        computeDTW(timeseries1, timeseries2);

        // get the dynamic time warping distance between two sequences
        int ROW = timeseries1.length;
        int COLUMN = timeseries2.length;
        distance = this.DPMatrix[ROW - 1][COLUMN - 1];


        return distance;
    }

    /**
     * Compute the optimal warping path between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the optimal warping path between two time series
     */
    public List<List<Integer>> computePath(double[] timeseries1, double[] timeseries2){
        List<List<Integer>> optimalPath = null;

        if (timeseries1 == null || timeseries2 == null) {
            LOGGER.log(Level.INFO, "The time series are null!");
            return optimalPath;
        }

        if (timeseries1.length == 0 || timeseries2.length == 0) {
            LOGGER.log(Level.INFO, "The time series are empty!");
            return optimalPath;
        }

        int N = this.p.size();
        for (int i = 0; i < N; i++) {
            optimalPath.add(Arrays.asList(p.get(i), q.get(i)));
        }

        return optimalPath;
    }

    /**
     * Compute the cost matrix with the minimum cost path between these two sequences
     * @param seq1 the first sequence
     * @param seq2 the second sequence
     * @return local cost matrix
     */
    private double[][] computeDTW(double[] seq1, double[] seq2) {
        int length1 = seq1.length;
        int length2 = seq2.length;

        double[][] localCostMatrix = new double[length1][length2];

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
    private int[][] dpDTW(double[][] costMatrix) {

        double[][] DP = null; // dynamic programming
        int[][] phi = null; // trace back

        clearAll(); // clear all member variables

        if (costMatrix == null) {
            LOGGER.log(Level.INFO, "The cost matrix is null!");
            return phi;
        }

        if (costMatrix.length == 0 || costMatrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The cost matrix is empty!");
            return phi;
        }

        int ROW = costMatrix.length; // The length of the first sequence
        int COLUMN = costMatrix[0].length; // The length of the second sequence
        DP = new double[ROW + 1][COLUMN + 1];
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
                double min = DP[i][j];
                int index = 0;

                // compare DP[i][j] with DP[i][j + 1]
                min = Math.min(DP[i][j], DP[i][j + 1]);
                index = min == DP[i][j] ? 0 : 1;

                // compare the larger one with DP[i + 1][j]
                double mincopy = min; // only to be consistent with the algorithm in matlab
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
        double[][] cache = new double[ROW][COLUMN];
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
    private int getLocalCostMeasure(double element1, double element2) {
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
    public double[][] getDPMatrix() {
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
        DTW test = new DTW();
        double[] a = new double[]{1, 1, 0, 1};
        double[] b = new double[]{1, 0, 1, 0};
        double[][] localCostMatrix = test.computeDTW(a ,b);

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