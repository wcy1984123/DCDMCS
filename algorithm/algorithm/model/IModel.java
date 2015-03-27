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


public interface IModel {

    /**
     * Build models over instances
     * @param instances input instances
     */
    public void trainModel(List<List<Double>> instances);

    /**
     * Assign instances into clusters in terms of input
     * @param data data matrix
     * @return cluster labels
     */
    public int[] assignClusterLabels(int[][] data);

}
