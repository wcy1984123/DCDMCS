package Utilities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: Utilities
 * Date: 22/Mar/2015
 * Time: 14:46
 * System Time: 2:46 PM
 */
public class Utilities {
    private final static Logger LOGGER = Logger.getLogger(Utilities.class.getName());

    /**
     * Maximum element of an array
     * @param array an array of integers
     * @return maximum value of the array
     */
    public static int maxOfArray(int[] array) {
        if (array == null || array.length == 0) {
            LOGGER.log(Level.INFO, "The input array is null!");
            return Integer.MIN_VALUE;
        }

        int res = array[0];
        for (int i = 1; i < array.length; i++) res = Math.max(res, array[i]);

        return res;
    }

    /**
     * Count state transition of a sequence in a matrix
     * @param seq a sequence of data whose index starts with 0
     * @return a matrix of state transition
     */
    public static int[][] countStateTransition(int[] seq) {

        // a sequence of data whose index starts with 0
        int[][] stateTransition = null;
        if (seq == null || seq.length < 2) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        // count the number of unique elements in the sequence
        int uniqueCount = 0;
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < seq.length; i++) {
            if (!set.contains(seq[i])) {
                uniqueCount++;
                set.add(seq[i]);
            }
        }

        // check at least there are two unique elements and thus there is at least one state transition
        if (uniqueCount < 2) {
            LOGGER.log(Level.INFO, "No more than 2 unique elements and thus no state transition exists!");
            return stateTransition;
        }

        stateTransition = new int[uniqueCount][uniqueCount];

        // count state transition
        for (int i = 0; i < seq.length - 1; i++) {
            stateTransition[seq[i]][seq[i + 1]]++;
        }

        return stateTransition;
    }

    /**
     * Print out a two-dimensional matrix
     * @param matrix a two-dimensional matrix
     */
    public static void printMatrix (int[][] matrix) {
        if (matrix == null) {
            LOGGER.log(Level.INFO, "The matrix is null!");
            return;
        }

        if (matrix.length == 0 || matrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The matrix is empty!");
            return;
        }

        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Print out a two-dimensional matrix
     * @param matrix a two-dimensional matrix
     */
    public static void printMatrix (double[][] matrix) {
        if (matrix == null) {
            LOGGER.log(Level.INFO, "The matrix is null!");
            return;
        }

        if (matrix.length == 0 || matrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The matrix is empty!");
            return;
        }

        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Print out a two-dimensional lists
     * @param matrix a two-dimensional lists
     */
    public static void printMatrix (List<List<Integer>> matrix) {
        if (matrix == null) {
            LOGGER.log(Level.INFO, "The matrix is null!");
            return;
        }

        if (matrix.size() == 0 || matrix.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The matrix is empty!");
            return;
        }

        System.out.println();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(0).size(); j++) {
                System.out.print(matrix.get(i).get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Print out a one-dimensional array
     * @param array a one-dimensional array
     */
    public static void printArray (int[] array) {
        if (array == null) {
            LOGGER.log(Level.INFO, "The array is null!");
            return;
        }

        if (array.length == 0) {
            LOGGER.log(Level.INFO, "The array is empty!");
            return;
        }

        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Print out a one-dimensional list
     * @param array a one-dimensional list
     */
    public static void printArray (List<Integer> array) {
        if (array == null) {
            LOGGER.log(Level.INFO, "The array is null!");
            return;
        }

        if (array.size() == 0) {
            LOGGER.log(Level.INFO, "The array is empty!");
            return;
        }

        System.out.println();
        for (int i = 0; i < array.size(); i++) {
            System.out.print(array.get(i) + " ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Convert list of list of integers into two dimensional array
     * @param data list of list of integers
     * @return a two-dimensional array
     */
    public static int[][] convertToTwoDimensionIntegerArray(List<List<Integer>> data, int format) {

        int[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of integers is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of lsit of integers is empty!");
            return res;
        }

        int ROW = data.size();

        // since each row may have different length, it is necessary to get the maximum value among column as the final column
        int COLUMN = data.get(0).size();
        for (int i = 1; i < ROW; i++) {
            COLUMN = Math.max(COLUMN, data.get(i).size());
        }

        res = new int[ROW][COLUMN];

        for (int i = 0; i < ROW; i++) {
            int colNum = data.get(i).size();
            for (int j = 0; j < colNum; j++) {
                res[i][j] = data.get(i).get(j);
            }

            for (int j = colNum; j < COLUMN; j++) {
                res[i][j] = 1; // padding values with 1
            }
        }

        return res;

    }

    /**
     * Convert list of list of doubles into two dimensional array
     * @param data list of list of doubles
     * @return a two-dimensional double array
     */
    public static double[][] convertToTwoDimensionDoubleArray(List<List<Double>> data) {

        double[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of integers is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of lsit of integers is empty!");
            return res;
        }

        int ROW = data.size();

        // since each row may have different length, it is necessary to get the maximum value among column as the final column
        int COLUMN = data.get(0).size();
        for (int i = 1; i < ROW; i++) {
            COLUMN = Math.max(COLUMN, data.get(i).size());
        }

        res = new double[ROW][COLUMN];

        for (int i = 0; i < ROW; i++) {
            int colNum = data.get(i).size();
            for (int j = 0; j < colNum; j++) {
                res[i][j] = data.get(i).get(j);
            }

            for (int j = colNum; j < COLUMN; j++) {
                res[i][j] = 1; // padding values with 1
            }
        }

        return res;

    }

}
