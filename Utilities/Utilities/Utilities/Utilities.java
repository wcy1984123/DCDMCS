package Utilities;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
     * Convert two dimensional doubles into a list of list of integers
     * @param data list of list of doubles
     * @return a list of list Integers
     */
    public static List<List<Integer>> convertToListOfListOfIntegers(double[][] data) {

        List<List<Integer>> res = null;

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

        for (int i = 0; i < ROW; i++) {
            List<Integer> eachRow = new ArrayList<Integer>();
            for (int j = 0; j < COLUMN; j++) {
                double temp = data[i][j];
                eachRow.add((int)temp);
            }

            res.add(eachRow);
        }

        return res;

    }

    /**
     * Convert list of list of doubles into a list of list of integers
     * @param data list of list of doubles
     * @return a list of list Integers
     */
    public static List<List<Integer>> convertToListOfListOfIntegers(List<List<Double>> data) {

        List<List<Integer>> res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The list of list of doubles is null!");
            return res;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The list of list of doubles is empty!");
            return res;
        }

        int ROW = data.size();

        res = new ArrayList<List<Integer>>();

        for (int i = 0; i < ROW; i++) {
            int COLUMN = data.get(i).size();
            List<Integer> eachRow = new ArrayList<Integer>();
            for (int j = 0; j < COLUMN; j++) {
                double temp = data.get(i).get(j);
                eachRow.add((int) temp);
            }

            res.add(eachRow);
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
     * Convert one dimensional double list into one dimensional integer array
     * @param data a two-dimensional double array
     * @return one dimensional integer array
     */
    public static int[] convertToOneDimensionalIntegerArray(List<Double> data) {

        int[] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The one dimensional list is null!");
            return res;
        }

        if (data.size() == 0) {
            LOGGER.log(Level.INFO, "The one dimensional list is empty!");
            return res;
        }

        int N = data.size();
        res = new int[N];

        for (int i = 0; i < N; i++) {
            double temp = data.get(i);
            res[i] = (int)temp;
        }

        return res;

    }

    /**
     * Convert one dimensional double list into one dimensional double array
     * @param data a one-dimensional double list
     * @return one dimensional double array
     */
    public static double[] convertToOneDimensionalDoubleArray(List<Double> data) {

        double[] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The one dimensional list is null!");
            return res;
        }

        if (data.size() == 0) {
            LOGGER.log(Level.INFO, "The one dimensional list is empty!");
            return res;
        }

        int N = data.size();
        res = new double[N];

        for (int i = 0; i < N; i++) {
            double temp = data.get(i);
            res[i] = temp;
        }

        return res;

    }

    /**
     * Convert one dimensional integer array into one dimensional double array
     * @param data a one-dimensional integer array
     * @return one dimensional double array
     */
    public static double[] convertToOneDimensionalDoubleArray(int[] data) {

        double[] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "The one dimensional array is null!");
            return res;
        }

        if (data.length == 0) {
            LOGGER.log(Level.INFO, "The one dimensional array is empty!");
            return res;
        }

        int N = data.length;
        res = new double[N];

        for (int i = 0; i < N; i++) {
            double temp = data[i];
            res[i] = temp;
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
     * Normalize the given one dimensional array
     * @param array one dimensional matrix
     * @return a normalized one dimensional array
     */
    public static double[] normalizeMatrix(int[] array) {

        double[] res = null;

        if (array == null) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is null!");
            return res;
        }

        if (array.length == 0) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is empty!");
            return res;
        }

        int COLUMN = array.length;
        res = new double[COLUMN];
        double sum = 0.0;
        for (int i = 0; i < COLUMN; i++) {
            sum += array[i];
        }

        if (sum != 0) {
            for (int j = 0; j < COLUMN; j++) {
                res[j] = array[j] * 1.0 / sum;
            }
        }

        return res;
    }

    /**
     * Normalize the given one dimensional array
     * @param array one dimensional matrix
     * @return a normalized one dimensional array
     */
    public static double[] normalizeArray(double[] array) {

        double[] res = null;

        if (array == null) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is null!");
            return res;
        }

        if (array.length == 0) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is empty!");
            return res;
        }

        int COLUMN = array.length;
        res = new double[COLUMN];
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < COLUMN; i++) {
            min = Math.min(min, array[i]);
            max = Math.max(max, array[i]);
        }

        if ((max - min) != 0) {
            for (int j = 0; j < COLUMN; j++) {
                res[j] = (array[j] * 1.0 - min) / (max - min);
            }
        }

        return res;
    }

    /**
     * Normalize the given one dimensional array
     * @param array one dimensional matrix
     * @return a normalized one dimensional array
     */
    public static double[] normalizeArrayByMax(List<Double> array) {

        double[] res = null;

        if (array == null) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is null!");
            return res;
        }

        if (array.size() == 0) {
            LOGGER.log(Level.INFO, "The one dimensional array of integers is empty!");
            return res;
        }

        int COLUMN = array.size();
        res = new double[COLUMN];
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < COLUMN; i++) {
            max = Math.max(max, array.get(i));
        }

        if (max != 0) {
            for (int j = 0; j < COLUMN; j++) {
                res[j] = array.get(j) * 1.0 / max;
            }
        }

        return res;
    }

    /**
     * Normalize list of list of data
     * @param data a list of list of double data
     * @return a array of normalized data
     */
    public static double[] normalizeListOfList(List<List<Double>> data) {
        double[] output = null;
        if (data == null) {
            LOGGER.log(Level.INFO, "The data is null!");
            return output;
        }

        if (data.size() == 0 || data.get(0).size() == 0) {
            LOGGER.log(Level.INFO, "The data is empty!");
            return output;
        }

        int count = 0;
        int length = 0;

        for (int i = 0; i < data.size(); i++) {
            length+= data.get(i).size();
        }

        output = new double[length];

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                double value = data.get(i).get(j);

                // deal with the special case when the value is infinity
                if (Double.isInfinite(value)) {
                    output[count++] = 0;
                } else {
                    output[count++] = value;
                }
            }
        }

        // normaize the array
        return normalizeArray(output);
    }

    /**
     * Retrieve a certain number of data in the array
     * @param data data source
     * @param start start position in the array
     * @param end ending position in the array
     * @return the partial data in the array
     */
    public static double[] retrievePartialDataFromArray(double[] data, int start, int end) {
        double[] output = null;

        if (data == null || data.length == 0) {
            LOGGER.info("The data is null or empty!");
            return output;
        }

        if (start < 0 || start > data.length) {
            LOGGER.info("The starting position is invalid!");
            return output;
        }

        if (end < 0 || end > data.length) {
            LOGGER.info("The ending position is invalid!");
            return output;
        }

        if (start > end) {
            LOGGER.info("The starting position should be less than or equal to ending position!");
            return output;
        }

        output = new double[end - start + 1];

        for (int i = start; i <= end; i++) {
            output[i - start] = data[i];
        }

        return output;
    }

    /**
     * Return an iconimage
     * @param path image file path
     * @return an iconimage
     */
    public static ImageIcon createImageIcon(String path) {
        String filePath = new File(path).getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon((new ImageIcon(filePath)).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        return imageIcon;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }

}
