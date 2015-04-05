package model;

import cluster.ICluster;
import starter.Config;
import weka.classifiers.bayes.HMM;
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
    private double[][] tranMatrix; // state transition matrix
    private double[][] emitMatrix; // emission transition matrix

    private HMM mHmm; // hidden markov model
    private Instances mTrainWekaInstances; // weka formated instances
    private Instances mTestWekaInstances; // weka formated instances
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

        this.mTrainWekaInstances = convertDataToInstances(instances);
        this.mHmm = new HMM();
        this.mHmm.setNumStates(Config.getSTATENUM());
        this.mHmm.setIterationCutoff(0.01);
        if (this.mHmm.isNumeric()) System.out.println("       It is not discrete HMM.");
        else System.out.println("       It is a discrete HMM.");
        System.out.println("        The number of states in HMM: " + this.mHmm.getNumStates());
        System.out.println("        The number of outputs in HMM: " + this.mHmm.getNumOutputs());
        System.out.println("        The dimension of outputs in HMM: " + this.mHmm.getOutputDimension());
        System.out.println("        The dimension of classes in HMM: " + this.mHmm.getNumClasses());

        try {
            this.mHmm.buildClassifier(this.mTrainWekaInstances);
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

        this.mTestWekaInstances = convertDataToInstances(instances);

        double[] probsOfAllInstances = new double[this.mTestWekaInstances.size()];

        try {
            for (int i = 0; i < this.mTestWekaInstances.size(); i++) {
                double[] probs = this.mHmm.distributionForInstance(this.mTestWekaInstances.get(i));
                probsOfAllInstances[i] = probs[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return probsOfAllInstances;
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
     * Convert instances data into weka formated instances
     * @param instances instances data
     * @return weka formated instances
     */
    private Instances convertDataToInstances(List<List<Double>> instances) {

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
        for(int i = 0; i < 1; i++) outputs.add("output_"+i);
        seqAttrs.add(new Attribute("output", outputs));

        Instances seqHeader = new Instances("seq", seqAttrs, 0);
        attrs.add(new Attribute("sequence", seqHeader));

        Instances seqs = new Instances("test", attrs, numseqs);
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

            for (int i = 0; i < eachInstance.size(); i++) {
                sequence.add(new DenseInstance(1));
                Instance frame = sequence.lastInstance();

                frame.setValue(0, eachInstance.get(i));
            }

            Attribute seqA = seqs.attribute(2);
            inst.setValue(seqA, seqA.addRelation(sequence));
        }

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
     * Visualize output of the dynamic model
     */
    @Override
    public void visualizeOutput() {

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
