package gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 21/Mar/2015
 * Time: 21:14
 * System Time: 9:14 PM
 */
public class DCDMCGUI extends JFrame{

    private static final Logger LOGGER = Logger.getLogger(DCDMCGUI.class.getName());

    private JPanel DCDMCPanel;
    private JTextField clusterNumberTextField;
    private JTextField similarityThresholdTextField;
    private JRadioButton hierarchicalClusteringRadioButton;
    private JRadioButton KMeansClusteringRadioButton;
    private JRadioButton deviatedDynamicTimeWarpingRadioButton;
    private JRadioButton originalDynamicTimeWarpingRadioButton;
    private JRadioButton matlabDynamicTimeWarpingRadioButton;
    private JRadioButton sakoeChibaDynamicTimeRadioButton;
    private JRadioButton itakuraParallelogramDynamicTimeRadioButton;
    private JRadioButton fastOptimalDynamicTimeRadioButton;
    private JRadioButton webUserNavigationBehaviorRadioButton;
    private JRadioButton hypnogramDatasetRadioButton;
    private JTextField otherDataSourceTextField;
    private JTextField stateNumberTextField;
    private JRadioButton markovChainModelRadioButton;
    private JRadioButton semiMarkovChainModelRadioButton;
    private JRadioButton hiddenMarkovModelRadioButton;
    private JRadioButton hiddenStateDurationMarkovRadioButton;
    private JRadioButton normalMutualInformationRadioButton;
    private JRadioButton adjustedRandIndexRadioButton;
    private JRadioButton randIndexRadioButton;
    private JRadioButton stateBasedDynamicModelRadioButton;
    private JRadioButton purityRadioButton;
    private JRadioButton hiddenSemiMarkovChainRadioButton;

    /**
     * class constructor
     */
    public DCDMCGUI() {
        super("Distributed Collective Dynamic Modeling & Clustering");
        setContentPane(DCDMCPanel);
        pack();


        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // show gui
    }

    private void initComponents() {

    }

    /**
     * Get all parameter settings given the GUI
     * @return a list of parameters
     */
    private List<String> getParameters() {

        List<String> configs = new ArrayList<String>();

        // cluster number
        String clusterNumber = this.clusterNumberTextField.getText();
        configs.add(clusterNumber);

        // similarity threshold
        String similarityThreshold = this.similarityThresholdTextField.getText();
        configs.add(similarityThreshold);

        // data source + state number
        String dataSource = getDataSource();
        String stateNumber = stateNumberTextField.getText();
        configs.add(dataSource + stateNumber);

        // dynamic time warping
        String dynamicTimeWarping = getDynamicTimeWarping();
        configs.add(dynamicTimeWarping);

        // stopping criteria
        String stoppingCriteria = getStoppingCriteria();
        configs.add(stoppingCriteria);

        // model type + dynamic model
        String modelType = getModelType();
        String dynamicModel = getDynamicModel();
        configs.add(modelType + " " + dynamicModel);

        return configs;
    }

    /**
     * Get data soruce
     * @return a string name of data source
     */
    private String getDataSource() {
        String res = null;
        if (hypnogramDatasetRadioButton.isSelected()) {
            res = "hypnogram" + " " + "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/dataset/hypnogram.csv";
        } else if (webUserNavigationBehaviorRadioButton.isSelected()) {
            res = "msnbc" + " " + "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/dataset/msnbcData.csv";
        } else {
            LOGGER.warning("The input data source is null!");
        }

        return res;
    }

    /**
     * Get dynamic time warping
     * @return a string name of dynamic time warping
     */
    private String getDynamicTimeWarping() {
        String res = null;

        if (originalDynamicTimeWarpingRadioButton.isSelected()) {
            res = "ORIGINALDTW";
        } else if (matlabDynamicTimeWarpingRadioButton.isSelected()) {
            res = "MATLABORIGINALDTW";
        } else if (sakoeChibaDynamicTimeRadioButton.isSelected()) {
            res = "SAKOECHIBADTW";
        } else if (itakuraParallelogramDynamicTimeRadioButton.isSelected()) {
            res = "ITAKURAPARALLELOGRAMDTW";
        } else if (fastOptimalDynamicTimeRadioButton.isSelected()) {
            res = "FASTOPTIMALDTW";
        } else if (deviatedDynamicTimeWarpingRadioButton.isSelected()) {
            res = "DEVIATEDDTW";
        } else {
            LOGGER.warning("The dynamic time warping is null!");
        }

        return res;
    }

    /**
     * Get stopping criteria
     * @return a string name of stopping criteria
     */
    private String getStoppingCriteria() {
        String res = null;

        if (randIndexRadioButton.isSelected()) {
            res = "RANDINDEX";
        } else if (adjustedRandIndexRadioButton.isSelected()) {
            res = "ADJUSTEDRANDINDEX";
        } else if (normalMutualInformationRadioButton.isSelected()) {
            res = "NORMALIZEDMUTUALINFORMATION";
        } else if (purityRadioButton.isSelected()) {
            res = "PURITY";
        } else {
            LOGGER.warning("The stopping criteria is null!");
        }

        return res;
    }

    /**
     * Get model type
     * @return a string name of model type
     */
    private String getModelType(){
        String res = null;
        if (stateBasedDynamicModelRadioButton.isSelected()) {
            res = "STATEBASEDDYNAMICMODELS";
        } else {
            LOGGER.warning("The model type is null!");
        }

        return res;
    }

    /**
     * Get dynamic model
     * @return a string name of dynamic model
     */
    private String getDynamicModel(){
        String res = null;
        if (markovChainModelRadioButton.isSelected()) {
            res = "MARKOVCHAINMODEL";
        } else if (semiMarkovChainModelRadioButton.isSelected()) {
            res = "SEMIMARKOVCHAINMODEL";
        } else if (hiddenMarkovModelRadioButton.isSelected()) {
            res = "HIDDENMARKOVMODEL";
        } else if (hiddenSemiMarkovChainRadioButton.isSelected()) {
            res = "";
        } else if (hiddenStateDurationMarkovRadioButton.isSelected()){
            res = "";
        } else {
            LOGGER.warning("The dynamic model is null!");
        }

        return res;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        DCDMCGUI test = new DCDMCGUI();
    }

}
