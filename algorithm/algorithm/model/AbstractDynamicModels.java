package model;

/**
 * Project: DCDMC
 * Package: model
 * Date: 28/Mar/2015
 * Time: 17:25
 * System Time: 5:25 PM
 */

import adapters.HistogramChartAdapter;
import starter.Config;
import umontreal.iro.lecuyer.charts.HistogramSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Abstract class of dynamic models
 */
abstract public class AbstractDynamicModels implements IModels{

    private static final Logger LOGGER = Logger.getLogger(AbstractDynamicModels.class.getName());

    private List<IModel> mModels; // dynamic models

    // GUI Variable
    private double[] mClusterLabelDistributions; // cluster labels distribution
    private int mMaxSizeInOneClusters; // maximum number of instances in one cluster

    /**
     * Class constructor
     */
    public AbstractDynamicModels() {
        this.mModels = null;
    }


    /**
     * Build models over instances
     * @param instances input instances
     * @param clusterNum cluster num
     * @param initialClusterLables initial cluster labels
     * @param mt model type
     */
    @Override
    public void trainDynamicModels(List<List<Double>> instances, int clusterNum, int[] initialClusterLables, MODELTYPE mt) {

        if (instances == null) {
            LOGGER.info("The instances are null!");
            return;
        }

        if (instances.size() == 0) {
            LOGGER.info("The instances are empty!");
            return;
        }

        if (clusterNum <= 0) {
            LOGGER.info("The number of dynamic models are 0!");
            return;
        }

        this.mModels = new ArrayList<IModel>(); // dynamic models

        for (int i = 0; i < clusterNum; i++) {
            this.mModels.add(ModelFactory.getInstance().createModel(mt));
        }

        // cluster each instance into corresponding clusters
        Map<Integer, List<List<Double>>> clusterInstances = new HashMap<Integer, List<List<Double>>>();
        for (int i = 0; i < instances.size(); i++) {
            int clusterNo = initialClusterLables[i];
            if (clusterInstances.containsKey(clusterNo)) {
                clusterInstances.get(clusterNo).add(instances.get(i));

            } else {
                List<List<Double>> list = new ArrayList<List<Double>>();
                list.add(instances.get(i));
                clusterInstances.put(clusterNo, list);
            }
        }

        // build dynamic models
        for (int i = 0; i < clusterNum; i++) {
            this.mModels.get(i).trainModel(clusterInstances.get(i));

            // output cluster instances distributions
            if (clusterInstances.get(i) == null) {
                System.out.println("        Model[" + (i + 1) + "]: 0 instances.");
            } else {
                System.out.println("        Model[" + (i + 1) + "]: " + clusterInstances.get(i).size() + " instances.");
            }
        }

        // build cluster labels distribution for GUI display
        mClusterLabelDistributions = new double[instances.size()];
        mMaxSizeInOneClusters = 0;
        int count = 0;
        for (int i = 0; i < clusterNum; i++) {
            if (clusterInstances.get(i) == null) {
            } else {
                int dataNum = clusterInstances.get(i).size();
                mMaxSizeInOneClusters = Math.max(mMaxSizeInOneClusters, dataNum);
                for (int j = 0; j < dataNum; j++) {
                    mClusterLabelDistributions[count++] = 2 * i + 1.0;
                }
            }
        }

        return;
    }


    /**
     * Assign instances into clusters in terms of input, cluster label starts with index 0
     * @param instances data matrix
     * @return cluster labels
     */
    @Override
    public int[] assignClusterLabels(List<List<Double>> instances) {

        int[] clusterLabels = null;

        if (instances == null) {
            LOGGER.info("The instances are null!");
            return clusterLabels;
        }

        if (instances.size() == 0) {
            LOGGER.info("The instances are empty!");
            return clusterLabels;
        }

        if (this.mModels == null) {
            LOGGER.info("The dynamic models are null!");
            return clusterLabels;
        }

        if (this.mModels.size() == 0) {
            LOGGER.info("The dynamic models are empty");
            return clusterLabels;
        }

        int ModelsNum = this.mModels.size();
        int InstancesNum = instances.size();

        // compute posterior probabilities of all instances given all models

        double[][] instancesProbsOfModels = new double[ModelsNum][InstancesNum];
        for (int i = 0; i < ModelsNum; i++) {
            instancesProbsOfModels[i] = this.mModels.get(i).getInstancesProbs(instances);
        }

        // cluster assignment
        clusterLabels = new int[InstancesNum];
        int[] clusterLabelsDist = new int[ModelsNum];

        for (int i = 0; i < InstancesNum; i++) {
            double maxProb = instancesProbsOfModels[0][i];
            int index = 0;
            for (int j = 1; j < ModelsNum; j++) {
                double curProb = instancesProbsOfModels[j][i];
                if (maxProb < curProb) {
                    maxProb = curProb;
                    index = j;
                }
            }

            clusterLabels[i] = index;
            clusterLabelsDist[index]++;
        }

        for (int i = 0; i < ModelsNum; i++) {
            System.out.println("        Mode[" + (i + 1) + "]: " + clusterLabelsDist[i] + " instances.");
        }

        return clusterLabels;
    }

    /**
     * Visualize outputs of all dynamic models
     */
    @Override
    public void visualizeOutputs() {
        if (this.mModels == null) {
            LOGGER.info("The outputs are null!");
            return;
        }

        if (this.mModels.size() == 0) {
            LOGGER.info("The outputs are empty!");
            return;
        }

        // background working thread
        SwingWorker worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                System.out.println("\n   ======= Final Models Parameters ======= ");
                for (int i = 0; i < AbstractDynamicModels.this.mModels.size(); i++) {
                    AbstractDynamicModels.this.mModels.get(i).visualizeOutput();
                }

                System.out.println("\n   ============================== \n");
                System.out.println("||************** Cluster & Models Ends *************||\n");
                return null;
            }
        };

        worker.execute();

        visualizeClusterDistributions();
    }

    /**
     * Make a histogram plot on cluster labels distribution
     */
    private void visualizeClusterDistributions() {
        HistogramChartAdapter chart;
        String modelName = this.mModels == null? "" : this.mModels.get(0).getModelName();
        chart = new HistogramChartAdapter("Cluster Distribution [" + modelName + "]", "Clusters", "Frequency", this.mClusterLabelDistributions);
        HistogramSeriesCollection collec = chart.getSeriesCollection();
        collec.setBins(0, 2 * Config.getCLUSTERNUM());
        collec.setColor(0, new Color(179, 232, 172));

        double[] bounds = { 0, 2 * Config.getCLUSTERNUM(), 0, this.mMaxSizeInOneClusters};
        chart.setManualRange(bounds);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        chart.view(screenDimension.width / 3, screenDimension.height / 5);

    }
}
