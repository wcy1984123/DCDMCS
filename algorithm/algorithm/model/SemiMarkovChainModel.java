package model;

import Utilities.Utilities;
import Utilities.Models;
import cluster.ICluster;
import gui.ContinuousDistChartAdapter;
import starter.Config;
import umontreal.iro.lecuyer.probdist.WeibullDist;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
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

public class SemiMarkovChainModel implements IModel, ICluster {

    private static final Logger LOGGER = Logger.getLogger(SemiMarkovChainModel.class.getName());

    private double[][] mStateTransitionProbability;
    private List<List<Integer>> mInstances;
    public double[][] mParameters; // trained parameters
    private static int Seq = 0;
    private int curSeq;
    private List<List<Integer>> scopeForStateDurations; // min and max of state durations for each state

    public SemiMarkovChainModel() {
        this.mStateTransitionProbability = null;
        this.mInstances = null;
        this.curSeq = Seq++;
        this.scopeForStateDurations = new ArrayList<List<Integer>>();
    }

    /**
     * Get parameters from semi-Markov chain model
     * @return
     */
    public double[][] getmParametersFromSemiMarkovChainModels() {
        return this.mParameters;
    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {

        if (instances == null) {
            LOGGER.info("The instances are null!");
            // compute state transition probablity
            this.mStateTransitionProbability = new double[Config.getSTATENUM()][Config.getSTATENUM()];
            this.mParameters = new double[Config.getSTATENUM()][3];
            for (int i = 0; i < Config.getSTATENUM(); i++) {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }
            return;
        }

        if (instances.size() == 0) {
            LOGGER.info("The instances are empty!");
            // compute state transition probablity
            this.mStateTransitionProbability = new double[Config.getSTATENUM()][Config.getSTATENUM()];
            this.mParameters = new double[Config.getSTATENUM()][3];
            for (int i = 0; i < Config.getSTATENUM(); i++) {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }
            return;
        }

        // --------------------- Compute State Transition -------------------- //
        // convert from double list into integer list
        this.mInstances = Utilities.convertToListOfListOfIntegers(instances);

        // compute state transition
        int[][] stateTransition = Models.countNoSelfStateTransitionForSequences(this.mInstances);

        // compute state transition probablity
        this.mStateTransitionProbability = Utilities.normalizeMatrix(stateTransition);

        // ---------------------- Compute State Duration --------------------- //
        int StateNum = Config.getCLUSTERNUM();
        this.mParameters = new double[StateNum][3];
        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);

        for (int i = 0; i < StateNum; i++) {

            // state duration distribution is not null
            if (i < map.keySet().size()) {
                Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1);

                int total = 0;

                for (Integer key : oneStateDurationDistribution.keySet())
                    total += oneStateDurationDistribution.get(key);

                double[] durations = new double[total];
                int count = 0;
                int min = Integer.MAX_VALUE;
                int max = Integer.MIN_VALUE;
                for (Integer key : oneStateDurationDistribution.keySet()) {
                    min = Math.min(min, key);
                    max = Math.max(max, key);
                    for (int j = 0; j < oneStateDurationDistribution.get(key); j++) durations[count++] = key;
                }

                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(min, max)));

