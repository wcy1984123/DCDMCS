package dao;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 09:40
 * System Time: 9:40 AM
 */

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
    public int[][] getDataSource(String datapath, String args) {
        // TODO
        return new int[0][];
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        //DaoInput test = new DaoInput();
    }

}