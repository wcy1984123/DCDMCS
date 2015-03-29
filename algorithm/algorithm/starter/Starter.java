package starter;

import Utilities.IOOperation;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.initializers.IInitializer;
import initializer.initializers.INITIALIZERTYPE;
import initializer.initializers.InitializerFactory;
import model.IModels;
import model.MODELSTYPE;
import model.MODELTYPE;
import model.ModelsFactory;
import stoppingcriteria.IStoppingCriteria;
import stoppingcriteria.STOPPINGCRITERIA;
import stoppingcriteria.StoppingCriteriaFactory;

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
 * Date: 22/Mar/2015
 * Time: 10:13
 * System Time: 10:13 AM
 */

/**
 * Main function of starting CDMC system
 */
public class Starter {

    private static final Logger LOGGER = Logger.getLogger(Starter.class.getName());

    private IDAO mIdao; // data
    private IStoppingCriteria mIsc; // stopping criteria
    private IInitializer mInitializer; // initialization
    private String[] mConfigs; // configuation
    private int mClusterNum; // maximum number of clusters
    private double mSimilarity; // minimum similarity of clustering results
    private IModels mIModels; // dynamic model

    /**
     * class constructor
     * @param configFilePath configuration file path
     */
    public Starter(String configFilePath) {
        init(configFilePath); // initialize member variables
    }

    /**
     * class constructor
     * @param configurationList configuration string list
     */
    public Starter(List<String> configurationList) {
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


        // convert string list into string array
        this.mConfigs = null;
        if (cache.size() > 0) {
            this.mConfigs = new String[cache.size()];
            this.mConfigs = cache.toArray(this.mConfigs);
        }

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

        //---------------------- Cluster Numbers ----------------------//
        this.mClusterNum = Integer.parseInt(this.mConfigs[0]);

        //------------------------- Similarity ------------------------//
        this.mSimilarity = Double.parseDouble(this.mConfigs[1]);

        //-------------------------- Dataset --------------------------//
        String[] strings = this.mConfigs[2].split(" ");
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(strings[0].toUpperCase()));

        //----------------------- Initialization ----------------------//
        this.mInitializer = InitializerFactory.getInstance().createInitializer(INITIALIZERTYPE.valueOf(this.mConfigs[3].toUpperCase()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(this.mConfigs[4].toUpperCase()));

        //----------------------- Dynamic Models ----------------------//
        String[] modelArgs = this.mConfigs[5].split(" ");
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(modelArgs[0].toUpperCase()));

    }

    /**
     * Initialize member variables according to config file
     * @param configurationList configuration string list
     */
    private void init(List<String> configurationList) {

        // save to member variable
        this.mConfigs = new String[configurationList.size()];
        this.mConfigs = configurationList.toArray(this.mConfigs);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //---------------------- Cluster Numbers ----------------------//
        this.mClusterNum = Integer.parseInt(this.mConfigs[0]);

        //------------------------- Similarity ------------------------//
        this.mSimilarity = Double.parseDouble(this.mConfigs[1]);

        //-------------------------- Dataset --------------------------//
        String[] strings = this.mConfigs[2].split(" ");
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(strings[0].toUpperCase()));

        //----------------------- Initialization ----------------------//
        this.mInitializer = InitializerFactory.getInstance().createInitializer(INITIALIZERTYPE.valueOf(this.mConfigs[3].toUpperCase()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(this.mConfigs[4].toUpperCase()));

        //----------------------- Dynamic Models ----------------------//
        String[] modelArgs = this.mConfigs[5].split(" ");
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(modelArgs[0].toUpperCase()));

    }

    /**
     * Collective Dynamic Modeling & Clustering algorithm
     */
    private void runCDMC() {

        //------------------- Initialization --------------------//
        LOGGER.info("Initialization Begins");
        String[] strings = this.mConfigs[2].split(" ");
        List<List<Double>> instances = this.mIdao.getDataSourceAsLists(strings[1], strings[2]);
        int[] previousClusterLabels = this.mInitializer.initializer(instances, this.mClusterNum);
        LOGGER.info("Initialization Ends");

        //--------------- CDMC Iterative Process ----------------//
        int instancesNum = instances.size();
        int[] initialClusterLabels  = new int[instancesNum];
        double similarity = mIsc.computeSimilarity(initialClusterLabels, previousClusterLabels);
        int[] currentClusterLabels = null;

        LOGGER.info("Cluster & Models Starts");
        while(similarity < this.mSimilarity) {

            System.out.println("Previous Similarity = " + similarity + " [ " + this.mSimilarity + " ].");
            LOGGER.info("Train Models Starts");
            // build dynamic model
            String[] modelArgs = this.mConfigs[5].split(" ");
            this.mIModels.trainDynamicModels(instances, this.mClusterNum, MODELTYPE.valueOf(modelArgs[1].toUpperCase()));
            LOGGER.info("Train Models Ends");

            LOGGER.info("Cluster Process Starts");
            // assign cluster labels
            currentClusterLabels = this.mIModels.assignClusterLabels(instances);
            LOGGER.info("Cluster Process Ends");

            LOGGER.info("Cluster Agreement Evaluation Starts");
            // compute similarity
            similarity = mIsc.computeSimilarity(previousClusterLabels, currentClusterLabels);
            LOGGER.info("Cluster Agreement Evaluation Ends");
            System.out.println("Current Similarity = " + similarity + " [ " + this.mSimilarity + " ].");
            // update cluster labels
            previousClusterLabels = currentClusterLabels;
        }
        LOGGER.info("Cluster & Models Ends");

        // save results
        IOOperation.writeFile(currentClusterLabels, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/FinalClusterLabels.txt");
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        String configPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/config/config.txt";
        Starter test = new Starter(configPath);
        test.runCDMC();

    }
}