                // do probability density estimation
                this.mParameters[i] = WeibullDist.getMLE(durations, durations.length);
            } else {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }

        }

    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    public void trainModel(double[][] instances) {

        if (instances == null) {
            LOGGER.info("The instances are null!");
            // compute state transition probablity
            this.mStateTransitionProbability = new double[Config.getSTATENUM()][Config.getSTATENUM()];
            this.mParameters = new double[Config.getSTATENUM()][3];
            for (int i = 0; i < Config.getSTATENUM(); i++) {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }
            return;
        }

        if (instances.length == 0 || instances[0].length == 0) {
            LOGGER.info("The instances are empty!");
            // compute state transition probablity
            this.mStateTransitionProbability = new double[Config.getSTATENUM()][Config.getSTATENUM()];
            this.mParameters = new double[Config.getSTATENUM()][3];
            for (int i = 0; i < Config.getSTATENUM(); i++) {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }
            return;
        }

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
            // state duration distribution is not null
            if (i < map.keySet().size()) {
                Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1);

                int total = 0;

                for (Integer key : oneStateDurationDistribution.keySet())
                    total += oneStateDurationDistribution.get(key);

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

                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(min, max)));

                // do probability density estimation
                this.mParameters[i] = WeibullDist.getMLE(durations, durations.length);
            } else {
                this.mParameters[i] = new double[]{0.0, 0.0, 0.0}; // set alpha, lambda, and delta to be 0
                this.scopeForStateDurations.add(new ArrayList<Integer>(Arrays.asList(0, 0)));
            }

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
        double logProb = Double.NEGATIVE_INFINITY;
        int[] aSeq = Utilities.convertToOneDimensionalIntegerArray(seq);
        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForOneSequence(aSeq);
        int stateNum = map.keySet().size();
        boolean flag = true; // it indicates the first time that it is valid parameters set up

        for (int i = 0; i < stateNum; i++) {
            Map<Integer, Integer> oneStateDurationDistribution = map.get(i + 1); // state starts with 1
            double alpha = this.mParameters[i][0];
            double lambda = this.mParameters[i][1];
            double delta = this.mParameters[i][2];

            // Invalid weibull parameters
            if (alpha <= 0 || lambda <= 0) {
                logProb = Double.NEGATIVE_INFINITY;
                break;
            } else {
                if (flag == true) {
                    logProb = 0; // reset to 0 instead of -infinity
                    flag = false;
                }
                // compute the state duration of the state in the sequence
                WeibullDist wd = new WeibullDist(alpha, lambda, delta);
                for (Integer oneStateDuration : oneStateDurationDistribution.keySet()) {
                    logProb += oneStateDurationDistribution.get(oneStateDuration) * Math.log(wd.cdf(oneStateDuration + 1) - wd.cdf(oneStateDuration));
                }
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
                // if mStateTransitionProbability[curState - 1][seq[i] - 1] == 0, Math.log would take -infinity
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
     * Visualize output of the dynamic model
     */
    @Override
    public void visualizeOutput() {
        int stateNum = Config.getSTATENUM();
        int modelSeq = this.curSeq % Config.getCLUSTERNUM() + 1; // modulo current model sequence value under total clusters scope
        System.out.println("\n               -------- Model [ " + modelSeq + " ] -------- ");

        // print out state transition matrix
        for (int i = 0; i < stateNum; i++) {
            for (int j = 0; j < stateNum; j++) {

                System.out.print("            " + String.format("%.4f", this.mStateTransitionProbability[i][j]) + " ");
            }
            System.out.println();
        }

        JFrame generalJframe = new JFrame("Model [ " + modelSeq + " ] - " + stateNum + " States");
        generalJframe.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension componentDimension = new Dimension(screenDimension.width / (Config.getCLUSTERNUM() + 1), screenDimension.height / (Config.getCLUSTERNUM() + 1));
        Point original = new Point(0, 0);
        // print out state duration distribution
        for(int i = 0; i < stateNum; i++) {
            double alpha = this.mParameters[i][0];
            double lambda = this.mParameters[i][1];
            double delta = this.mParameters[i][2];

            WeibullDist wd = null;

            // if invalid parameters set up
            if (alpha <= 0 || lambda <= 0) {
                // create a plot of probability density estimation
                wd = new WeibullDist(Double.POSITIVE_INFINITY, 1, 0);

            } else {
                // create a plot of probability density estimation
                wd = new WeibullDist(alpha, lambda, delta);

            }

            int min = this.scopeForStateDurations.get(i).get(0);
            int max = this.scopeForStateDurations.get(i).get(1);


            int stateSeq = i + 1; // modulo// current state sequence value under total states scope
            ContinuousDistChartAdapter chart = new ContinuousDistChartAdapter(wd, min, max, max - min + 1);
            chart.setDensityChartFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12));
            JFrame jf = chart.viewDensity(300, 400);

            // put all individual frames into a frame
            Component component = jf.getComponent(0);
            component.setLocation(original);

            generalJframe.add(component, c);
            original = new Point(original.x + component.getBounds().width, original.y);

            // set frame title
            jf.setTitle("Model [ " + modelSeq + " ] --- State [ " + stateSeq + " ]");
            jf.setVisible(false); // hide individual frame figures
        }

        generalJframe.pack();
        generalJframe.setLocation(screenDimension.width, (modelSeq - 1) * screenDimension.height / Config.getCLUSTERNUM());
        generalJframe.setSize(componentDimension);
        generalJframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        generalJframe.setVisible(true);

    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
