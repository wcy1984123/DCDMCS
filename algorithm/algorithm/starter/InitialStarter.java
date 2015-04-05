package starter;

import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.initializers.IInitializer;
import initializer.initializers.INITIALIZERTYPE;
import initializer.initializers.InitializerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: starter
 * Date: 31/Mar/2015
 * Time: 02:00
 * System Time: 2:00 AM
 */

/**
 * Main function of starting CDMC system
 */
public class InitialStarter {

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getName());

    private IDAO mIdao; // data
    private IInitializer mInitializer; // initialization
    private Config mConfigs; // configuation

    /**
     * class constructor
     * @param configFilePath configuration file path
     */
    public InitialStarter(String configFilePath) {
        init(configFilePath); // initialize member variables
    }

    /**
     * class constructor
     * @param configurationList configuration string list
     */
    public InitialStarter(List<String> configurationList) {
        init(configurationList); // initialize member variables
    }

    /**
     * Read configuration file into memory
     * @param path file path
     * @return an array of string
     */
    private void readConfigFile(String path) {
        List<String> cache = new ArrayList<String>(); // cache configuration parameters
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;

            while((line = br.readLine()) != null) {
                cache.add(line);
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }


        // set up configurations
        this.mConfigs = new Config(cache);
        this.mConfigs.setCONFIGPATH(path);
    }

    /**
     * Initialize member variables according to config file
     * @param configFilePath configuration file path
     */
    private void init(String configFilePath) {

        // read config file
        readConfigFile(configFilePath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //-------------------------- Dataset --------------------------//
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(Config.getDATASETTYPE()));

        //----------------------- Initialization ----------------------//
        this.mInitializer = InitializerFactory.getInstance().createInitializer(INITIALIZERTYPE.valueOf(Config.getDTWTYPE()));

    }

    /**
     * Initialize member variables according to config file
     * @param configurationList configuration string list
     */
    private void init(List<String> configurationList) {

        // save to member variable
        this.mConfigs = new Config(configurationList);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //-------------------------- Dataset --------------------------//
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(Config.getDATASETTYPE()));

        //----------------------- Initialization ----------------------//
        this.mInitializer = InitializerFactory.getInstance().createInitializer(INITIALIZERTYPE.valueOf(Config.getDTWTYPE()));

    }

    /**
     * Collective Dynamic Modeling & Clustering algorithm
     */
    public void runInitialization() {

        //------------------- Initialization --------------------//
        LOGGER.info("Initialization Begins");
        System.out.println("\n||----------- Initialization Begins -----------||\n");
        List<List<Double>> instances = this.mIdao.getDataSourceAsLists(Config.getDATASETPATH(), String.valueOf(Config.getSTATENUM()));
        this.mInitializer.initializer(instances, Config.getCLUSTERNUM()); // compute distance

    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        String configPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/config/config.txt";
        InitialStarter test = new InitialStarter(configPath);
        test.runInitialization();

    }
}