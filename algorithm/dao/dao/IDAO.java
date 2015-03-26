package dao;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 09:39
 * System Time: 9:39 AM
 */

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
    public int[][] getDataSource(String path, String args);
}
