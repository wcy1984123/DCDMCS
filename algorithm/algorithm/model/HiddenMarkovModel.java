package model;

import Utilities.Utilities;
import cluster.ICluster;
import starter.Config;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
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

public class HiddenMarkovModel implements IModel, ICluster {
    private static final Logger LOGGER = Logger.getLogger(HiddenMarkovModel.class.getName());

    private int stateNum;
    private int emitNum;
    private double[] initialTranMatrix; // initial state transition matrix
    private double[][] tranMatrix; // state transition matrix
    private double[][] emitMatrix; // emission transition matrix
    private static int Seq = 0;
    private int curSeq;

    // weka package variables
    private HMMAdapter mHmm; // hidden markov model
    private Instances mTrainWekaInstances; // weka formated instances
    private Instances mTestWekaInstances; // weka formated instances
    /**
     * class constructor
     */
    public HiddenMarkovModel() {
        this.mHmm = null;
        this.mTestWekaInstances = null;
        this.mTestWekaInstances = null;
        this.curSeq = Seq++;
        this.stateNum = Config.getSTATENUM();
        this.emitNum = Config.getSTATENUM();
    }

    /**
     * class constructor
     */
    @Deprecated
    public HiddenMarkovModel(int stateNum, int emitNum) {
        this.stateNum = stateNum;
        this.emitNum = emitNum;
        tranMatrix = new double[stateNum][stateNum];
        emitMatrix = new double[stateNum][emitNum];
        initializeHMM();
        this.curSeq = Seq++;
    }

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {

        if (instances == null) {
            LOGGER.info("The instances are null!");
            this.mTrainWekaInstances = null;
            this.mHmm = null;
            this.initialTranMatrix = new double[this.stateNum];
            this.tranMatrix = new double[this.stateNum][this.stateNum];
            this.emitMatrix = new double[this.stateNum][this.stateNum];
            return;
        }

        if (instances.size() == 0) {
            LOGGER.info("The instances are empty!");
            this.mTrainWekaInstances = null;
            this.mHmm = null;
            this.initialTranMatrix = new double[this.stateNum];
            this.tranMatrix = new double[this.stateNum][this.stateNum];
            this.emitMatrix = new double[this.stateNum][this.stateNum];
            return;
        }

        this.mTrainWekaInstances = convertDataToInstances(instances);
        this.mHmm = new HMMAdapter();
        this.mHmm.setNumStates(Config.getSTATENUM());
//        if (this.mHmm.isNumeric()) System.out.println("         It is not discrete HMM.");
//        else System.out.println("           It is a discrete HMM.");
//        System.out.println("        The number of states in HMM: " + this.mHmm.getNumStates());
//        System.out.println("        The number of outputs in HMM: " + this.mHmm.getNumOutputs());
//        System.out.println("        The dimension of outputs in HMM: " + this.mHmm.getOutputDimension());
//        System.out.println("        The dimension of classes in HMM: " + this.mHmm.getNumClasses());

        try {
            this.mHmm.buildClassifier(this.mTrainWekaInstances);
            HMMEstimatorAdapter[] estimators = this.mHmm.getEstimators();

            if (estimators != null) {
                this.initialTranMatrix = estimators[0].getInitialStateTransitionMatrix();
                this.tranMatrix = estimators[0].getStateTransitionMatrix();
                this.emitMatrix = estimators[0].getOutputTransitionMatrix();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    /**
     * Compute the posterior probability of instances given the model
     * @param instances instances matrix
     * @return the posterior probability of instances given the model
     */
    @Override
    public double[] getInstancesProbs(List<List<Double>> instances) {

        double[] probsOfAllInstances = null;

        if (instances == null) {
            LOGGER.info("The instances are null!");
            this.mTestWekaInstances = null;
            return probsOfAllInstances;
        }

        if (instances.size() == 0 ) {
            LOGGER.info("The instances are empty!");
            this.mTestWekaInstances = null;
            return probsOfAllInstances;
        }

        this.mTestWekaInstances = convertDataToInstances(instances);

        probsOfAllInstances = new double[this.mTestWekaInstances.size()];

        // if the trained HMM is null
        if (this.mHmm == null) {
            for (int i = 0; i < this.mTestWekaInstances.size(); i++) {
                probsOfAllInstances[i] = Double.NEGATIVE_INFINITY;
            }
        } else {
            try {
                for (int i = 0; i < this.mTestWekaInstances.size(); i++) {

                    double[] probs = this.mHmm.distributionForInstance(this.mTestWekaInstances.get(i));
                    probsOfAllInstances[i] = Math.log(probs[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return probsOfAllInstances;
    }

    /**
     * Convert instances data into weka formated instances
     * @param instances instances data
     * @return weka formated instances
     */
    private Instances convertDataToInstances(List<List<Double>> instances) {

        Instances seqs = null;
        if (instances == null) {
            LOGGER.info("The instances are null!");
            return seqs;
        }

        if (instances.size() == 0) {
            LOGGER.info("The instances are empty!");
            return seqs;
        }

        int numseqs = instances.size();
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        ArrayList<String> seqIds = new ArrayList<String>();

        for (int i = 0; i < numseqs; i++) seqIds.add("seq_"+i);
        attrs.add(new Attribute("seq-id", seqIds));

        ArrayList<String> classNames = new ArrayList<String>();
        for (int i = 0; i < 1; i++) classNames.add("class_"+i);
        attrs.add(new Attribute("class", classNames));

        ArrayList<Attribute> seqAttrs = new ArrayList<Attribute>();

        ArrayList<String> outputs = new ArrayList<String>();

        // It must be between [0 StateNum] since it looks for output_1, output_2, ..., output_StateNum
        // It must start with index 0 in case for the search process in the next step
        for(int i = 0; i <= Config.getSTATENUM(); i++) outputs.add("output_"+i);
        seqAttrs.add(new Attribute("output", outputs));

        Instances seqHeader = new Instances("seq", seqAttrs, 0);
        attrs.add(new Attribute("sequence", seqHeader));

        seqs = new Instances("test", attrs, numseqs);
        seqs.setClassIndex(1);

        for (int seq = 0; seq < numseqs; seq++)
        {
            // get instance data
            List<Double> eachInstance = instances.get(seq);

            //System.out.println("sampling sequence "+ seq);
            seqs.add(new DenseInstance(3));
            Instance inst = seqs.lastInstance();
            inst.setValue(0, seqIds.get(seq));
            inst.setValue(1, classNames.get(0));
            //System.out.print("class "+classId+":");

            Instances sequence = new Instances(seqIds.get(seq), seqAttrs, eachInstance.size());
            int[] integerSeq = Utilities.convertToOneDimensionalIntegerArray(eachInstance);

            for (int i = 0; i < eachInstance.size(); i++) {
                sequence.add(new DenseInstance(1));
                Instance frame = sequence.lastInstance();

                frame.setValue(0, integerSeq[i]);
            }

            Attribute seqA = seqs.attribute(2);
            inst.setValue(seqA, seqA.addRelation(sequence));
        }

        // save weka-formated instances into file
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/sleepsequences.arff"));

            bw.write(seqs.toString());
            bw.flush();
            bw.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return seqs;
    }

    /**
     * Visualize output of the dynamic model
     */
    @Override
    public void visualizeOutput() {
        int stateNum = Config.getSTATENUM();
        int modelSeq = this.curSeq % Config.getCLUSTERNUM() + 1; // modulo current model sequence value under total clusters scope
        System.out.println("\n               -------- Model [ " + modelSeq + " ] -------- ");

        // print out initial state transition matrix
        System.out.print("                          Initial State Matrix\n");
        for (int i = 0; i < stateNum; i++) {
            System.out.print("            " + String.format("%.4f", this.initialTranMatrix[i]) + " ");
        }
        System.out.println();
        System.out.println();

        // print out state transition matrix
        System.out.print("                      State Transition Matrix\n");
        for (int i = 0; i < stateNum; i++) {
            for (int j = 0; j < stateNum; j++) {

                System.out.print("            " + String.format("%.4f", this.tranMatrix[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();

        // print out output transition matrix
        System.out.print("                      Emission Output Matrix\n");
        for (int i = 0; i < stateNum; i++) {
            for (int j = 0; j < stateNum; j++) {

                System.out.print("            " + String.format("%.4f", this.emitMatrix[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println();
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
     * getter of initialTranMatrix member variable
     * @return initialTranMatrix
     */
    public double[] getInitialTranMatrix() {
        return this.initialTranMatrix;
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
