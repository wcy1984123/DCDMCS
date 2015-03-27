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
     * Assign instances into clusters in terms of input
     * @param data data matrix
     * @return cluster labels
     */
    @Override
    public int[] assignClusterLabels(int[][] data) {
        return new int[0];
    }
}
