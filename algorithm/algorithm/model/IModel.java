package model;

import cluster.ICluster;

import java.util.List;

/**
 * Project: DCDMC
 * Package: model
 * Date: 27/Mar/2015
 * Time: 07:59
 * System Time: 7:59 AM
 */

/**
 * A single dynamic model
 */
public interface IModel {

    /**
     * Build models over instances
     * @param instances input instances
     */
    public void trainModel(List<List<Double>> instances);

    /**
     * Compute the posterior probability of instances given the model
     * @param instances instances matrix
     * @return the posterior probability of instances given the model
     */
    public double[] getInstancesProbs(List<List<Double>> instances);

    /**
     * Visualize output of the dynamic model
     */
    public void visualizeOutput();

    /**
     * Model name
     * @return model name
     */
    public String getModelName();

}
