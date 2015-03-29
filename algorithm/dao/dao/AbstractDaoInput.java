package dao;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 09:40
 * System Time: 9:40 AM
 */

import java.util.ArrayList;
import java.util.List;

/**
 * implement how to read various format data file into system
 */
public abstract class AbstractDaoInput implements IDAO {

    /**
     * Generate two dimensional array including data
     * @param datapath source file path
     * @return two dimensional array including data
     */
    @Override
    public double[][] getDataSourceAsMatrix(String datapath, String args) {
        // TODO
        return new double[0][];
    }

    /**
     * Generate list of list of doubles including data
     * @param path source file path
     * @args sparing parameters for other functionality
     * @return list of list of doubles including data
     */
    public List<List<Double>> getDataSourceAsLists(String path, String args) {
        // TODO
        return new ArrayList<List<Double>>();
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        //DaoInput test = new DaoInput();
    }

}