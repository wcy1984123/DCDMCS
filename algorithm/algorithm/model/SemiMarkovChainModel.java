package model;

import Utilities.Utilities;
import Utilities.Models;
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

    double[][] mStateTransitionProbability;
    List<List<Integer>> mInstances;
    double[][] mParameters;

    public SemiMarkovChainModel() {
        this.mStateTransitionProbability = null;
        this.mInstances = null;
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

            this.mParameters[i] = WeibullDist.getMLE(durations, durations.length);
            WeibullDist wd = new WeibullDist(this.mParameters[i][0], this.mParameters[i][1], this.mParameters[i][2]);
            ContinuousDistChart chart = new ContinuousDistChart(wd, min, max, max - min + 1);
            JFrame jf = chart.viewDensity(300, 400);
            jf.setVisible(true);
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
            WeibullDist wd = new WeibullDist(this.mParameters[i][0], this.mParameters[i][1], this.mParameters[i][2]);
            ContinuousDistChart chart = new ContinuousDistChart(wd, min, max, max - min + 1);
            JFrame jf = chart.viewDensity(300, 400);
            jf.setVisible(true);

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
        for (int i = 0; i < N; i++) {
            instancesProbs[i] = getLogProbabilityStateTransition(instances.get(i));
        }

        return instancesProbs;
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

    /**
     * Compute the log probability of a seq staring with index 1 given the model
     * @param instance a sequence data
     * @return the log probability of a seq given the model
     */
    private double getLogProbabilityStateTransition(List<Double> instance) {

        // seq starts with index 1
        double logProb = 0.0;

        // convert a list of doubles into an integer array
        int[] seq = Utilities.convertToOneDimensionalIntegerArray(instance);

        int curState = seq[0];

        for (int i = 1; i < seq.length; i++) {
            logProb += Math.log(this.mStateTransitionProbability[curState - 1][seq[i] - 1]);
            curState = seq[i];
        }

        return logProb;
    }
}
