package starter;

import Utilities.IOOperation;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.clusterings.InitialClusteringFactory;
import initializer.clusterings.HierarchicalClusterAdapter;
import initializer.clusterings.IClusteringAlgorithm;
import initializer.clusterings.INITIALCLUSTERINGTYPE;
import model.*;
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

    private Config mConfigs; // configuation
    private IDAO mIdao; // data
    private IStoppingCriteria mIsc; // stopping criteria
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

        // set up configurations
        this.mConfigs = new Config(cache);
        this.mConfigs.setCONFIGPATH(path);

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
     * Initialize member variables according to config file (For Read Config File Call)
     * @param configFilePath configuration file path
     * @param distanceMatrixFilePath distance matrix
     */
    private void init(String configFilePath, String distanceMatrixFilePath) {

        //-------------- Read Configurations From File ---------------//
        // read config file
        readConfigFile(configFilePath);

        // read distance matrix
        readDistanceMatrix(distanceMatrixFilePath);
        Config.setDISTANCEMATRIXFILEPATH(distanceMatrixFilePath);

        // read initial cluster lable file
        // readInitialClusterLabels(initialClusterFilePath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //----------------------- Initialization ----------------------//
        IClusteringAlgorithm ica = InitialClusteringFactory.getInstance().createInitialClusters(INITIALCLUSTERINGTYPE.valueOf(Config.getINITIALCLUSTERINGTYPE()));
        this.initialClusterLalels = ica.getClusterAssignment(Config.getCLUSTERNUM(), this.distanceMatrix);

        //-------------------------- Dataset --------------------------//
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(Config.getDATASETTYPE()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(Config.getSTOPPINGCRITERIATYPE()));

        //----------------------- Dynamic Models ----------------------//
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(Config.getMODELINGMODE()));

        //----------------------- Dynamic Models ----------------------//
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(Config.getMODELINGMODE()));
    }

    /**
     * Initialize member variables according to config file (For GUI Call)
     * @param configurationList configuration string list
     * @param distanceMatrixFilePath distance matrix file path
     */
    private void init(List<String> configurationList, String distanceMatrixFilePath) {

        //--------------- Read Configuration From File ---------------//
        this.mConfigs = new Config(configurationList);

        // read distance matrix
        readDistanceMatrix(distanceMatrixFilePath);
        Config.setDISTANCEMATRIXFILEPATH(distanceMatrixFilePath);

        // read the initial cluster labels
        // readInitialClusterLabels(initialClusterFilePath);

        if (this.mConfigs == null) {
            LOGGER.log(Level.INFO, "Config file is null!");
            return;
        }

        //----------------------- Initialization ----------------------//
        IClusteringAlgorithm ica = InitialClusteringFactory.getInstance().createInitialClusters(INITIALCLUSTERINGTYPE.valueOf(Config.getINITIALCLUSTERINGTYPE()));
        this.initialClusterLalels = ica.getClusterAssignment(Config.getCLUSTERNUM(), this.distanceMatrix);

        //-------------------------- Dataset --------------------------//
        this.mIdao = DaoFactory.getInstance().createData(DATATYPE.valueOf(Config.getDATASETTYPE()));

        //--------------------- Stopping Criteria ---------------------//
        this.mIsc = StoppingCriteriaFactory.getInstance().createStoppingCriteria(STOPPINGCRITERIA.valueOf(Config.getSTOPPINGCRITERIATYPE()));

        //----------------------- Dynamic Models ----------------------//
        this.mIModels = ModelsFactory.getInstance().createModels(MODELSTYPE.valueOf(Config.getMODELINGMODE()));

    }

    /**
     * Collective Dynamic Modeling & Clustering algorithm
     */
    public void runCDMC() {

        LOGGER.info("Cluster & Models Starts");
        System.out.println("\n||************** Cluster & Models Starts ************||");

        //------------------- Initialization --------------------//
        List<List<Double>> instances = this.mIdao.getDataSourceAsLists(Config.getDATASETPATH(), String.valueOf(Config.getSTATENUM()));
        int[] previousClusterLabels = this.initialClusterLalels;

        //--------------- CDMC Iterative Process ----------------//
        int instancesNum = instances.size();
        int[] initialClusterLabels  = new int[instancesNum];
        double similarity = mIsc.computeSimilarity(initialClusterLabels, previousClusterLabels);
        int[] currentClusterLabels = null;
        int iterationCount = 1;

        while(similarity < Config.getSIMILARITY()) {

            System.out.println("\n   ============== " + iterationCount + " ==============");
            String preSimilarity = String.format("%.4f", similarity);
            System.out.println("        Previous Similarity = " + preSimilarity + " [ " + Config.getSIMILARITY() + " ].");
            LOGGER.info("Train Models Starts");
            System.out.println("        Train Models Starts.");


            // build dynamic model
            this.mIModels.trainDynamicModels(instances, Config.getCLUSTERNUM(), previousClusterLabels,  MODELTYPE.valueOf(Config.getDYNAMICMODELTYPE()));
            LOGGER.info("       Train Models Ends");
            System.out.println("        Train Models Ends.");

            LOGGER.info("Cluster Process Starts");
            System.out.println("        Cluster Process Starts.");


            // assign cluster labels
            currentClusterLabels = this.mIModels.assignClusterLabels(instances);
            LOGGER.info("Cluster Process Ends");
            System.out.println("        Cluster Process Ends.");


            LOGGER.info("Cluster Agreement Evaluation Starts");
            // compute similarity
            similarity = mIsc.computeSimilarity(previousClusterLabels, currentClusterLabels);
            LOGGER.info("Cluster Agreement Evaluation Ends");
            String curSimilarity = String.format("%.4f", similarity);
            System.out.println("        Current Similarity  = " + curSimilarity + " [ " + Config.getSIMILARITY() + " ].");


            // update cluster labels
            previousClusterLabels = currentClusterLabels;
            System.out.println("   ==============================\n");


            iterationCount++;
        }

        // save results
        IOOperation.writeFile(this.initialClusterLalels, Config.getINITIALCLUSTERSFILEPATH());
        IOOperation.writeFile(currentClusterLabels, Config.getFINALCLUSTERSFILEPATH());

        // visualize results
        System.out.println("\n   ======= Final Models Parameters ======= ");
        this.mIModels.visualizeOutputs();
        System.out.println("\n   ============================== \n");

        LOGGER.info("Cluster & Models Ends");
        System.out.println("||************** Cluster & Models Ends *************||\n");
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        String configPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/config/config.txt";
        String distanceMatrixPath = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/Results/DistanceMatrix.txt";
        Starter test = new Starter(configPath, distanceMatrixPath);

        test.runCDMC();

    }
}
