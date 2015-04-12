package starter;

import stoppingcriteria.STOPPINGCRITERIA;

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
    private static String DEVIATEDDTWTYPE; // Deviated DTW type
    private static String STOPPINGCRITERIATYPE; // stopping criteria
    private static String MODELINGMODE; // modeling mode
    private static String DYNAMICMODELTYPE; // dynamic model type
    private static String INITIALCLUSTERINGTYPE; // initial clustering type
    private static String HIERARCHICALLINKAGETYPE; // hierarchical linkage type
    private static int DATAFORMAT; // source data format

    /*--------------------- File Paths --------------------*/
    private static String CONFIGPATH; // config file path
    private static String INITIALCLUSTERSFILEPATH; // initial cluster file path
    private static String FINALCLUSTERSFILEPATH; // final cluster file path
    private static String DISTANCEMATRIXFILEPATH; // distance matrix file path
    private static String HYPNOGRAMDATASETFILEPATH; // hypnogram dataset file path
    private static String WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH; // web user navigation behavior dataset file path


    /*--------------------- Constant ----------------------*/


    static {

        // File Path Default Settings
        CONFIGPATH = CONSTANTS.CONFIGPATH;
        INITIALCLUSTERSFILEPATH = CONSTANTS.INITIALCLUSTERSFILEPATH;
        FINALCLUSTERSFILEPATH = CONSTANTS.FINALCLUSTERSFILEPATH;
        DISTANCEMATRIXFILEPATH = CONSTANTS.DISTANCEMATRIXFILEPATH;
        HYPNOGRAMDATASETFILEPATH = CONSTANTS.HYPNOGRAMDATASETFILEPATH;
        WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH = CONSTANTS.WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH;


        // DCDMC Parameter Default Configuration
        CLUSTERNUM = CONSTANTS.CLUSTERNUM;
        SIMILARITY = CONSTANTS.SIMILARITY;
        DATASETTYPE = CONSTANTS.DATASETTYPE;
        DATASETPATH = CONSTANTS.DATASETPATH;
        STATENUM = CONSTANTS.STATENUM;
        DTWTYPE = CONSTANTS.DTWTYPE;
        DEVIATEDDTWTYPE = CONSTANTS.DEVIATEDDTWTYPE;
        STOPPINGCRITERIATYPE= CONSTANTS.STOPPINGCRITERIATYPE;
        MODELINGMODE = CONSTANTS.MODELINGMODE;
        DYNAMICMODELTYPE = CONSTANTS.DYNAMICMODELTYPE;
        INITIALCLUSTERINGTYPE = CONSTANTS.INITIALCLUSTERINGTYPE;
        HIERARCHICALLINKAGETYPE = CONSTANTS.HIERARCHICALLINKAGETYPE;
        DATAFORMAT = CONSTANTS.DATAFORMAT;
    }

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
        DATAFORMAT = Integer.parseInt(strs[3]);
        DTWTYPE = args.get(3);
        STOPPINGCRITERIATYPE = args.get(4);
        strs = args.get(5).split(" ");
        MODELINGMODE = strs[0];
        DYNAMICMODELTYPE = strs[1];
        String[] params = args.get(6).split(" ");
        INITIALCLUSTERINGTYPE = params[0];
        HIERARCHICALLINKAGETYPE = params[1];
    }

    /**
     * Reset all configurations to default configuration
     */
    public static void resetConfig() {

        // File Path Default Settings
        CONFIGPATH = CONSTANTS.CONFIGPATH;
        INITIALCLUSTERSFILEPATH = CONSTANTS.INITIALCLUSTERSFILEPATH;
        FINALCLUSTERSFILEPATH = CONSTANTS.FINALCLUSTERSFILEPATH;
        DISTANCEMATRIXFILEPATH = CONSTANTS.DISTANCEMATRIXFILEPATH;
        HYPNOGRAMDATASETFILEPATH = CONSTANTS.HYPNOGRAMDATASETFILEPATH;
        WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH = CONSTANTS.WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH;

        // DCDMC Parameter Default Configuration
        CLUSTERNUM = CONSTANTS.CLUSTERNUM;
        SIMILARITY = CONSTANTS.SIMILARITY;
        DATASETTYPE = CONSTANTS.DATASETTYPE;
        DATASETPATH = CONSTANTS.DATASETPATH;
        STATENUM = CONSTANTS.STATENUM;
        DTWTYPE = CONSTANTS.DTWTYPE;
        DEVIATEDDTWTYPE = CONSTANTS.DEVIATEDDTWTYPE;
        STOPPINGCRITERIATYPE= CONSTANTS.STOPPINGCRITERIATYPE;
        MODELINGMODE = CONSTANTS.MODELINGMODE;
        DYNAMICMODELTYPE = CONSTANTS.DYNAMICMODELTYPE;
        INITIALCLUSTERINGTYPE = CONSTANTS.INITIALCLUSTERINGTYPE;
        HIERARCHICALLINKAGETYPE = CONSTANTS.HIERARCHICALLINKAGETYPE;
        DATAFORMAT = CONSTANTS.DATAFORMAT;
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
     * Getter
     * @return initial clustering type
     */
    public static String getINITIALCLUSTERINGTYPE() {
        return INITIALCLUSTERINGTYPE.toUpperCase();
    }

    /**
     * Getter
     * @return hierarchical linkage type
     */
    public static String getHIERARCHICALLINKAGETYPE() {
        return HIERARCHICALLINKAGETYPE.toUpperCase();
    }

    /**
     * Getter
     * @return data format
     */
    public static int getDATAFORMAT() {
        return DATAFORMAT;
    }

    /**
     * Getter
     * @return deviated dtw type
     */
    public static String getDEVIATEDDTWTYPE() {
        return DEVIATEDDTWTYPE.toUpperCase();
    }

    /**
     * Getter
     * @return version information
     */
    public static String getVERSIONINFO() {
        return VERSIONINFO;
    }

    /**
     * Getter
     * @return hypnogram dataset file path
     */
    public static String getHYPNOGRAMDATASETFILEPATH() {
        return HYPNOGRAMDATASETFILEPATH;
    }

    /**
     * Geter
     * @return web user navigation behavior dataset file path
     */
    public static String getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH() {
        return WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH;
    }

    /**
     * Seter
     * @param CLUSTERNUM cluster number
     */
    public static void setCLUSTERNUM(int CLUSTERNUM) {
        Config.CLUSTERNUM = CLUSTERNUM;
    }

    /**
     * Setter
     * @param SIMILARITY similarity
     */
    public static void setSIMILARITY(double SIMILARITY) {
        Config.SIMILARITY = SIMILARITY;
    }

    /**
     * Setter
     * @param DATASETTYPE dataset type
     */
    public static void setDATASETTYPE(String DATASETTYPE) {
        Config.DATASETTYPE = DATASETTYPE.toUpperCase();
    }

    /**
     * Setter
     * @param DATASETPATH dataset path
     */
    public static void setDATASETPATH(String DATASETPATH) {
        Config.DATASETPATH = DATASETPATH.toUpperCase();
    }

    /**
     * Setter
     * @param STATENUM state number
     */
    public static void setSTATENUM(int STATENUM) {
        Config.STATENUM = STATENUM;
    }

    /**
     * Setter
     * @param DTWTYPE dtw type
     */
    public static void setDTWTYPE(String DTWTYPE) {
        Config.DTWTYPE = DTWTYPE.toUpperCase();
    }

    /**
     * Setter
     * @param DEVIATEDDTWTYPE deviated dtw type
     */
    public static void setDEVIATEDDTWTYPE(String DEVIATEDDTWTYPE) {
        Config.DEVIATEDDTWTYPE = DEVIATEDDTWTYPE.toUpperCase();
    }

    /**
     * Setter
     * @param STOPPINGCRITERIATYPE stopping criteria
     */
    public static void setSTOPPINGCRITERIATYPE(String STOPPINGCRITERIATYPE) {
        Config.STOPPINGCRITERIATYPE = STOPPINGCRITERIATYPE.toUpperCase();
    }

    /**
     * Setter
     * @param MODELINGMODE modeling mode
     */
    public static void setMODELINGMODE(String MODELINGMODE) {
        Config.MODELINGMODE = MODELINGMODE.toUpperCase();
    }

    /**
     * Setter
     * @param DYNAMICMODELTYPE dynamic model type
     */
    public static void setDYNAMICMODELTYPE(String DYNAMICMODELTYPE) {
        Config.DYNAMICMODELTYPE = DYNAMICMODELTYPE.toUpperCase();
    }

    /**
     * Setter
     * @param INITIALCLUSTERINGTYPE initial clustering type
     */
    public static void setINITIALCLUSTERINGTYPE(String INITIALCLUSTERINGTYPE) {
        Config.INITIALCLUSTERINGTYPE = INITIALCLUSTERINGTYPE.toUpperCase();
    }

    /**
     * Setter
     * @param HIERARCHICALLINKAGETYPE hierarchical linkage type
     */
    public static void setHIERARCHICALLINKAGETYPE(String HIERARCHICALLINKAGETYPE) {
        Config.HIERARCHICALLINKAGETYPE = HIERARCHICALLINKAGETYPE.toUpperCase();
    }

    /**
     * Setter
     * @param DATAFORMAT dataset format
     */
    public static void setDATAFORMAT(int DATAFORMAT) {
        Config.DATAFORMAT = DATAFORMAT;
    }

    /**
     * Seter
     * @param INITIALCLUSTERSFILEPATH initial clusters file path
     */
    public static void setINITIALCLUSTERSFILEPATH(String INITIALCLUSTERSFILEPATH) {
        Config.INITIALCLUSTERSFILEPATH = INITIALCLUSTERSFILEPATH;
    }

    /**
     * Setter
     * @param FINALCLUSTERSFILEPATH final clusters file path
     */
    public static void setFINALCLUSTERSFILEPATH(String FINALCLUSTERSFILEPATH) {
        Config.FINALCLUSTERSFILEPATH = FINALCLUSTERSFILEPATH;
    }

    /**
     * Setter
     * @param HYPNOGRAMDATASETFILEPATH hypnogram dataset file path
     */
    public static void setHYPNOGRAMDATASETFILEPATH(String HYPNOGRAMDATASETFILEPATH) {
        Config.HYPNOGRAMDATASETFILEPATH = HYPNOGRAMDATASETFILEPATH;
    }

    /**
     * Setter
     * @param WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH web user navigation behavior dataset file path
     */
    public static void setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(String WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH) {
        Config.WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH = WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH;
    }

    /*--------------------- String Sources -------------------*/
    public final static String VERSIONINFO =
            "\n|*******************************************************************************|\n" +
            " * Version 1.1.0\n" +
            " * Copyright 2015 Alex Wong\n" +
            " * \n" +
            " * Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
            " * you may not use this file except in compliance with the License.\n" +
            " * You may obtain a copy of the License at\n" +
            " * \n" +
            " * http://www.apache.org/licenses/LICENSE-2.0\n" +
            " * \n" +
            " * Unless required by applicable law or agreed to in writing, software\n" +
            " * distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
            " * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
            " * See the License for the specific language governing permissions and\n" +
            " * limitations under the License.\n" +
            "|*******************************************************************************|\n"; // software version

    /**
     * Print out all config information
     * @return all config information
     */
    public static String toFormatAsString() {
        StringBuffer sb = new StringBuffer();

        // DCDMC Parameter Default Configuration
        sb.append("\n   ||************************************************************** DCDMC Configurations ********************************************************||\n");
        sb.append("         Cluster Num: " + CLUSTERNUM + "\n");
        sb.append("         Similarity: " + SIMILARITY + "\n");
        sb.append("         Dataset Type: " + DATASETTYPE + "\n");
        sb.append("         Dataset Path: " + DATASETPATH + "\n");
        sb.append("         State Number: " + STATENUM + "\n");
        sb.append("         Dataset Format: " + DATAFORMAT + "\n");
        sb.append("         Dynamic Time Warping Type: " + DTWTYPE + "\n");
        sb.append("         Deviated Dynamic Time Warping Type: " + DEVIATEDDTWTYPE + "\n");
        sb.append("         Stopping Criterion: " + STOPPINGCRITERIATYPE + "\n");
        sb.append("         Modeling Mode: " + MODELINGMODE + "\n");
        sb.append("         Dynamic Model Type: " + DYNAMICMODELTYPE + "\n");
        sb.append("         Initial Clustering Type: " + INITIALCLUSTERINGTYPE + "\n");
        sb.append("         Hierarchical Linkage Type: " + HIERARCHICALLINKAGETYPE + "\n");
        sb.append("   ||************************************************************************************************************************************************||\n");

        // File Path Default Settings
        sb.append("\n   ||*************************************************************** File Path Settings **************************************************************||\n");
        sb.append("         Config File Path: " + CONFIGPATH + "\n");
        sb.append("         Initial Clusters File Path: " + INITIALCLUSTERSFILEPATH + "\n");
        sb.append("         Final Clusters File Path: " + FINALCLUSTERSFILEPATH + "\n");
        sb.append("         Distance Matrix File Path: " + DISTANCEMATRIXFILEPATH + "\n");
        sb.append("         Hypnogram Dataset File Path: " + HYPNOGRAMDATASETFILEPATH + "\n");
        sb.append("         Web User Navigation Behavior Dataset File Path: " + WEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH + "\n");
        sb.append("   ||************************************************************************************************************************************************||\n");

        return sb.toString();
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
