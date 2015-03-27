package Utilities;

import java.util.ArrayList;
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

/*

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
     * @param format only just for differ from the same function name
     * @return a two-dimensional array
     */
    public static int[][] convertToTwoDimensionIntegerArray(List<List<Integer>> data, int format) {

        int[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of integers is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of list of integers is empty!");
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
    public static int[][] convertToTwoDimensionIntegerArray(List<List<Double>> data) {

        int[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of doubles is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of list of doubles is empty!");
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
                double temp = data.get(i).get(j);
                res[i][j] = (int)temp;
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
    public static int[][] convertToTwoDimensionIntegerArray(double[][] data) {

        int[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of doubles is null!");
            return res;
        }

        if (data.length == 0 || data[0].length == 0) {
            LOGGER.log(Level.INFO, "The list of list of doubles is empty!");
            return res;
        }

        int ROW = data.length;
        int COLUMN = data[0].length;

        res = new int[ROW][COLUMN];

        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                double temp = data[i][j];
                res[i][j] = (int)temp;
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
            LOGGER.log(Level.INFO, "The list of list of doubles is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of list of doubles is empty!");
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

    /**
     * Convert two dimensional array into a list of list of doubles
     * @param data a two-dimensional double array
     * @return list of list of doubles
     */
    public static List<List<Double>> convertToTwoDimensionalDoubleList(double[][] data) {

        List<List<Double>> res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The two dimensional array of doubles is null!");
            return res;
        }

        if (data.length == 0 || data[0].length == 0) {
            LOGGER.log(Level.INFO, "The two dimensional array of doubles is empty!");
            return res;
        }

        int ROW = data.length;
        int COLUMN = data[0].length;
        res = new ArrayList<List<Double>>();

        for (int i = 0; i < ROW; i++) {
            List<Double> list = new ArrayList<Double>();
            for (int j = 0; j < COLUMN; j++) {
                list.add(data[i][j]);
            }

            res.add(list);

        }

        return res;

    }

    /**
     * Normalize the given two dimensional matrix
     * @param matrix two dimensional matrix
     * @return a normalized two dimensional matrix
     */
    public static double[][] normalizeMatrix(int[][] matrix) {

        double[][] res = null;

        if (matrix == null) {
            LOGGER.log(Level.INFO, "The two dimensional array of integers is null!");
            return res;
        }

        if (matrix.length == 0 || matrix[0].length == 0) {
            LOGGER.log(Level.INFO, "The two dimensional array of integers is empty!");
            return res;
        }

        int ROW = matrix.length;
        int COLUMN = matrix[0].length;
        res = new double[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
            double sum = 0.0;
            for (int j = 0; j < COLUMN; j++) {
                sum += matrix[i][j];
            }

            if (sum != 0) {
                for (int j = 0; j < COLUMN; j++) {
                    res[i][j] = (matrix[i][j] * 1.0 / sum);
                }
            }
        }

        return res;
    }



    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }

}
