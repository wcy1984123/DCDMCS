package starter;

import Utilities.IOOperation;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.initializers.HierarchicalClusterAdapter;
import initializer.initializers.IClusteringAlgorithm;
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
    private String[] mConfigs; // configuation
    private int mClusterNum; // maximum number of clusters
    private double mSimilarity; // minimum similarity of clustering results
    private IModels mIModels; // dynamic model
    private int[] initialClusterLalels; // initial cluster labels
    private double[][] distanceMatrix; // distance matrix

    /**
     * class constructor
     * @param configFilePath configuration file path
     * @param distanceMatrixFilePath distance matrix file path
     */
    public Starter(String configFilePath, String distanceMatrixFilePath) {
        init(configFilePath, distanceMatrixFilePath); // initialize member variables
    }

    /**
     * class constructor
     * @param configurationList configuration string list
     * @param distanceMatrixFilePath distance matrix file path
     */
    public Starter(List<String> configurationList, String distanceMatrixFilePath) {
        init(configurationList, distanceMatrixFilePath); // initialize member variables
    }

    /**
     * Read initial cluster labels into memory
     * @param path file path
     * @return an array of integers
     */
    private void readInitialClusterLabels(String path) {
        List<Integer> cache = new ArrayList<Integer>(); // cache configuration parameters
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;

            while((line = br.readLine()) != null) {
                String[] strs = line.split(" ");
                for (int i = 0; i < strs.length; i++) {
                    cache.add(Integer.parseInt(strs[i]));
                }
            }
            br.close();

        } catch(IOException e) {
            e.printStackTrace();
        }


        // convert integer list into integer array
        this.initialClusterLalels = new int[cache.size()];
        for (int i = 0; i < cache.size(); i++) this.initialClusterLalels[i] = cache.get(i);

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
     * Read the input two-dimensional distance matrix from the file
     * @param path file path
     */
    public void readDistanceMatrix(String path) {

        if (path == null || path.length() == 0) {
            LOGGER.info("The input path is null!");
            return;
        }

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            String[] strs = line.split(" ");
            int ROW = strs.length;
            this.distanceMatrix = new double[ROW][ROW];

            for (int i = 0; i < ROW; i++) {
                this.distanceMatrix[0][i] = Double.parseDouble(strs[i]);
            }

            int count = 1;
            while((line = br.readLine()) != null) {
                String [] parts = line.split(" ");
                for (int i = 0; i < ROW; i++) {
                    this.distanceMatrix[count][i] = Double.parseDouble(parts[i]);
                }
                count++;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize member variables according to config file
     * @param configFilePath configuration file path
     * @param distanceMatrixFilePath distance matrix
     */
    private void init(String configFilePath, String distanceMatrixFilePath) {

        //-------------- Read Configurations From File ---------------//
        // read config file
        readConfigFile(configFilePath);

        // read distance matrix
        readDistanceMatrix(distanceMatrixFilePath);

        // read initial cluster lable file
        // readInitialClusterLabels(initialClusterFilePath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //---------------------- Cluster Numbers ----------------------//
        this.mClusterNum = Integer.parseInt(this.mConfigs[0]);

        //----------------------- Initialization ----------------------//
        IClusteringAlgorithm ica = new HierarchicalClusterAdapter();
        this.initialClusterLalels = ica.getClusterAssignment(this.mClusterNum, this.distanceMatrix);

        //------------------------- Similarity ------------------------//
        this.mSimilarity = Double.parseDouble(this.mConfigs[1]);

        //-------------------------- Dataset --------------------------//
        String[] strings = this.mConfigs[2].split(" ");
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(strings[0].toUpperCase()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(this.mConfigs[4].toUpperCase()));

        //----------------------- Dynamic Models ----------------------//
        String[] modelArgs = this.mConfigs[5].split(" ");
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(modelArgs[0].toUpperCase()));

    }

    /**
     * Initialize member variables according to config file
     * @param configurationList configuration string list
     * @param distanceMatrixFilePath distance matrix file path
     */
    private void init(List<String> configurationList, String distanceMatrixFilePath) {

        // save to member variable
        this.mConfigs = new String[configurationList.size()];
        this.mConfigs = configurationList.toArray(this.mConfigs);

        //--------------- Read Configuration From File ---------------//
        // read distance matrix
        readDistanceMatrix(distanceMatrixFilePath);

        // read the initial cluster labels
        // readInitialClusterLabels(initialClusterFilePath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //---------------------- Cluster Numbers ----------------------//
        this.mClusterNum = Integer.parseInt(this.mConfigs[0]);

        //----------------------- Initialization ----------------------//
        IClusteringAlgorithm ica = new HierarchicalClusterAdapter();
        this.initialClusterLalels = ica.getClusterAssignment(this.mClusterNum, this.distanceMatrix);

        //------------------------- Similarity ------------------------//
        this.mSimilarity = Double.parseDouble(this.mConfigs[1]);

        //-------------------------- Dataset --------------------------//
        String[] strings = this.mConfigs[2].split(" ");
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(strings[0].toUpperCase()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(this.mConfigs[4].toUpperCase()));

        //----------------------- Dynamic Models ----------------------//
        String[] modelArgs = this.mConfigs[5].split(" ");
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(modelArgs[0].toUpperCase()));

    }

    /**
     * Collective Dynamic Modeling & Clustering algorithm
     */
    public void runCDMC() {

        //------------------- Initialization --------------------//
        LOGGER.info("Initialization Begins");
        System.out.println("Initialization Begins");

        String[] strings = this.mConfigs[2].split(" ");
        List<List<Double>> instances = this.mIdao.getDataSourceAsLists(strings[1], strings[2]);
        int[] previousClusterLabels = this.initialClusterLalels;
        LOGGER.info("Initialization Ends");
        System.out.println("Initialization Ends");

        //--------------- CDMC Iterative Process ----------------//
        int instancesNum = instances.size();
        int[] initialClusterLabels  = new int[instancesNum];
        double similarity = mIsc.computeSimilarity(initialClusterLabels, previousClusterLabels);
        int[] currentClusterLabels = null;

        LOGGER.info("Cluster & Models Starts");
        System.out.println("Cluster & Models Starts");
        while(similarity < this.mSimilarity) {

            System.out.println("Previous Similarity = " + similarity + " [ " + this.mSimilarity + " ].");
            LOGGER.info("Train Models Starts");
            System.out.println("Train Models Starts");
            // build dynamic model
            String[] modelArgs = this.mConfigs[5].split(" ");
            this.mIModels.trainDynamicModels(instances, this.mClusterNum, MODELTYPE.valueOf(modelArgs[1].toUpperCase()));
            LOGGER.info("Train Models Ends");
            System.out.println("Train Models Ends");

            LOGGER.info("Cluster Process Starts");
            System.out.println("Cluster Process Starts");
            // assign cluster labels
            currentClusterLabels = this.mIModels.assignClusterLabels(instances);
            LOGGER.info("Cluster Process Ends");
            System.out.println("Cluster Process Ends");

            LOGGER.info("Cluster Agreement Evaluation Starts");
            System.out.println("Cluster Agreement Evaluation Starts");
            // compute similarity
            similarity = mIsc.computeSimilarity(previousClusterLabels, currentClusterLabels);
            LOGGER.info("Cluster Agreement Evaluation Ends");
            System.out.println("Cluster Agreement Evaluation Ends");
            System.out.println("Current Similarity = " + similarity + " [ " + this.mSimilarity + " ].");
            // update cluster labels
            previousClusterLabels = currentClusterLabels;
        }
        LOGGER.info("Cluster & Models Ends");
        System.out.println("Cluster & Models Ends");

        // save results
        IOOperation.writeFile(this.initialClusterLalels, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/InitialClusteringAssignment.txt");
        IOOperation.writeFile(currentClusterLabels, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/FinalClusterLabels.txt");
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        String configPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/config/config.txt";
        String initialClusterPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/FinalClusterLabels.txt";
        Starter test = new Starter(configPath, initialClusterPath);
        test.runCDMC();

    }
}
