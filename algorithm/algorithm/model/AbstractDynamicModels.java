package model;

/**
 * Project: DCDMC
 * Package: model
 * Date: 28/Mar/2015
 * Time: 17:25
 * System Time: 5:25 PM
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Abstract class of dynamic models
 */
abstract public class AbstractDynamicModels implements IModels {

    private static final Logger LOGGER = Logger.getLogger(AbstractDynamicModels.class.getName());

    private int mClusterNum; // cluster num
    private List<IModel> mModels; // dynamic models

    public AbstractDynamicModels() {
        this.mClusterNum = 0;
        this.mModels = null;
    }

    /**
     * Build models over instances
     * @param instances input instances
     * @param clusterNum cluster num
     * @param mt model type
     */
    @Override
    public void trainDynamicModels(List<List<Double>> instances, int clusterNum, MODELTYPE mt) {

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

        // build dynamic models
        for (int i = 0; i < clusterNum; i++) {
            this.mModels.get(i).trainModel(instances);
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
        }

        return clusterLabels;
    }
}
