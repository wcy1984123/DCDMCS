package model;

import Utilities.Utilities;
import Utilities.Models;
import cluster.ICluster;
import adapters.ContinuousDistChartAdapter;
import adapters.XYLineChartApdater;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import starter.Config;
import umontreal.iro.lecuyer.charts.XYListSeriesCollection;
import umontreal.iro.lecuyer.probdist.ContinuousDistribution;
import umontreal.iro.lecuyer.probdist.WeibullDist;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.*;
import java.util.List;
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
        int StateNum = Config.getSTATENUM();
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

             // If some sequence has no corresponding state duration information
            if (oneStateDurationDistribution == null) {
                continue;
            } else {
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
        if (Config.isPROBABILITYDENSITYVIEW()) {
            visualizePDFView();
        }

        if (Config.isCUMULATIVEDISTRIBUTIONVIEW()) {
            visualizeCDFView();
        }

    }

    /**
     * Visualize PDF view
     */
    private void visualizePDFView() {
        int stateNum = Config.getSTATENUM();
        int modelSeq = this.curSeq % Config.getCLUSTERNUM() + 1; // modulo current model sequence value under total clusters scope
        System.out.println();
        System.out.println("               -------- Model [ " + modelSeq + " ] -------- ");

        // print out state transition matrix
        for (int i = 0; i < stateNum; i++) {
            for (int j = 0; j < stateNum; j++) {

                System.out.print("            " + String.format("%.4f", this.mStateTransitionProbability[i][j]) + " ");
            }
            System.out.println();
        }

        JFrame generalJframe = new JFrame("Model [ " + modelSeq + " ] - " + stateNum + " States" + " {" + (this.mInstances == null ? 0 : this.mInstances.size()) + " Instances}");
        generalJframe.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension componentDimension = new Dimension(screenDimension.width / (Config.getCLUSTERNUM() + 1), screenDimension.height / (Config.getCLUSTERNUM() + 1));

        if (Config.isCUMULATIVEDISTRIBUTIONVIEW()) {
            componentDimension = new Dimension((int)componentDimension.getWidth(), (int)componentDimension.getHeight() / 2);
        }

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

            // compute the actual data probability distribution
            double[][] actualProbs = normalizeStateDurationsForAState(stateSeq);

            // compute the estimated probability distribution
            double[][] estimatedProbs = computeDensityStateDurationForAState(wd, stateSeq);

            String params = "   Alpha = " + String.format("%.4f", alpha) + " Lambda = " + String.format("%.4f", lambda) + " Delta = " + String.format("%.4f", delta);
            System.out.println(params);
            String title = "Weibull Probability Density Distribution\n" + params;
            XYLineChartApdater chart = new XYLineChartApdater(title, "State Duration", "Probability", actualProbs, estimatedProbs);

            // change font and its size
            JFreeChart jc = chart.getChart();
            TextTitle tt = jc.getTitle();
            tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font

            XYListSeriesCollection collec = chart.getSeriesCollection();
            collec.setColor(0, Config.getCOLORCOLLECTION()[i % Config.getCOLORCOLLECTION().length]);
            collec.setDashPattern(0, "only marks");
            collec.setColor(1, Color.BLACK);
            collec.setName(1, "Weibull Density Estimation");

            JFrame jf = chart.view(300, 400);
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
        generalJframe.setLocation(screenDimension.width - componentDimension.width, (modelSeq - 1) * screenDimension.height / Config.getCLUSTERNUM());
        generalJframe.setSize(componentDimension);
        generalJframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        generalJframe.setVisible(true);
    }

    /**
     * Compute the probability of state durations for a given state
     * @param state a given state
     * @return the probability of state durations for a given state
     */
    private double[][] normalizeStateDurationsForAState(int state) {
        double[][] probs = null;
        if (this.mInstances == null || this.mInstances.size() == 0) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);
        Map<Integer, Integer> stateDuration = map.get(state);

        if (stateDuration == null) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        int[] data = new int[stateDuration.keySet().size()];
        int count = 0;
        for (Integer key : stateDuration.keySet()) {
            data[count++] = stateDuration.get(key);
        }

        double[] probData = Utilities.normalizeMatrix(data); // compute the probability of state duration given a state

        probs = new double[2][count];
        count = 0;
        for (Integer key : stateDuration.keySet()) {
            probs[0][count] = key;
            probs[1][count] = probData[count];
            count++;
        }

        return probs;
    }

    /**
     * Compute the density probabilities of state duration given a state
     * @param cd distribution of the given state
     * @param state a given state
     * @return the density probabilities of state duration given a state
     */
    private double[][] computeDensityStateDurationForAState(ContinuousDistribution cd, int state) {
        double[][] probs = null;
        if (this.mInstances == null || this.mInstances.size() == 0) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);
        Map<Integer, Integer> stateDuration = map.get(state);

        if (stateDuration == null) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        int count = stateDuration.keySet().size();

        probs = new double[2][count];
        count = 0;
        for (Integer key : stateDuration.keySet()) {
            probs[0][count] = key;
            probs[1][count] = cd.cdf(key + 1) - cd.cdf(key);
            count++;
        }

        return probs;
    }

    /**
     * Visualize CDF view
     */
    private void visualizeCDFView() {
        int stateNum = Config.getSTATENUM();
        int modelSeq = this.curSeq % Config.getCLUSTERNUM() + 1; // modulo current model sequence value under total clusters scope

        JFrame generalJframe = new JFrame("Model [ " + modelSeq + " ] - " + stateNum + " States" + " {" + (this.mInstances == null ? 0 : this.mInstances.size()) + " Instances}");
        generalJframe.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension componentDimension = new Dimension(screenDimension.width / (Config.getCLUSTERNUM() + 1), screenDimension.height / (Config.getCLUSTERNUM() + 1));
        componentDimension = new Dimension(componentDimension.width, componentDimension.height / 2);
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
            // compute the actual data probability distribution
            double[][] actualProbs = normalizeCumulativeStateDurationsForAState(stateSeq);

            // compute the estimated cumulative distribution
            double[][] estimatedProbs = computeCumulativeStateDurationForAState(wd, stateSeq);

            String params = "   Alpha = " + String.format("%.4f", alpha) + " Lambda = " + String.format("%.4f", lambda) + " Delta = " + String.format("%.4f", delta);
            System.out.println(params);
            String title = "Weibull Cumulative Density Probability Distribution\n" + params;
            XYLineChartApdater chart = new XYLineChartApdater(title, "State Duration", "Probability", actualProbs, estimatedProbs);

            // change font and its size
            JFreeChart jc = chart.getChart();
            TextTitle tt = jc.getTitle();
            tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font

            XYListSeriesCollection collec = chart.getSeriesCollection();
            collec.setColor(0, Config.getCOLORCOLLECTION()[i % Config.getCOLORCOLLECTION().length]);
            collec.setDashPattern(0, "only marks");
            collec.setColor(1, Color.BLACK);
            collec.setName(1, "Weibull Cumulative Estimation");

            JFrame jf = chart.view(300, 400);
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
        generalJframe.setLocation(screenDimension.width - componentDimension.width, (modelSeq - 1) * screenDimension.height / Config.getCLUSTERNUM() + componentDimension.height);
        generalJframe.setSize(componentDimension);
        generalJframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        generalJframe.setVisible(true);
    }

    /**
     * Compute the cumulative of state durations for a given state
     * @param state a given state
     * @return the probability of state durations for a given state
     */
    private double[][] normalizeCumulativeStateDurationsForAState(int state) {
        double[][] probs = normalizeStateDurationsForAState(state);

        SortedMap<Double, Double> sortedMap = new TreeMap<Double, Double>();

        int COLUMN = probs[0].length;
        for (int i = 0; i < COLUMN; i++) {
            sortedMap.put(probs[0][i], probs[1][i]);
        }

        double cumulativeProbs = 0;
        int count = 0;
        double[][] newProbs = new double[2][COLUMN];
        for (Double key : sortedMap.keySet()) {
            cumulativeProbs += sortedMap.get(key);
            newProbs[0][count] = key;
            newProbs[1][count] = cumulativeProbs;
            count++;
        }

        return newProbs;
    }

    /**
     * Compute the cumulative probabilities of state duration given a state
     * @param cd distribution of the given state
     * @param state a given state
     * @return the density probabilities of state duration given a state
     */
    private double[][] computeCumulativeStateDurationForAState(ContinuousDistribution cd, int state) {
        double[][] probs = null;
        if (this.mInstances == null || this.mInstances.size() == 0) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        Map<Integer, Map<Integer, Integer>> map = Models.countStateDurationForSequences(this.mInstances);
        Map<Integer, Integer> stateDuration = map.get(state);

        if (stateDuration == null) {
            probs = new double[2][1];
            probs[0] = new double[]{0.0};
            probs[1] = new double[]{0.0};
            return probs;
        }

        int count = stateDuration.keySet().size();

        probs = new double[2][count];
        count = 0;
        for (Integer key : stateDuration.keySet()) {
            probs[0][count] = key;
            probs[1][count] = cd.cdf(key);
            count++;
        }

        return probs;
    }

    /**
     * Model name
     * @return model name
     */
    @Override
    public String getModelName() {
        return "Semi-Markov Chain Model";
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
