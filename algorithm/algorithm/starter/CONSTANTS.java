package starter;

import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: starter
 * Date: 10/Apr/2015
 * Time: 17:10
 * System Time: 5:10 PM
 */
public final class CONSTANTS {

    private static final Logger LOGGER = Logger.getLogger(CONSTANTS.class.getName());

    /*-------------------- Variables ---------------------*/
    public final static int CLUSTERNUM; // cluster number
    public final static double SIMILARITY; // similarity threshold
    public final static String DATASETTYPE; // dataset type
    public final static String DATASETPATH; // dataset  path
    public final static int STATENUM; // state number
    public final static String DTWTYPE; // DTW type
    public final static String DEVIATEDDTWTYPE; // Deviated DTW type
    public final static String STOPPINGCRITERIATYPE; // stopping criteria
    public final static String MODELINGMODE; // modeling mode
    public final static String DYNAMICMODELTYPE; // dynamic model type
    public final static String INITIALCLUSTERINGTYPE; // initial clustering type
    public final static String HIERARCHICALLINKAGETYPE; // hierarchical linkage type
    public final static int DATAFORMAT; // source data format

    /*--------------------- File Paths --------------------*/
    public final static String CONFIGPATH; // config file path
    public final static String INITIALCLUSTERSFILEPATH; // initial cluster file path
    public final static String FINALCLUSTERSFILEPATH; // final cluster file path
    public final static String DISTANCEMATRIXFILEPATH; // distance matrix file path
    public final static String HYPNOGRAMDATASETFILEPATH; // hypnogram dataset file path
    public final static String WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH; // web user navigation behavior dataset file path

    /*-------------------- GUI Variables ------------------*/
    public final static Boolean PROBABILITYDENSITYVIEW;
    public final static Boolean CUMULATIVEDISTRIBUTIONVIEW;

    static {
        // File Path Default Settings
        CONFIGPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/config/config.txt";
        INITIALCLUSTERSFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/InitialClusteringAssignment.txt";
        FINALCLUSTERSFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/FinalClusterLabels.txt";
        DISTANCEMATRIXFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/DistanceMatrix.txt";
        HYPNOGRAMDATASETFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/dataset/hypnogram.csv";
        WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH = "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/dataset/msnbcData.csv";


        // DCDMC Parameter Default Configuration
        CLUSTERNUM = 3;
        SIMILARITY = 0.99;
        DATASETTYPE = "HYPNOGRAM";
        DATASETPATH = HYPNOGRAMDATASETFILEPATH;
        STATENUM = 3;
        DTWTYPE = "MATLABORIGINALDTW";
        DEVIATEDDTWTYPE = "GLOBALWEIGHTEDDTW";
        STOPPINGCRITERIATYPE= "RANDINDEX";
        MODELINGMODE = "STATEBASEDDYNAMICMODELS";
        DYNAMICMODELTYPE = "MARKOVCHAINMODEL";
        INITIALCLUSTERINGTYPE = "HIERARCHICALCLUSTERING";
        HIERARCHICALLINKAGETYPE = "AVERAGELINKAGESTRATEGY";
        DATAFORMAT = 3;

        // GUI variables
        PROBABILITYDENSITYVIEW = true;
        CUMULATIVEDISTRIBUTIONVIEW = false;
    }
}
