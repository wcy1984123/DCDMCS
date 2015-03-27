package starter;

import Utilities.Utilities;
import cluster.ICluster;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.initializers.IInitializer;
import initializer.initializers.INITIALIZERTYPE;
import initializer.initializers.InitializerFactory;
import model.IModel;
import model.MODELTYPE;
import model.ModelFactory;
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
    private IModel mIModel; // dynamic model

    /**
     * class constructor
     */
    public Starter() {
        init(); // initialize member variables
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
     */
    private void init() {

        // read config file
        String configPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMC/config/config.txt";
        readConfigFile(configPath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //------------------- Cluster Numbers -------------------//
        this.mClusterNum = Integer.parseInt(this.mConfigs[0]);

        //--------------------- Similarity ----------------------//
        this.mSimilarity = Double.parseDouble(this.mConfigs[1]);

        // create the objects through factory pattern
        String[] strings = this.mConfigs[2].split(" ");
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(strings[0].toUpperCase()));
        this.mInitializer = InitializerFactory.getInstance().createInitializer(INITIALIZERTYPE.valueOf(this.mConfigs[3].toUpperCase()));
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(this.mConfigs[4].toUpperCase()));
        this.mIModel = ModelFactory.getInstance().createModel(MODELTYPE.valueOf(this.mConfigs[5].toUpperCase()));

    }

    /**
     * Collective Dynamic Modeling & Clustering algorithm
     */
    private void runCDMC() {

        //------------------- Initialization --------------------//
        String[] strings = this.mConfigs[2].split(" ");
        double[][] instances = this.mIdao.getDataSource(strings[1], strings[2]);
        int[][] instancesArray = Utilities.convertToTwoDimensionIntegerArray(instances);
        List<List<Double>> instancesList = Utilities.convertToTwoDimensionalDoubleList(instances);
        int[] previousClusterLabels = this.mInitializer.initializer(instances, this.mClusterNum);

        //--------------- CDMC Iterative Process ----------------//
        int instancesNum = instances.length;
        int[] initialClusterLabels  = new int[instancesNum];
        double similarity = mIsc.computeSimilarity(initialClusterLabels, previousClusterLabels);

        while(similarity < this.mSimilarity) {

            // build dynamic model
            this.mIModel.trainModel(instancesList);

            // assign cluster labels
            int[] currentClusterLabels = this.mIModel.assignClusterLabels(instancesArray);

            // compute similarity
            similarity = mIsc.computeSimilarity(previousClusterLabels, currentClusterLabels);

            // update cluster labels
            previousClusterLabels = currentClusterLabels;
        }

    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        Starter test = new Starter();
        test.runCDMC();

    }
}
