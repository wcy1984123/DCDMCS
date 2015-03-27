package model;

import cluster.ICluster;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: model
 * Date: 23/Mar/2015
 * Time: 09:33
 * System Time: 9:33 AM
 */
public class HiddenMarkovModel implements IModel, ICluster{
    private static final Logger LOGGER = Logger.getLogger(HiddenMarkovModel.class.getName());

    private int stateNum;
    private int emitNum;
    private double[][] tranMatrix; // state transition matrix
    private double[][] emitMatrix; // emission transition matrix

    /**
     * class constructor
     */
    public HiddenMarkovModel() {

    }

    /**
     * class constructor
     */
    public HiddenMarkovModel(int stateNum, int emitNum) {
        this.stateNum = stateNum;
        this.emitNum = emitNum;
        tranMatrix = new double[stateNum][stateNum];
        emitMatrix = new double[stateNum][emitNum];
        initializeHMM();
    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {
        return;
    }

    /**
     * Assign instances into clusters in terms of input
     * @param data data matrix
     * @return cluster labels
     */
    @Override
    public int[] assignClusterLabels(int[][] data) {
        return new int[0];
    }

    /**
     * Randomly initialize transition matrix and emission matrix
     */
    public void initializeHMM() {

        // initialize state transition
        for(int i = 0; i < stateNum; i++) {
            for (int j = 0; j < stateNum; j++) {
                this.tranMatrix[i][j] = Math.random() + 0.001; // plus 0.001 to avoid 0 value
            }
        }

        // initialize emission transition
        for (int i = 0; i < stateNum; i++) {
            for (int j = 0; j < emitNum; j++) {
                this.emitMatrix[i][j] = Math.random() + 0.001; // plut 0.001 to avoid 0 value
            }
        }

        // normalize matrix
        normalizeMatrix(this.tranMatrix);
        normalizeMatrix(this.emitMatrix);
    }

    /**
     * Train HMM
     */
    public void trainHMM() {
        // TODO
    }

    /**
     * Test HMM
     */
    public void testHMM() {
        // TODO
        // this functionality has been move to package "cluster" assigning instances into clusters
    }

    /**
     * Normalize matrix where the sum of each row is equal to 1 
     * @param matrix a given matrix with double values
     */
    private void normalizeMatrix(double[][] matrix) {
        if (matrix == null) {
            LOGGER.log(Level.INFO, "The matrix is null!");
            return;
        }
        
        if (matrix.length == 0 || matrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The matrix is empty!");
            return;
        }
        
        for (int i = 0; i < matrix.length; i++) {
            double sum = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j];
            }
            
            // normalize the current row
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] /= sum;
            }
        }
    }

    /**
     * getter of tranMatrix member variable
     * @return tranMatrix
     */
    public double[][] getTranMatrix() {
        return this.tranMatrix;
    }

    /**
     * getter of emitMatrix member variable
     * @return emitMatrix
     */
    public double[][] getEmitMatrix() {
        return this.emitMatrix;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        HiddenMarkovModel test = new HiddenMarkovModel(3, 3);
        double[][] tranGuess = test.getTranMatrix();
        double[][] emitGuess = test.getEmitMatrix();
        
        for (int i = 0; i < tranGuess.length; i++) {
            for (int j = 0; j < tranGuess[0].length; j++) {
                System.out.print(tranGuess[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < emitGuess.length; i++) {
            for (int j = 0; j < emitGuess[0].length; j++) {
                System.out.print(emitGuess[i][j] + " ");
            }
            System.out.println();
        }
    }
}
