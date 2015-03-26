package stoppingcriteria;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 10:53
 * System Time: 10:53 AM
 */

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Purity is a simple and transparent evaluation measure.
 * From: http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
 */
public class Purity  extends AbstractClusterAgreement implements IStoppingCriteria{

    private final static Logger LOGGER = Logger.getLogger(Purity.class.getName());

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return the degree of clustering agreement; 1 refers to be the same clustering results; 0 refers to be the totally different clustering results
     */
    @Override
    public double computeSimilarity(int[] cluster1, int[] cluster2){
        double res = computePurity(cluster1, cluster2);
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
     * Compute the purity
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return purity
     */
    private double computePurity(int[] cluster1, int[] cluster2) {
        // create the confusion matrix
        int[][] confusionMatrix = null;
        double purity = 0.0;

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
        purity = purity(confusionMatrix);
        return purity;
    }

    /**
     *
     * @param confusionMatrix
     * @return purity
     */
    private double purity(int[][] confusionMatrix) {
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

        int RowNum = confusionMatrix.length;
        int ColumnNum = confusionMatrix[0].length;
        double purity = 0.0;

        int numerator = 0;
        int denominator = 0;
        for (int i = 0; i < ColumnNum; i++) {
            int max = confusionMatrix[0][i];
            int sum = 0;
            for (int j = 0; j < RowNum; j++) {
                max = Math.max(max, confusionMatrix[j][i]);
                sum += confusionMatrix[j][i];
            }
            numerator = numerator + max;
            denominator = denominator + sum;
        }

        if (denominator > 0) {
            purity = 1.0 * numerator / denominator;
        }

        return purity;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        Purity test = new Purity();
        int[][] confusionMatrix = new int[2][2];
        confusionMatrix[0][0] = 20;
        confusionMatrix[0][1] = 24;
        confusionMatrix[1][0] = 20;
        confusionMatrix[1][1] = 72;
        System.out.println(test.purity(confusionMatrix));

        int[] a1 = new int[]{0, 1, 1, 1, 0, 2, 2};
        int[] a2 = new int[]{1, 0, 0, 1, 1, 2, 2};
        System.out.println(test.computePurity(a1, a2));

        a1 = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        a2 = new int[]{1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 2, 1, 3, 1, 3, 3};
        System.out.println(test.computePurity(a1, a2));
    }
}
