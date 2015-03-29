package dao;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 09:39
 * System Time: 9:39 AM
 */

import java.util.List;

/**
 * define how to read various format data file into system
 */
public interface IDAO {

    /**
     * Generate two dimensional array including data
     * @param path source file path
     * @args sparing parameters for other functionality
     * @return two dimensional array including data
     */
    public double[][] getDataSourceAsMatrix(String path, String args);

    /**
     * Generate list of list of doubles including data
     * @param path source file path
     * @args sparing parameters for other functionality
     * @return list of list of doubles including data
     */
    public List<List<Double>> getDataSourceAsLists(String path, String args);
}
