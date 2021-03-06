package stoppingcriteria;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 09:47
 * System Time: 9:47 AM
 */


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Normalized mutual information can be information-theoretically interpreted.
 * From: http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
 */
public class NormalizedMutualInformation  extends AbstractClusterAgreement implements IStoppingCriteria {

    private static final Logger LOGGER = Logger.getLogger(NormalizedMutualInformation.class.getName());

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return the degree of clustering agreement; 1 refers to be the same clustering results; 0 refers to be the totally different clustering results
     */
    @Override
    public double computeSimilarity(int[] cluster1, int[] cluster2){
        double res = computeNormalizedMutualInformation(cluster1, cluster2);
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
     * Compute the normalized mutual information
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return normalized mutual information
     */
    private double computeNormalizedMutualInformation(int[] cluster1, int[] cluster2) {

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
        randIndex = normalizedMutualInformation(confusionMatrix);
        return randIndex;
    }

    /**
     * Calculate the normalized mutual information
     * @param confusionMatrix confusion matrix containing false positive, true positive, false negative, and false positive
     * @return normalized mutual information
     */
    private double normalizedMutualInformation(int[][] confusionMatrix) {

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

        int N = 0;
        for (int i = 0; i < RowNum; i++) {
            for (int j = 0; j < ColumnNum; j++) {
                N += confusionMatrix[i][j];
            }
        }

        LOGGER.log(Level.INFO, "N: " + N);

        double I = 0;
        for (int i = 0; i < RowNum; i++) {
            for (int j = 0; j < ColumnNum; j++) {
                double Part1 = 1.0 * confusionMatrix[i][j] / N;
                int sum1 = 0;
                for (int k = 0; k < ColumnNum; k++) sum1 += confusionMatrix[i][k];
                int sum2 = 0;
                for (int k = 0; k < RowNum; k++) sum2 += confusionMatrix[k][j];
                if (sum1 * sum2 != 0) {
                    double Part2 = (1.0 * N * confusionMatrix[i][j]) / (1.0 * sum1 * sum2);
                    if (Part2 != 0) I += Part1 * Math.log(1.0 * Part2);
                }
            }
        }

        LOGGER.log(Level.INFO, "I: " + I);

        double HCluster = 0;
        for (int i = 0; i < ColumnNum; i++) {
            int sum = 0;
            for (int j = 0; j < RowNum; j++) sum += confusionMatrix[j][i];
            double Temp = 0.0;
            if (N != 0) Temp = 1.0 * sum / N;
            if (Temp != 0) HCluster = HCluster - Temp * Math.log(Temp);
        }

        LOGGER.log(Level.INFO, "HCluster: " + HCluster);

        double HClassification = 0;
        for (int i = 0; i < RowNum; i++) {
            int sum = 0;
            for (int j = 0; j < ColumnNum; j++) sum += confusionMatrix[i][j];
            double Temp = 0.0;
            if (N != 0) Temp = 1.0 * sum / N;
            if (Temp != 0) HClassification = HClassification - Temp * Math.log(Temp);
        }

        LOGGER.log(Level.INFO, "HClassification: " + HClassification);

        // Compute NMI
        double NMI = 0.0;
        if (HCluster + HClassification != 0) {
            NMI = (I * 2) / (HCluster + HClassification);
        } else {
            // When two cluster labels are the same or totally different, NMI is NAN
            NMI = 0.0;
        }

        return NMI;
    }


    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        NormalizedMutualInformation test = new NormalizedMutualInformation();
        int[][] confusionMatrix = new int[2][2];
        confusionMatrix[0][0] = 20;
        confusionMatrix[0][1] = 24;
        confusionMatrix[1][0] = 20;
        confusionMatrix[1][1] = 72;
        System.out.println(test.normalizedMutualInformation(confusionMatrix));

        int[] a1 = new int[]{0, 1, 1, 0, 0, 2, 2};
        int[] a2 = new int[]{1, 0, 0, 1, 1, 2, 2};
        System.out.println(test.computeNormalizedMutualInformation(a1, a2));

        a1 = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        a2 = new int[]{1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 2, 1, 3, 1, 3, 3};
        System.out.println(test.computeNormalizedMutualInformation(a1, a2));
    }
}
