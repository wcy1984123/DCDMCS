package initializer.initializers;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:41
 * System Time: 8:41 PM
 */
public class InitializerFactory {
    private static InitializerFactory ourInstance = new InitializerFactory();
    

    /**
     *
     * @return instance of the class
     */
    public static InitializerFactory getInstance() {
        return ourInstance;
    }

    /**
     * Class constructor
     */
    private InitializerFactory() {
    }

    /**
     * Generate data source
     * @param dt data type
     * @return data interface
     */
    public IInitializer createInitializer(INITIALIZERTYPE dt) {
        IInitializer iInitializer = null;
        switch (dt) {
            case DTW:
                iInitializer = new DTWInitializer();
                break;
            case CDTW:
                iInitializer = new CDTWInitializer();
                break;
            case DDTW:
                iInitializer = new DDTWInitializer();
                break;
            default:
                System.out.println("No Matching Instance To Created!");
        }

        return iInitializer;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        InitializerFactory test = new InitializerFactory();
    }
}
