package starter;

import java.awt.*;
import java.io.File;
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
    public final static String SIMILARITYTRENDLINEFILEPATH; // similarity trendline file path
    public final static String FINALPROBSFORALLINSTANCESFILEPATH; // final probabilities for all instances file path
    public final static String TOTALPROBABILITIESTRENDLINEFILEPATH; // total probabilities trendline file path
    public final static String SAVECONSOLETODISKFILEPATH; // save console to disk file path

    /*-------------------- GUI Variables ------------------*/
    public final static Boolean PROBABILITYDENSITYVIEW; // probability density view
    public final static Boolean CUMULATIVEDISTRIBUTIONVIEW; // cumulative distribution view

    /*---------------------- Separator --------------------*/
    public final static String CONFIGSEPARATOR; // separator between configuration parameters

    /*--------------------- Color Array -------------------*/
    public final static Color[] COLORCOLLECTION; // colors for plot

    static {
        // File Path Default Settings
        CONFIGPATH = new File("config" + File.separator + "config.txt").getAbsolutePath();
        INITIALCLUSTERSFILEPATH = new File("results" + File.separator + "InitialClusteringAssignment.txt").getAbsolutePath();
        FINALCLUSTERSFILEPATH = new File("results" + File.separator + "FinalClusterLabels.txt").getAbsolutePath();
        DISTANCEMATRIXFILEPATH = new File("results" + File.separator + "DistanceMatrix.txt").getAbsolutePath();
        HYPNOGRAMDATASETFILEPATH = new File("dataset" + File.separator + "hypnogram.csv").getAbsolutePath();
        WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH = new File("dataset" + File.separator + "msnbcData.csv").getAbsolutePath();
        SIMILARITYTRENDLINEFILEPATH = new File("results" + File.separator + "SimilarityTrendline.txt").getAbsolutePath();
        FINALPROBSFORALLINSTANCESFILEPATH = new File("results" + File.separator + "FinalProbsForAllInstances.txt").getAbsolutePath();
        TOTALPROBABILITIESTRENDLINEFILEPATH = new File("results" + File.separator + "TotalProbsTrendline.txt").getAbsolutePath();
        SAVECONSOLETODISKFILEPATH = new File("results" + File.separator + "consoleData.txt").getAbsolutePath();

        // DCDMC Parameter Default Configuration
        CLUSTERNUM = 3;
        SIMILARITY = 0.99;
        DATASETTYPE = "HYPNOGRAM";
        DATASETPATH = HYPNOGRAMDATASETFILEPATH;
        STATENUM = 3;
        DTWTYPE = "ORIGINALDTW";
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

        // Separator
        CONFIGSEPARATOR = "@@@";

        // Color Array
        COLORCOLLECTION = new Color[]{Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.YELLOW};
    }
}
