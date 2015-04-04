package starter;

import java.util.List;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: starter
 * Date: 03/Apr/2015
 * Time: 17:37
 * System Time: 5:37 PM
 */

/**
 * Config class of reading config file
 */
public final class Config {

    private static final Logger LOGGER = Logger.getLogger(Config.class.getName());

    /*-------------------- Variables ---------------------*/
    private static int CLUSTERNUM; // cluster number
    private static double SIMILARITY; // similarity threshold
    private static String DATASETTYPE; // dataset type
    private static String DATASETPATH; // dataset  path
    private static int STATENUM; // state number
    private static String DTWTYPE; // DTW type
    private static String STOPPINGCRITERIATYPE; // stopping criteria
    private static String MODELINGMODE; // modeling mode
    private static String DYNAMICMODELTYPE; // dynamic model type

    /*--------------------- File Paths --------------------*/
    private static String CONFIGPATH; // config file path
    private static String INITIALCLUSTERSFILEPATH; // initial cluster file path
    private static String FINALCLUSTERSFILEPATH; // final cluster file path
    private static String DISTANCEMATRIXFILEPATH; // distance matrix file path

    /**
     * Initialize parameters
     * @param args string parameters

     */
    public Config(List<String> args) {
        if (args == null) {
            LOGGER.info("The args are null!");
            return;
        }

        if (args.size() == 0) {
            LOGGER.info("The args are empty!");
            return;
        }

        CLUSTERNUM = Integer.parseInt(args.get(0));

        SIMILARITY = Double.parseDouble(args.get(1));

        String[] strs = args.get(2).split(" ");
        DATASETTYPE = strs[0];
        DATASETPATH = strs[1];
        STATENUM = Integer.parseInt(strs[2]);
        DTWTYPE = args.get(3);
        STOPPINGCRITERIATYPE = args.get(4);
        strs = args.get(5).split(" ");
        MODELINGMODE = strs[0];
        DYNAMICMODELTYPE = strs[1];

        // file path
        INITIALCLUSTERSFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/InitialClusteringAssignment.txt";
        FINALCLUSTERSFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/FinalClusterLabels.txt";
    }

    /**
     * Getter
     * @return distance matrix path
     */
    public static String getDISTANCEMATRIXFILEPATH() {
        return DISTANCEMATRIXFILEPATH;
    }

    /**
     * Setter
     * @param DISTANCEMATRIXFILEPATH distance matrix path
     */
    public static void setDISTANCEMATRIXFILEPATH(String DISTANCEMATRIXFILEPATH) {
        Config.DISTANCEMATRIXFILEPATH = DISTANCEMATRIXFILEPATH;
    }

    /**
     * Getter
     * @return config file path
     */
    public static String getCONFIGPATH() {
        return CONFIGPATH;
    }

    /**
     * Setter
     * @param CONFIGPATH config file path
     */
    public static void setCONFIGPATH(String CONFIGPATH) {
        Config.CONFIGPATH = CONFIGPATH;
    }

    /**
     * Getter
     * @return final clusters file path
     */
    public static String getFINALCLUSTERSFILEPATH() {
        return FINALCLUSTERSFILEPATH;
    }

    /**
     * Getter
     * @return initial clusters file path
     */
    public static String getINITIALCLUSTERSFILEPATH() {
        return INITIALCLUSTERSFILEPATH;
    }

    /**
     * Getter
     * @return cluster number
     */
    public static int getCLUSTERNUM() {
        return CLUSTERNUM;
    }

    /**
     * Getter
     * @return similiarity threshold
     */
    public static double getSIMILARITY() {
        return SIMILARITY;
    }

    /**
     * Getter
     * @return dataset type
     */
    public static String getDATASETTYPE() {
        return DATASETTYPE.toUpperCase();
    }

    /**
     * Getter
     * @return dataset path
     */
    public static String getDATASETPATH() {
        return DATASETPATH;
    }

    /**
     * Getter
     * @return state number
     */
    public static int getSTATENUM() {
        return STATENUM;
    }

    /**
     * Getter
     * @return DTW type
     */
    public static String getDTWTYPE() {
        return DTWTYPE.toUpperCase();
    }

    /**
     * Getter
     * @return stopping criteria
     */
    public static String getSTOPPINGCRITERIATYPE() {
        return STOPPINGCRITERIATYPE.toUpperCase();
    }

    /**
     * Getter
     * @return modeling model
     */
    public static String getMODELINGMODE() {
        return MODELINGMODE.toUpperCase();
    }

    /**
     * Getter
     * @return dynamic model type
     */
    public static String getDYNAMICMODELTYPE() {
        return DYNAMICMODELTYPE.toUpperCase();
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
