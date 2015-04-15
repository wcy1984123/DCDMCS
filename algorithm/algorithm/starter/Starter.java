package starter;

import Utilities.IOOperation;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import initializer.clusterings.InitialClusteringFactory;
import initializer.clusterings.IClusteringAlgorithm;
import initializer.clusterings.INITIALCLUSTERINGTYPE;
import model.*;
import stoppingcriteria.IStoppingCriteria;
import stoppingcriteria.STOPPINGCRITERIA;
import stoppingcriteria.StoppingCriteriaFactory;

import javax.swing.*;
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

        // start a new background thread to run CDMC algorithm
        SwingWorker task = new SwingWorker<Void, String>() {

            private void printInBackground(String info) {
                System.out.println(info);
//                publish(info);
            }

            @Override
            protected Void doInBackground() throws Exception {

                LOGGER.info("Cluster & Models Starts");
                printInBackground("\n||************** Cluster & Models Starts ************||");

                //------------------- Initialization --------------------//
                List<List<Double>> instances = Starter.this.mIdao.getDataSourceAsLists(Config.getDATASETPATH(), String.valueOf(Config.getDATAFORMAT()));
                int[] previousClusterLabels = Starter.this.initialClusterLalels;

                //--------------- CDMC Iterative Process ----------------//
                int instancesNum = instances.size();
                int[] initialClusterLabels  = new int[instancesNum];
                double similarity = mIsc.computeSimilarity(initialClusterLabels, previousClusterLabels);
                int[] currentClusterLabels = null;
                int iterationCount = 1;

                while(similarity < Config.getSIMILARITY()) {

                    printInBackground("\n   ============== " + iterationCount + " ==============");
                    String preSimilarity = String.format("%.4f", similarity);

                    printInBackground("        Previous Similarity = " + preSimilarity + " [ " + Config.getSIMILARITY() + " ].");
                    LOGGER.info("Train Models Starts");
                    printInBackground("        Train Models Starts.");


                    // build dynamic model
                    Starter.this.mIModels.trainDynamicModels(instances, Config.getCLUSTERNUM(), previousClusterLabels, MODELTYPE.valueOf(Config.getDYNAMICMODELTYPE()));
                    LOGGER.info("       Train Models Ends");
                    printInBackground("        Train Models Ends.");

                    LOGGER.info("Cluster Process Starts");
                    printInBackground("        Cluster Process Starts.");

                    // assign cluster labels
                    currentClusterLabels = Starter.this.mIModels.assignClusterLabels(instances);
                    LOGGER.info("Cluster Process Ends");
                    printInBackground("        Cluster Process Ends.");


                    LOGGER.info("Cluster Agreement Evaluation Starts");
                    // compute similarity
                    similarity = mIsc.computeSimilarity(previousClusterLabels, currentClusterLabels);
                    LOGGER.info("Cluster Agreement Evaluation Ends");
                    String curSimilarity = String.format("%.4f", similarity);
                    printInBackground("        Current Similarity  = " + curSimilarity + " [ " + Config.getSIMILARITY() + " ].");

                    // update cluster labels
                    previousClusterLabels = currentClusterLabels;
                    printInBackground("   ==============================\n");

                    iterationCount++;
                }

                // save results
                IOOperation.writeFile(Starter.this.initialClusterLalels, Config.getINITIALCLUSTERSFILEPATH());
                IOOperation.writeFile(currentClusterLabels, Config.getFINALCLUSTERSFILEPATH());

                // Just in case if initial clusters have provided a good enough clustering, it never goes into the above CDMC loop
                if(currentClusterLabels == null) {
                    // build dynamic model over initial clusters
                    LOGGER.warning("        Never Enter Into CDMC Algorithm!");
                    Starter.this.mIModels.trainDynamicModels(instances, Config.getCLUSTERNUM(), previousClusterLabels,  MODELTYPE.valueOf(Config.getDYNAMICMODELTYPE()));
                }

                // visualize results
                Starter.this.mIModels.visualizeOutputs();
                LOGGER.info("Cluster & Models Ends");

                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                String info = chunks.get(chunks.size() - 1);
                System.out.println(info);
            }
        };

        task.execute();

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
