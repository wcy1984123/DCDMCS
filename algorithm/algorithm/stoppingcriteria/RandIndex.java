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
 * The Rand index penalizes both false positive and false negative decisions during clustering.
 * From: http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
 */

/*
%Confusion_Matrix should be n by n matrix, otherwise it should not be
%input.
 */
public class RandIndex extends AbstractClusterAgreement implements IStoppingCriteria {

    private final static Logger LOGGER = Logger.getLogger(RandIndex.class.getName());

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return the degree of clustering agreement; 1 refers to be the same clustering results; 0 refers to be the totally different clustering results
     */
    @Override
    public double computeSimilarity(int[] cluster1, int[] cluster2){
        double res = computeRandIndex(cluster1, cluster2);
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
    private double computeRandIndex(int[] cluster1, int[] cluster2) {

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
        randIndex = randIndex(confusionMatrix);
        return randIndex;
    }

    /**
     * Calculate the rand index
     * @param confusionMatrix confusion matrix containing false positive, true positive, false negative, and false positive
     * @return rand index
     */
    private double randIndex(int[][] confusionMatrix) {

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

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute TP + FP%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int TPFP = 0;
        for (int i = 0; i < ColumnNum; i++) {
            int n = 0;
            for (int j = 0; j < RowNum; j++) n+= confusionMatrix[j][i];

            if (n >= 2) TPFP += (int)Combinations.nchoosek(n, 2);
        }

//        System.out.println("TPFP = " + TPFP);
        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute TP%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int TP = 0;
        int sum = 0;
        for (int i = 0; i < RowNum; i++) {
            for (int j = 0; j < ColumnNum; j++) {
                sum += confusionMatrix[i][j];
                if (confusionMatrix[i][j] > 1) {
                    TP += (int)Combinations.nchoosek(confusionMatrix[i][j], 2);
                }
            }
        }

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute Denominator%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int Denominator = (int)Combinations.nchoosek( sum, 2);

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute TN+FN%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int TNFN = Denominator - TPFP;

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute FN%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int FN = 0;
        for (int i = 0; i < RowNum; i++) {
            for (int j = 0; j < ColumnNum; j++) {
                for (int k = (j + 1); k < ColumnNum; k++) {
                    FN = FN + confusionMatrix[i][j] * confusionMatrix[i][k];

                }
            }
        }
        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute TN%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int TN = TNFN - FN;

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute Numerator%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        int Numerator = TP + TN;

        /*%%%%%%%%%%%%%%%%%%%%%%%%%%%%Compute Result%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%*/
        double RandIndex = 1.0 * Numerator / Denominator;
        LOGGER.log(Level.INFO, "TP: " + TP + " TN: " + TN);
        LOGGER.log(Level.INFO, "Numberator: " + Numerator + " Denominator: " + Denominator);

        return RandIndex;
    }


    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        RandIndex test = new RandIndex();
        int[][] confusionMatrix = new int[2][2];
        confusionMatrix[0][0] = 20;
        confusionMatrix[0][1] = 24;
        confusionMatrix[1][0] = 20;
        confusionMatrix[1][1] = 72;
        System.out.println(test.randIndex(confusionMatrix));

        int[] a1 = new int[]{0, 1, 1, 1, 0, 2, 2};
        int[] a2 = new int[]{1, 0, 0, 1, 1, 2, 2};
        System.out.println(test.computeRandIndex(a1, a2));

        a1 = new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};
        a2 = new int[]{1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 3, 2, 1, 3, 1, 3, 3};
        System.out.println(test.computeRandIndex(a1, a2));
    }
}
