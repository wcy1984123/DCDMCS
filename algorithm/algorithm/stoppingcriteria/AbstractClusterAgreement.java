package stoppingcriteria;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 14:13
 * System Time: 2:13 PM
 */

/**
 * Base class for rand index, adjusted rand index, purity, and normalized mutual information
 */
public abstract class AbstractClusterAgreement{

    private final static Logger LOGGER = Logger.getLogger(AbstractClusterAgreement.class.getName());

    /**
     * Calculate the confusion matrix in terms of cluster label 1 and cluster label 2
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return confusion matrix
     * The confusion matrix derived from taking a clustering as a class label
     * and compare these class labels with another clustering so that it is a way of computing confusion matrix
     * For confusion matrix, the row denotes the classification, namely class label and the column denotes the clustering
     */
    protected int[][] computeConfusionMatrix(int[] cluster1, int[] cluster2) {

        int[][] confusionMatrix = null;

        if (cluster1 == null || cluster1.length == 0) {
            LOGGER.log(Level.INFO, "hierarchicalclustering.Cluster 1 is null or empty!");
            return confusionMatrix;
        }

        if (cluster2 == null || cluster2.length == 0) {
            LOGGER.log(Level.INFO, "hierarchicalclustering.Cluster 2 is null or empty!");
            return confusionMatrix;
        }

        // Determine the maximum NO. of cluster label
        int ClusterNO = Math.max(cluster1.length, cluster2.length);

        // Determine the number of clusters
        // Replace Cluster_Num with Cluster_NO to avoid the problem of lacking some cluster labels in clusterLabel1 or clusterLabel2;
        // Adding 1 comes from the index of cluster label starts with 0 instead of 1
        int ClusterNum = ClusterNO;

        if (cluster1.length != cluster2.length) {
            LOGGER.log(Level.INFO, "Two clustering labels are not consistent in dimensions!");
            return confusionMatrix;
        }

        confusionMatrix = new int[ClusterNum][ClusterNum];

        // Based on clusterLabel1 as class label, compare clusterLabel2 clustering result with classification results, namely, clusterLabel1
        for (int k = 0; k < ClusterNum; k++) {
            // Based on clusterLabel1 as class label
            //Compare clusterLabel2 and compute confusion matrix
            confusionMatrix[cluster1[k]][cluster2[k]] = confusionMatrix[cluster1[k]][cluster2[k]] + 1;
        }

        return confusionMatrix;
    }
}
