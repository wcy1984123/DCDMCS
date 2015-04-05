package model;

import java.util.List;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: model
 * Date: 28/Mar/2015
 * Time: 17:40
 * System Time: 5:40 PM
 */
public class StateBasedDynamicModels extends AbstractDynamicModels {

    private static final Logger LOGGER = Logger.getLogger(StateBasedDynamicModels.class.getName());

    /**
     * class constructor
     */
    public StateBasedDynamicModels() {
        super();
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



        // train dynamic models
        super.trainDynamicModels(instances, clusterNum, initialClusterLables, mt);
    }

    /**
     * Assign instances into clusters in terms of input, cluster label starts with index 0
     * @param instances data matrix
     * @return cluster labels
     */
    @Override
    public int[] assignClusterLabels(List<List<Double>> instances) {
        return super.assignClusterLabels(instances);
    }

    /**
     * Visualize outputs of all dynamic models
     */
    @Override
    public void visualizeOutputs() {
        super.visualizeOutputs();
    }
}
