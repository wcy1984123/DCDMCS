package Utilities;

/**
 * Project: DCDMC
 * Package: Utilities
 * Date: 28/Mar/2015
 * Time: 20:33
 * System Time: 8:33 PM
 */

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * IO operation class
 */
public class IOOperation {

    private static final Logger LOGGER = Logger.getLogger(IOOperation.class.getName());

    /**
     * Write the input two-dimensional integer matrix into file
     * @param dataMarix two dimensional integer matrix
     * @param path file path
     */
    public static void writeFile(int[][] dataMarix, String path) {

        if (dataMarix == null) {
            LOGGER.info("The input matrix is null!");
            return;
        }

        if (dataMarix.length == 0 || dataMarix[0].length == 0) {
            LOGGER.info("The input matrix is empty!");
            return;
        }

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int ROW = dataMarix.length;
            int COLUMN = dataMarix[0].length;

            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    bw.write(dataMarix[i][j]);

                    if (j < COLUMN - 1) bw.write(' ');
                }
                bw.newLine();
            }

            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input double two-dimensional matrix into file
     * @param dataMarix two dimensional double matrix
     * @param path file path
     */
    public static void writeFile(double[][] dataMarix, String path) {

        if (dataMarix == null) {
            LOGGER.info("The input matrix is null!");
            return;
        }

        if (dataMarix.length == 0 || dataMarix[0].length == 0) {
            LOGGER.info("The input matrix is empty!");
            return;
        }

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int ROW = dataMarix.length;
            int COLUMN = dataMarix[0].length;

            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    bw.write(String.valueOf(dataMarix[i][j]));
                    if (j < COLUMN - 1) bw.write(" ");
                }
                bw.newLine();
            }

            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input array into file
     * @param dataArray an array
     * @param path file path
     */
    public static void writeFile(int[] dataArray, String path) {

        if (dataArray == null) {
            LOGGER.info("The input array is null!");
            return;
        }

        if (dataArray.length == 0) {
            LOGGER.info("The input array is empty!");
            return;
        }

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int N = dataArray.length;

            for (int i = 0; i < N; i++) {
                bw.write(String.valueOf(dataArray[i]));

                if (i < N - 1) bw.write(" ");
            }

            bw.newLine();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input array into file
     * @param data a text
     * @param path file path
     */
    public static void writeFile(String data, String path) {

        if (data == null) {
            LOGGER.info("The data is null!");
            return;
        }

        if (data.length() == 0) {
            LOGGER.info("The data is empty!");
            return;
        }

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));

            bw.write(data);

            bw.newLine();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input list into file
     * @param dataList a list
     * @param path file path
     */
    public static void writeFile(List<Integer> dataList, String path) {

        if (dataList == null) {
            LOGGER.info("The input array is null!");
            return;
        }

        if (dataList.size() == 0) {
            LOGGER.info("The input array is empty!");
            return;
        }

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int N = dataList.size();

            for (int i = 0; i < N; i++) {
                bw.write(dataList.get(i));

                if (i < N - 1) bw.write(' ');
            }

            bw.newLine();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input two dimensional list into file
     * @param data a two dimensional list
     * @param path file path
     */
    public static void writeProbsToFile(List<List<Double>> data, String path) {

        if (data == null) {
            LOGGER.info("The input two dimensional list is null!");
            return;
        }

        if (data.size() == 0) {
            LOGGER.info("The input two dimensional list is empty!");
            return;
        }

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int N = data.size();

            for (int i = 0; i < N; i++) {
                int M =  data.get(i).size();
                for (int j = 0; j < M; j++) {
                    bw.write(String.format("%.4f", data.get(i).get(j)));

                    if (j < M - 1) bw.write(' ');
                }
                bw.newLine();
            }
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the input list into file
     * @param dataList a list
     * @param path file path
     */
    public static void writeFile(double[] dataList, String path) {

        if (dataList == null) {
            LOGGER.info("The input array is null!");
            return;
        }

        if (dataList.length == 0) {
            LOGGER.info("The input array is empty!");
            return;
        }

        try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            int N = dataList.length;

            for (int i = 0; i < N; i++) {
                bw.write(String.format("%.4f", dataList[i]));

                if (i < N - 1) bw.write(' ');
            }

            bw.newLine();
            bw.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read the input two dimensional list into memory
     * @param path file path
     * @return a two dimensional list
     */
    public static List<List<Double>> readProbsFromFile(String path) {

        List<List<Double>> data = new ArrayList<List<Double>>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;

            while((line = br.readLine()) != null) {
                String[] strs = line.split(" ");
                List<Double> eachLine = new ArrayList<Double>();
                for (int i = 0; i < strs.length; i++) {
                    if (strs[i].length() > 0) {
                        eachLine.add(Double.parseDouble(strs[i]));
                    }
                }
                data.add(eachLine);
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Read initial cluster labels into memory
     * @param path file path
     * @return an array of integers
     */
    public static List<Integer> readClusterLabels(String path) {
        List<Integer> clusterLabels = new ArrayList<Integer>(); // cache configuration parameters
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;

            while((line = br.readLine()) != null) {
                String[] strs = line.split(" ");
                for (int i = 0; i < strs.length; i++) {
                    clusterLabels.add(Integer.parseInt(strs[i]));
                }
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return clusterLabels;
    }

    /**
     * Read similarity trendline into memory
     * @param path file path
     * @return an array of doubles
     */
    public static List<Double> readSimilarityTrendline(String path) {
        List<Double> similarityTrendline = new ArrayList<Double>(); // cache configuration parameters
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;

            while((line = br.readLine()) != null) {
                String[] strs = line.split(" ");
                for (int i = 0; i < strs.length; i++) {
                    similarityTrendline.add(Double.parseDouble(strs[i]));
                }
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }

        return similarityTrendline;
    }

    /**
     * Read the input two-dimensional double matrix from the file
     * @param path file path
     */
    public static double[][] readMatrix(String path) {

        double[][] res = null;
        if (path == null || path.length() == 0) {
            LOGGER.info("The input path is null!");
            return res;
        }

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            String[] strs = line.split(" ");
            int ROW = strs.length;
            res = new double[ROW][ROW];

            for (int i = 0; i < ROW; i++) {
                res[0][i] = Double.parseDouble(strs[i]);
            }

            int count = 1;
            while((line = br.readLine()) != null) {
                String [] parts = line.split(" ");
                for (int i = 0; i < ROW; i++) {
                    res[count][i] = Double.parseDouble(parts[i]);
                }
                count++;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Redirect the standard output stream
     * @param area swing testarea component
     * @throws IOException
     */
    public static void console(final JTextArea area) throws IOException {
        final PipedInputStream outPipe = new PipedInputStream();

        // reassign the standard output stream
        System.setOut(new PrintStream(new PipedOutputStream(outPipe), true));

        // redirect the output stream into swing textarea
        new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                Scanner s = new Scanner(outPipe);
                while (s.hasNextLine()){
                    String line = s.nextLine();
                    publish(line + "\n");
                }
                s.close();
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String line : chunks){
                    area.append(line);
                }
            }
        }.execute();
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
