package dao;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 17:58
 * System Time: 5:58 PM
 */
public class DaoFactory {
    private static DaoFactory ourInstance = new DaoFactory();

    /**
     *
     * @return instance of the class
     */
    public static DaoFactory getInstance() {
        return ourInstance;
    }

    /**
     * Class constructor
     */
    private DaoFactory() {
    }

    /**
     * Generate data source
     * @param dt data type
     * @return data interface
     */
    public IDAO createData(DATATYPE dt) {
        IDAO idao = null;
        switch (dt) {
            case HYPNOGRAM:
                idao = new HypnogramDao();
                break;
            case MSNBC:
                idao = new MSNBCDao();
                break;
            default:
                System.out.println("No Matching Instance To Created!");
        }

        return idao;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        DaoFactory test = new DaoFactory();
    }
}
