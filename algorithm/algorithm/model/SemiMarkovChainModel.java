package model;

import cluster.ICluster;

import java.util.List;

/**
 * Project: DCDMC
 * Package: model
 * Date: 27/Mar/2015
 * Time: 07:57
 * System Time: 7:57 AM
 */

public class SemiMarkovChainModel implements IModel, ICluster{

    /**
     * Build models over instances
     * @param instances input instances
     */
    @Override
    public void trainModel(List<List<Double>> instances) {
        return;
    }

    /**
     * Compute the posterior probability of instances given the model
     * @param instances instances matrix
     * @return the posterior probability of instances given the model
     */
    @Override
    public double[] getInstancesProbs(List<List<Double>> instances) {
        return new double[0];
    }
}
