package model;

/**
 * Project: DCDMC
 * Package: model
 * Date: 27/Mar/2015
 * Time: 07:57
 * System Time: 7:57 AM
 */

import Utilities.Utilities;
import Utilities.Models;
import cluster.ICluster;
import com.sun.org.apache.bcel.internal.generic.LOOKUPSWITCH;

import java.util.List;
import java.util.logging.Logger;

/**
 * Markov Chain Model
 */
public class MarkovChainModel implements IModel, ICluster {

    private static final Logger LOGGER = Logger.getLogger(MarkovChainModel.class.getName());

    double[][] mStateTransitionProbability;
    int[][] mInstances;

    public MarkovChainModel() {
        this.mStateTransitionProbability = null;
    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {

        // convert from double array into integer array
        this.mInstances = Utilities.convertToTwoDimensionIntegerArray(instances);

        // compute state transition
        int[][] stateTransition = Models.countStateTransitionForSequences(this.mInstances);

        // compute state transition probablity
        this.mStateTransitionProbability = Utilities.normalizeMatrix(stateTransition);

    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    public void trainModel(double[][] instances) {

        // convert from double array into integer array
        this.mInstances = Utilities.convertToTwoDimensionIntegerArray(instances);

        // compute state transition
        int[][] stateTransition = Models.countStateTransitionForSequences(this.mInstances);

        // compute state transition probablity
        this.mStateTransitionProbability = Utilities.normalizeMatrix(stateTransition);

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

    private int[] getClusterLabels(int[][] seqs) {

        int[] clusterLabels = null;

        if (seqs == null) {
            LOGGER.info("The sequences are null!");
            return clusterLabels;
        }

        if (seqs.length == 0 || seqs[0].length == 0) {
            LOGGER.info("The sequences are empty!");
            return clusterLabels;
        }

        return new int[0];
    }

    /**
     * Compute the log probability of a seq staring with index 1 given the model
     * @param seq a sequence data
     * @return the log probability of a seq given the model
     */
    private double getLogProbabilityStateTransition(int[] seq) {

        // seq starts with index 1
        double logProb = 0.0;

        int curState = seq[0];

        for (int i = 1; i < seq.length; i++) {
            logProb += Math.log(this.mStateTransitionProbability[curState - 1][seq[i] - 1]);
            curState = seq[i];
        }

        return logProb;
    }
}
