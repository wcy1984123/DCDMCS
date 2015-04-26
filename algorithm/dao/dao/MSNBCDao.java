package dao;

import Utilities.IOOperation;
import starter.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 18:02
 * System Time: 6:02 PM
 */

public class MSNBCDao extends AbstractDaoInput {

    private static final Logger LOGGER = Logger.getLogger(MSNBCDao.class.getName());
    /**
     * Generate two dimensional array including data
     * @param path source file path
     * @param args length info path
     * @return two dimensional array including data
     */
    @Override
    public double[][] getDataSourceAsMatrix(String path, String args) {
        // args refers to length info path
        return readFileInMatrix(path, args);
    }

    /**
     * Generate list of list of doubles including data
     * @param path source file path
     * @args sparing parameters for other functionality
     * @return list of list of doubles including data
     */
    @Override
    public List<List<Double>> getDataSourceAsLists(String path, String args) {
        return readFileInArrayList(path, args);
    }

    /**
     * Load data into memory
     * @param path data file path
     * @return a list of integers
     */
    private List<Double> getData(String path) {
        List<Double> res = new ArrayList<Double>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;
            while ((line = br.readLine()) != null) {
                res.add(Double.parseDouble(line));
            }

            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Combine data and length into two dimensional dataset
     * @param datapath data path
     * @param lengthpath file of length of data path
     * @return two dimensional array
     */
    private double[][] readFileInMatrix(String datapath, String lengthpath) {

        List<List<Double>> cache = new ArrayList<List<Double>>();
        List<Double> sessionData = getData(datapath);
        List<Double> sessionLength = getData(lengthpath);

        if (sessionData == null || sessionData.size() == 0) {
            LOGGER.log(Level.INFO, "session data is null or empty!");
            return null;
        }

        if (sessionLength == null | sessionLength.size() == 0) {
            LOGGER.log(Level.INFO, "session length is null or empty!");
            return null;
        }

        List<Double> data = new ArrayList<Double>();
        int offset = 0;
        int maxlength = 0;
        for (int i = 0; i < sessionLength.size(); i++) {
            data = new ArrayList<Double>();
            double temp = sessionLength.get(i);
            int length = (int)temp;
            for (int j = 0; j < length; j++) {
                data.add(sessionData.get(offset + j));
            }
            maxlength = Math.max(maxlength, length);
            cache.add(data);
        }

        double[][] res = new double[sessionLength.size()][maxlength];

        // copy data in two-dimensional list into a two dimensional array
        for (int i = 0; i < cache.size(); i++) {
            List<Double> sequence = cache.get(i);
            for (int j = 0; j < sequence.size(); j++) {
                res[i][j] = sequence.get(j);
            }

            for (int j = sequence.size(); j < maxlength; j++) {
                res[i][j] = 99;
            }
        }

        return res;
    }

    /**
     * Combine data and length into two dimensional dataset
     * @param datapath data path
     * @param lengthpath file of length of data path
     * @return a  two dimensional list
     */
    private List<List<Double>> readFileInArrayList(String datapath, String lengthpath) {

        List<List<Double>> res = new ArrayList<List<Double>>();
        List<Double> sessionData = getData(datapath);
        List<Double> sessionLength = getData(lengthpath);

        if (sessionData == null || sessionData.size() == 0) {
            LOGGER.log(Level.INFO, "session data is null or empty!");
            return null;
        }

        if (sessionLength == null | sessionLength.size() == 0) {
            LOGGER.log(Level.INFO, "session length is null or empty!");
            return null;
        }

        List<Double> data = new ArrayList<Double>();
        int offset = 0;
        int maxlength = 0;
        for (int i = 0; i < sessionLength.size(); i++) {
            data = new ArrayList<Double>();
            double temp = sessionLength.get(i);
            int length = (int)temp;
            for (int j = 0; j < length; j++) {
                data.add(sessionData.get(offset + j));
            }
            maxlength = Math.max(maxlength, length);
            res.add(data);
        }

        return res;
    }

    /**
     * Remove sequences of more than 500 requests in each record
     * @param datapath dataset file path
     * @param lengthpath dataset length file path
     */
    private void removeOutliers(String datapath, String lengthpath) {

        List<Double> sessionData = getData(datapath); // get data
        List<Double> sessionLength = getData(lengthpath); // get data length

        if (sessionData == null || sessionData.size() == 0) {
            LOGGER.log(Level.INFO, "session data is null or empty!");
            return;
        }

        if (sessionLength == null | sessionLength.size() == 0) {
            LOGGER.log(Level.INFO, "session length is null or empty!");
            return;
        }

        List<List<Double>> res = new ArrayList<List<Double>>();
        List<Double> data = new ArrayList<Double>();
        int offset = 0;
        int maxlength = 0;
        int count = 0;
        for (int i = 0; i < sessionLength.size(); i++) {
            data = new ArrayList<Double>();
            double temp = sessionLength.get(i);
            int length = (int)temp;
            // if the length of a sequence is less than 500
            if (length > 13 && length < 500) {
                for (int j = 0; j < length; j++) {
                    data.add(sessionData.get(offset + j));
                }

                maxlength = Math.max(maxlength, length);
                res.add(data);
            } else {
                count++;
            }
            offset += length;
        }

        System.out.println("Count = " + count);
        System.out.println("Max Length = " + maxlength);

        IOOperation.writeDataAsCSVFile(res, new File("dataset" + File.separator + "msnbcDataRemoveOutliers(higherthan13lessthan500).csv").getAbsolutePath());


    }


    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        MSNBCDao test = new MSNBCDao();

        test.removeOutliers(Config.getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(), Config.getWEBUSERNAVIGATIONBEHAVIORDATALENGTHFILEPATH());

//        List<List<Double>> results = test.readFileInArrayList(Config.getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(), Config.getWEBUSERNAVIGATIONBEHAVIORDATALENGTHFILEPATH());

//        double[][] matrix = test.readFileInMatrix(Config.getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(), Config.getWEBUSERNAVIGATIONBEHAVIORDATALENGTHFILEPATH());

        return;

    }
}
