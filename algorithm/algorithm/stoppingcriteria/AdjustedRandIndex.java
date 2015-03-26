package stoppingcriteria;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 09:46
 * System Time: 9:46 AM
 */

import Utilities.Combinations;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adjusted rand index is to overcome a problem with the Rand index:
 * that is that the expected value of the Rand index of two random partitions does not take a constant value (say zero).
 * From: http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
 */

/*
    Computes the adjusted Rand index to assess the quality of a clustering.
    Perfectly random clustering returns the minimum score of 0, perfect
    clustering returns the maximum score of 1.

    INPUTS
    u = the labeling as predicted by a clustering algorithm
    v = the true labeling

    OUTPUTS
    adjrand = the adjusted Rand index


    From: Tijl De Bie, february 2003.
 */

public class AdjustedRandIndex extends AbstractClusterAgreement implements IStoppingCriteria {

    private final static Logger LOGGER = Logger.getLogger(AdjustedRandIndex.class.getName());

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return the degree of clustering agreement; 1 refers to be the same clustering results; 0 refers to be the totally different clustering results
     */
    @Override
    public double computeSimilarity(int[] cluster1, int[] cluster2){
        double res = computeAdjustedRandIndex(cluster1, cluster2);
        return res;
    }

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 a list of integers
     * @param cluster2 a list of integers
     * @return the degree of clustering agreemnt; 1 refers to be the same cluster
     */
    public double computeSimilarity(List<Integer> cluster1, List<Integer> cluster2) {
        int[] c1 = new int[cluster1.size()];
        int[] c2 = new int[cluster2.size()];

        for (int i = 0; i < cluster1.size(); i++) {
            c1[i] = cluster1.get(i);
        }

        for (int i = 0; i< cluster2.size(); i++) {
            c2[2] = cluster2.get(i);
        }

        return computeSimilarity(c1, c2);
    }

    /**
     * Compute the rand index
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return rand index
     */
    private double computeAdjustedRandIndex(int[] cluster1, int[] cluster2) {

        // create the confusion matrix
        int[][] confusionMatrix = null;
        double randIndex = 0.0;

        if (cluster1 == null || cluster1.length == 0) {
            LOGGER.log(Level.INFO, "hierarchicalclustering.Cluster 1 is null or empty!");
            return 0.0;
        }

        if (cluster2 == null || cluster2.length == 0) {
            LOGGER.log(Level.INFO, "hierarchicalclustering.Cluster 2 is null or empty!");
            return 0.0;
        }

        // here call functions in the base class to compute the confusion matrix
        // Confusion Matrix should be n by n matrix, otherwise it should not be input.
        confusionMatrix = computeConfusionMatrix(cluster1, cluster2);

        // compute the rand index
        randIndex = AdjustedRandIndex(confusionMatrix);
        return randIndex;
    }

    /**
     * Calculate the rand index
     * @param confusionMatrix confusion matrix containing false positive, true positive, false negative, and false positive
     * @return rand index
     */
    private double AdjustedRandIndex(int[][] confusionMatrix) {

        if (confusionMatrix == null) {
            LOGGER.log(Level.INFO, "confusion matrix is null!");
            return 0;
        }
        if (confusionMatrix.length == 0) {
            LOGGER.log(Level.INFO, "Row info in confusion matrix is null!");
            return 0;
        }

        if (confusionMatrix[0].length == 0) {
            LOGGER.log(Level.INFO, "Column info in confusion matrix is null!");
            return 0;
        }

        // The lagest NO of cluster label for each of clustering
        int ku = confusionMatrix.length;
        int kv = confusionMatrix[0].length;


        // The number of instances for clustering
        int n = 0;
        for (int i = 0; i < ku; i++) {
            for (int j = 0; j < kv; j++) {
                n += confusionMatrix[i][j];
            }
        }

        // Confusition Matrix
        int[] mu = new int[ku];
        int[] mv = new int[kv];
        for (int i = 0; i < ku; i++) {
            for (int j = 0; j < kv; j++) {
                mu[i] += confusionMatrix[i][j];
                mv[j] += confusionMatrix[i][j];
            }
        }

        long a = 0;
        for (int i = 0; i < ku; i++) {
            for (int j = 0; j < kv; j++) {
                if (confusionMatrix[i][j] > 1) a += Combinations.nchoosek(confusionMatrix[i][j], 2);
            }
        }

        long b1=0;
        for (int i = 0; i < ku; i++) {
            if (mu[i] > 1) b1 += Combinations.nchoosek(mu[i], 2);
        }

        long b2=0;
        for (int i = 0; i < kv; i++) {
            if (mv[i] > 1) b2 += Combinations.nchoosek(mv[i], 2);
        }

        long c = Combinations.nchoosek(n, 2);

        double denominator = 0.5 * (b1 + b2) - 1.0 * b1 * b2 / c;

        double ARI = 0.0;
        if (denominator == 0) {
            ARI = 0; // instances are clusters into totally different clusters like[ 0 0 0]and[1 1 1]
        } else {
            ARI = 1.0 * (a - 1.0 * b1 * b2 / c) / denominator;
        }

        return ARI;
    }


    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        AdjustedRandIndex test = new AdjustedRandIndex();
        int[][] confusionMatrix = new int[2][2];
        confusionMatrix[0][0] = 20;
        confusionMatrix[0][1] = 24;
        confusionMatrix[1][0] = 20;
        confusionMatrix[1][1] = 72;
        System.out.println(test.AdjustedRandIndex(confusionMatrix));

        int[] a1 = new int[]{0, 1, 1, 0, 0, 2, 2};
        int[] a2 = new int[]{1, 0, 0, 1, 1, 2, 2};
        System.out.println(test.computeAdjustedRandIndex(a1, a2));

        a1 = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        a2 = new int[]{1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 2, 1, 3, 1, 3, 3};
        System.out.println(test.computeAdjustedRandIndex(a1, a2));
    }
}
