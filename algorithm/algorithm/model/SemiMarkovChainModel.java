package model;

import Utilities.Utilities;
import Utilities.Models;
import Utilities.IOOperation;
import cluster.ICluster;
import umontreal.iro.lecuyer.charts.ContinuousDistChart;
import umontreal.iro.lecuyer.probdist.WeibullDist;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: model
 * Date: 27/Mar/2015
 * Time: 07:57
 * System Time: 7:57 AM
 */

public class SemiMarkovChainModel implements IModel, ICluster{

    private static final Logger LOGGER = Logger.getLogger(SemiMarkovChainModel.class.getName());

    private double[][] mStateTransitionProbability;
    private List<List<Integer>> mInstances;
    public static double[][] mParameters; // trained parameters
    private static int Seq = 0;

    public SemiMarkovChainModel() {
        this.mStateTransitionProbability = null;
        this.mInstances = null;
        this.Seq++;
    }

    /**
     * Get parameters from semi-Markov chain model
     * @return
     */
    public static double[][] getmParametersFromSemiMarkovChainModels() {
        return mParameters;
    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {

        // --------------------- Compute State Transition -------------------- //
        // convert from double list into integer list
        this.mInstances = Utilities.convertToListOfListOfIntegers(instances);

        // compute state transition
        int[][] stateTransition = Models.countNoSelfStateTransitionForSequences(this.mInstances);

        // compute state transition probablity
        this.mStateTransitionProbability = Utilities.normalizeMatrix(stateTransition);

        // ---------------------- Compute State Duration --------------------- //

        int StateNum = stateTransition.length;
        this.mParameters = new double[StateNum][3];
        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);

        for (int i = 0; i < StateNum; i++) {

            Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1);

            int total = 0;

            for (Integer key : oneStateDurationDistribution.keySet()) total += oneStateDurationDistribution.get(key);

            double[] durations = new double[total];
            int count = 0;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (Integer key : oneStateDurationDistribution.keySet()) {
                min = Math.min(min, key);
                max = Math.max(max, key);
                for (int j = 0; j < oneStateDurationDistribution.get(key); j++) durations[count++] = key;
            }

            // do probability density estimation
            this.mParameters[i] = WeibullDist.getMLE(durations, durations.length);

            // create a plot of probability density estimation
//            WeibullDist wd = new WeibullDist(this.mParameters[i][0], this.mParameters[i][1], this.mParameters[i][2]);
//            ContinuousDistChart chart = new ContinuousDistChart(wd, min, max, max - min + 1);
//            JFrame jf = chart.viewDensity(300, 400);
//            jf.setVisible(true);
        }

    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    public void trainModel(double[][] instances) {

        // --------------------- Compute State Transition -------------------- //
        // convert from double array into integer array
        this.mInstances = Utilities.convertToListOfListOfIntegers(instances);

        // compute state transition
        int[][] stateTransition = Models.countNoSelfStateTransitionForSequences(this.mInstances);

        // compute state transition probablity
        this.mStateTransitionProbability = Utilities.normalizeMatrix(stateTransition);

        // ---------------------- Compute State Duration --------------------- //

        int StateNum = stateTransition.length;
        this.mParameters = new double[StateNum][3];
        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);

        for (int i = 0; i < StateNum; i++) {
            Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1);

            int total = 0;

            for (Integer key : oneStateDurationDistribution.keySet()) total += oneStateDurationDistribution.get(key);

            double[] durations = new double[total];
            int count = 0;
            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            for (Integer key : oneStateDurationDistribution.keySet()) {
                min = Math.min(min, key);
                max = Math.max(max, key);
                while (count < total) {
                    for (int j = 0; j < oneStateDurationDistribution.get(key); j++) durations[count++] = key;
                }
            }

            this.mParameters[i] = WeibullDist.getMLE(durations, durations.length);

            // create a plot of probability density estimation
//            WeibullDist wd = new WeibullDist(this.mParameters[i][0], this.mParameters[i][1], this.mParameters[i][2]);
//            ContinuousDistChart chart = new ContinuousDistChart(wd, min, max, max - min + 1);
//            JFrame jf = chart.viewDensity(300, 400);
//            jf.setVisible(true);

        }

    }

    /**
     * Compute the posterior probability of instances given the model
     * @param instances instances matrix
     * @return the posterior probability of instances given the model
     */
    @Override
    public double[] getInstancesProbs(List<List<Double>> instances) {

        double[] instancesProbs = null;

        if (instances == null) {
            LOGGER.info("The sequences are null!");
            return instancesProbs;
        }

        if (instances.size() == 0) {
            LOGGER.info("The sequences are empty!");
            return instancesProbs;
        }

        int N = instances.size();
        instancesProbs = new double[N];

        // compute state transition probability for each instance
        for (int i = 0; i < N; i++) {
            instancesProbs[i] = getLogProbabilityStateTransition(instances.get(i));
        }

        // compute state duration probability for each instance
        for (int i = 0; i < N; i++) {
            instancesProbs[i] += getLogProbabilityStateDuration(instances.get(i));
        }

        return instancesProbs;
    }

    /**
     * Compute the log probability of state duration in a seq starting with index 1 given the model
     * @param seq a sequence data
     * @return the log probability of state duration in a seq given the model
     */
    private double getLogProbabilityStateDuration(List<Double> seq) {

        // seq starts with index 1
        double logProb = 0.0;
        int[] aSeq = Utilities.convertToOneDimensionalIntegerArray(seq);
        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForOneSequence(aSeq);
        int stateNum = map.keySet().size();

        for (int i = 0; i < stateNum; i++) {
            Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1);
            for (Integer oneStateDuration : oneStateDurationDistribution.keySet()) {

                // compute the state duration of the state in the sequence
                WeibullDist wd = new WeibullDist(this.mParameters[i][0], this.mParameters[i][1], this.mParameters[i][2]);

                logProb += oneStateDurationDistribution.get(oneStateDuration) * Math.log(wd.cdf(oneStateDuration + 1) - wd.cdf(oneStateDuration));
            }
        }

        return logProb;
    }

    /**
     * Compute the log probability of state transition in a seq staring with index 1 given the model
     * @param seq a sequence data
     * @return the log probability of state transition in a seq given the model
     */
    private double getLogProbabilityStateTransition(int[] seq) {

        // seq starts with index 1
        double logProb = 0.0;

        int curState = seq[0];

        for (int i = 1; i < seq.length; i++) {
            // compute the probability of state transition only when two consecutive states are not same
            if(curState != seq[i]) {
                logProb += Math.log(this.mStateTransitionProbability[curState - 1][seq[i] - 1]);
            }
            curState = seq[i];
        }

        return logProb;
    }

    /**
     * Compute the log probability of state transition in a seq staring with index 1 given the model
     * @param instance a sequence data
     * @return the log probability of state transition in a seq given the model
     */
    private double getLogProbabilityStateTransition(List<Double> instance) {

        // seq starts with index 1

        // convert a list of doubles into an integer array
        int[] seq = Utilities.convertToOneDimensionalIntegerArray(instance);

        return getLogProbabilityStateTransition(seq);
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
