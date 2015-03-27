package initializer.initializers;

import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:41
 * System Time: 8:41 PM
 */
public class InitializerFactory {

    private static final Logger LOGGER = Logger.getLogger(InitializerFactory.class.getName());
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
            case ORIGINALDTW:
                iInitializer = new OriginalDTWInitializer();
                break;
            case SAKOECHIBADTW:
                iInitializer = new SakoeChibaDTWInitializer();
                break;
            case ITAKURAPARALLELOGRAMDTW:
                iInitializer = new ItakuraParallelogramDTWInitializer();
                break;
            case FASTOPTIMALDTW:
                iInitializer = new FastOptimalDTWInitializer();
                break;
            case MATLABORIGINALDTW:
                iInitializer = new MatlabOriginalDTWInitializer();
                break;
            case DEVIATEDDTW:
                iInitializer = new DDTWInitializer();
                break;
            default:
                LOGGER.info("No Matching Instance To Created!");
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
