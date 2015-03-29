package dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
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
        return readFile(path, args);
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
    private double[][] readFile(String datapath, String lengthpath) {

        List<List<Double>> cache = new ArrayList<List<Double>>();
        List<Double> sessionData = getData(datapath);
        List<Double> sessionLength = getData(lengthpath);

        if (sessionData == null || sessionData.size() == 0) {
            LOGGER.log(Level.INFO, "session data is null or empty!");
            return null;
        }

        if (sessionLength == null | sessionLength.size() == 0) {
            LOGGER.log(Level.INFO, "session length is null or empty!");
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
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        MSNBCDao test = new MSNBCDao();
    }
}
