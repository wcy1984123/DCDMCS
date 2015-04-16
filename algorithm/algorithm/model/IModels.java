package model;

/**
 * Project: DCDMC
 * Package: model
 * Date: 28/Mar/2015
 * Time: 17:22
 * System Time: 5:22 PM
 */

import java.util.List;

/**
 * Dynamic models for this system
 */
public interface IModels {

    /**
     * Build models over instances
     * @param instances input instances
     * @param clusterNum cluster num
     * @param initialClusterLables initial cluster labels
     * @param mt dynamic model type
     */
    public void trainDynamicModels(List<List<Double>> instances, int clusterNum, int[] initialClusterLables, MODELTYPE mt);

    /**
     * Assign instances into clusters in terms of input, cluster label starts with index 0
     * @param instances data matrix
     * @return cluster labels
     */
    public int[] assignClusterLabels(List<List<Double>> instances);


    /**
     * Visualize output of the dynamic model
     */
    public void visualizeOutputs();

    /**
     * Model name
     * @return model name
     */
    public String getModelName();

}
