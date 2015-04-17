package gui;

import Utilities.IOOperation;
import starter.Config;
import starter.InitialStarter;
import starter.Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
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
public class DCDMCGUI extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(DCDMCGUI.class.getName());

    // ----------------------------- GUI Components ------------------------------ //
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
    private JTextField stateNumberTextField;
    private JRadioButton markovChainModelRadioButton;
    private JRadioButton semiMarkovChainModelRadioButton;
    private JRadioButton hiddenMarkovModelRadioButton;
    private JRadioButton normalMutualInformationRadioButton;
    private JRadioButton adjustedRandIndexRadioButton;
    private JRadioButton randIndexRadioButton;
    private JRadioButton stateBasedDynamicModelRadioButton;
    private JRadioButton purityRadioButton;
    private JButton startButton;
    private JButton runButton;
    private JPanel BasicSettingsPanel;
    private JPanel DataSourcePanel;
    private JPanel StoppingCriteriaPanel;
    private JButton exitButton;
    private JLabel captionLabel;
    private JTabbedPane dcdmcTabbedPane;
    private JPanel initializationJPanel;
    private JPanel cdmcJPanel;
    private JPanel dynamicTimeWarpingJPanel;
    private JPanel clusteringAlgorithmJPanel;
    private JPanel modelTypeJPanel;
    private JPanel dynamicModelsJPanel;
    private JLabel clusterNumberJLabel;
    private JLabel similarityThresholdJLabel;
    private JLabel stateNumberJLabel;
    // ----------------------------------------------------------------------------//

    /**
     * class constructor
     */
    public DCDMCGUI() {
        super("Distributed Collective Dynamic Modeling & Clustering System");

        // add menu
        DCDMCMenuGUI dcdmcMenuGUI = new DCDMCMenuGUI(DCDMCGUI.this);
        setJMenuBar(dcdmcMenuGUI.createMenuBar());
        setContentPane(DCDMCPanel);
        pack();


        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null); // center the current frame
        setVisible(true); // show gui

        // start CDMC algorithm
        startButton.addActionListener(new ActionListener() {
            /**
             * Action performed function
             * @param e event
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> parameters = getParameters();
                Starter starter = new Starter(parameters, Config.getDISTANCEMATRIXFILEPATH());
                starter.runCDMC();
            }
        });

        // start initialization
        runButton.addActionListener(new ActionListener() {
            /**
             * Action performed
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> parameters = getParameters();
                InitialStarter initialStarter = new InitialStarter(parameters);
                initialStarter.runInitialization();
            }
        });

        // config deviated dynamic time warping
        deviatedDynamicTimeWarpingRadioButton.addActionListener(new ActionListener() {
            /**
             * Action performed
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                DeviatedDTWGUI ddtw = new DeviatedDTWGUI(DCDMCGUI.this);
                DCDMCGUI.this.setAllComponentsEnabled(false);
                ddtw.createAndShowGUI();
            }
        });

        deviatedDynamicTimeWarpingRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                deviatedDynamicTimeWarpingRadioButton.setForeground(Color.BLUE);
                deviatedDynamicTimeWarpingRadioButton.setBackground(Color.WHITE);
                deviatedDynamicTimeWarpingRadioButton.setFont(new Font("Helvetica neue", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                deviatedDynamicTimeWarpingRadioButton.setForeground(Color.BLACK);
                deviatedDynamicTimeWarpingRadioButton.setBackground(Color.WHITE);
                deviatedDynamicTimeWarpingRadioButton.setFont(new Font("Helvetica neue", Font.PLAIN, 13));
            }
        });


        // config hierarchical clustering
        hierarchicalClusteringRadioButton.addActionListener(new ActionListener() {
            /**
             * Action performed
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                HierarchicalClusteringGUI hcls = new HierarchicalClusteringGUI(DCDMCGUI.this);
                DCDMCGUI.this.setAllComponentsEnabled(false);
                hcls.createAndShowGUI();
            }
        });

        hierarchicalClusteringRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hierarchicalClusteringRadioButton.setForeground(Color.ORANGE);
                hierarchicalClusteringRadioButton.setBackground(Color.WHITE);
                hierarchicalClusteringRadioButton.setFont(new Font("Helvetica neue", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hierarchicalClusteringRadioButton.setForeground(Color.BLACK);
                hierarchicalClusteringRadioButton.setBackground(Color.WHITE);
                hierarchicalClusteringRadioButton.setFont(new Font("Helvetica neue", Font.PLAIN, 13));
            }
        });

        // config hypnogram dataset
        hypnogramDatasetRadioButton.addActionListener(new ActionListener() {
            /**
             * Action performed
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                HypnogramGUI hd = new HypnogramGUI(DCDMCGUI.this);
                DCDMCGUI.this.setAllComponentsEnabled(false);
                hd.createAndShowGUI();
            }
        });

        hypnogramDatasetRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hypnogramDatasetRadioButton.setForeground(Color.RED);
                hypnogramDatasetRadioButton.setBackground(Color.WHITE);
                hypnogramDatasetRadioButton.setFont(new Font("Helvetica neue", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hypnogramDatasetRadioButton.setForeground(Color.BLACK);
                hypnogramDatasetRadioButton.setBackground(Color.WHITE);
                hypnogramDatasetRadioButton.setFont(new Font("Helvetica neue", Font.PLAIN, 13));
            }
        });

        // parameter settings label
        captionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // double click
                if (e.getClickCount() == 2) {
                    DCDMCMenuGUI.showAllParameterConfigurations(DCDMCGUI.this);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                captionLabel.setForeground(Color.YELLOW);
                captionLabel.setBackground(Color.WHITE);
                captionLabel.setFont(new Font("Helvetica neue", Font.BOLD, 22));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                captionLabel.setForeground(Color.BLACK);
                captionLabel.setBackground(Color.WHITE);
                captionLabel.setFont(new Font("Helvetica neue", Font.BOLD, 22));
            }
        });

        // semi-Markov chain options
        semiMarkovChainModelRadioButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                SemiMarkovChainGUI semiMarkovChainGUI = new SemiMarkovChainGUI(DCDMCGUI.this);
                DCDMCGUI.this.setAllComponentsEnabled(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                semiMarkovChainModelRadioButton.setForeground(Color.GREEN);
                semiMarkovChainModelRadioButton.setBackground(Color.WHITE);
                semiMarkovChainModelRadioButton.setFont(new Font("Helvetica neue", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                semiMarkovChainModelRadioButton.setForeground(Color.BLACK);
                semiMarkovChainModelRadioButton.setBackground(Color.WHITE);
                semiMarkovChainModelRadioButton.setFont(new Font("Helvetica neue", Font.PLAIN, 13));
            }
        });

        // exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

    /**
     * Initialize components
     */
    private void initComponents() {

    }

    /**
     * set all components enabled
     * @param enabled true if it is enabled; otherwise not enabled
     */
    public void setAllComponentsEnabled(boolean enabled) {

        clusterNumberTextField.setEnabled(enabled);
        similarityThresholdTextField.setEnabled(enabled);
        hierarchicalClusteringRadioButton.setEnabled(enabled);
        KMeansClusteringRadioButton.setEnabled(enabled);
        deviatedDynamicTimeWarpingRadioButton.setEnabled(enabled);
        originalDynamicTimeWarpingRadioButton.setEnabled(enabled);
        matlabDynamicTimeWarpingRadioButton.setEnabled(enabled);
        sakoeChibaDynamicTimeRadioButton.setEnabled(enabled);
        itakuraParallelogramDynamicTimeRadioButton.setEnabled(enabled);
        fastOptimalDynamicTimeRadioButton.setEnabled(enabled);
        webUserNavigationBehaviorRadioButton.setEnabled(enabled);
        hypnogramDatasetRadioButton.setEnabled(enabled);
        stateNumberTextField.setEnabled(enabled);
        markovChainModelRadioButton.setEnabled(enabled);
        semiMarkovChainModelRadioButton.setEnabled(enabled);
        hiddenMarkovModelRadioButton.setEnabled(enabled);
        normalMutualInformationRadioButton.setEnabled(enabled);
        adjustedRandIndexRadioButton.setEnabled(enabled);
        randIndexRadioButton.setEnabled(enabled);
        stateBasedDynamicModelRadioButton.setEnabled(enabled);
        purityRadioButton.setEnabled(enabled);
        startButton.setEnabled(enabled);
        runButton.setEnabled(enabled);
        DCDMCPanel.setEnabled(enabled);
        BasicSettingsPanel.setEnabled(enabled);
        DataSourcePanel.setEnabled(enabled);
        StoppingCriteriaPanel.setEnabled(enabled);
        dcdmcTabbedPane.setEnabled(enabled);
        initializationJPanel.setEnabled(enabled);
        cdmcJPanel.setEnabled(enabled);
        dynamicTimeWarpingJPanel.setEnabled(enabled);
        clusteringAlgorithmJPanel.setEnabled(enabled);
        modelTypeJPanel.setEnabled(enabled);
        dynamicModelsJPanel.setEnabled(enabled);
        clusterNumberJLabel.setEnabled(enabled);
        similarityThresholdJLabel.setEnabled(enabled);
        stateNumberJLabel.setEnabled(enabled);
    }

    /**
     * Reset all components to default setting according to CONSTANTS configuration
     */
    public void resetAllComponents() {

        // reset basic settings
        clusterNumberTextField.setText(String.valueOf(Config.getCLUSTERNUM()));
        similarityThresholdTextField.setText(String.valueOf(Config.getSIMILARITY()));
        stateNumberTextField.setText(String.valueOf(Config.getSTATENUM()));

        // initialization
        setInitialClusteringModel(Config.getINITIALCLUSTERINGTYPE() + Config.getCONFIGSEPARATOR() + Config.getHIERARCHICALLINKAGETYPE());
        setDynamicTimeWarping(Config.getDTWTYPE());

        // data source
        setDataSource(Config.getDATASETTYPE() + Config.getCONFIGSEPARATOR() + Config.getDATASETPATH() + Config.getCONFIGSEPARATOR() + Config.getSTATENUM() + Config.getCONFIGSEPARATOR() + Config.getDATAFORMAT());

        // dynamic models
        setModelType(Config.getMODELINGMODE());
        setDynamicModel(Config.getDYNAMICMODELTYPE());

        // stopping criteria
        setStoppingCriteria(Config.getSTOPPINGCRITERIATYPE());
    }

    /**
     * Import config file and set it up in DCDMC GUI
     * @param file file pointer
     */
    public void importConfigFile(File file) {

        List<String> configs = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            while((line = br.readLine()) != null) {
                    configs.add(line);
            }

            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // cluster number
        this.clusterNumberTextField.setText(configs.get(0));
        this.similarityThresholdTextField.setText(configs.get(1));

        // set dataset type
        setDataSource(configs.get(2));


        // set dynamic time warping
        setDynamicTimeWarping(configs.get(3));

        // set stopping criteria
        setStoppingCriteria(configs.get(4));

        // set model type + dynamic model
        setModelType(configs.get(5).split(Config.getCONFIGSEPARATOR())[0]);
        setDynamicModel(configs.get(5).split(Config.getCONFIGSEPARATOR())[1]);

        // set clustering model + strategy
        setInitialClusteringModel(configs.get(6));
    }

    /**
     * Export config file and set it up in DCDMC GUI
     * @param file file pointer
     */
    public void exportConfigFile(File file) {

        List<String> configs = new ArrayList<String>();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            List<String> parameters = getParameters();

            for (String line : parameters) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Get all parameter settings given the GUI
     * @return a list of parameters
     */
    public List<String> getParameters() {

        List<String> configs = new ArrayList<String>();

        // cluster number
        String clusterNumber = this.clusterNumberTextField.getText();
        configs.add(clusterNumber);

        // similarity threshold
        String similarityThreshold = this.similarityThresholdTextField.getText();
        configs.add(similarityThreshold);

        // data source + state number + dataset format
        String dataSource = getDataSource();
        String stateNumber = this.stateNumberTextField.getText();
        configs.add(dataSource + Config.getCONFIGSEPARATOR() + stateNumber + Config.getCONFIGSEPARATOR() + Config.getDATAFORMAT());

        // dynamic time warping
        String dynamicTimeWarping = getDynamicTimeWarping();
        configs.add(dynamicTimeWarping);

        // stopping criteria
        String stoppingCriteria = getStoppingCriteria();
        configs.add(stoppingCriteria);

        // model type + dynamic model
        String modelType = getModelType();
        String dynamicModel = getDynamicModel();
        configs.add(modelType + Config.getCONFIGSEPARATOR() + dynamicModel);

        // clustering model + strategy
        String clusteringModel = getInitialClusteringModel();
        String linkageStrategy = Config.getHIERARCHICALLINKAGETYPE();
        configs.add(clusteringModel + Config.getCONFIGSEPARATOR() + linkageStrategy);

        return configs;
    }

    /**
     * Get data soruce
     * @return a string name of data source
     */
    private String getDataSource() {
        String res = null;
        if (hypnogramDatasetRadioButton.isSelected()) {
            res = "HYPNOGRAM" + Config.getCONFIGSEPARATOR() + Config.getHYPNOGRAMDATASETFILEPATH();;
        } else if (webUserNavigationBehaviorRadioButton.isSelected()) {
            res = "MSNBC" + Config.getCONFIGSEPARATOR() + Config.getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH();
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
            if (Config.getDEVIATEDDTWTYPE().equals("GLOBALWEIGHTEDDTW")) {
                res = "GLOBALWEIGHTEDDTW";
            } else {
                res = "STEPWISEDEVIATEDDTW";

            }
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
     * Get initial clustering model
     * @return a string name of initial clustering model
     */
    private String getInitialClusteringModel() {
        String res = null;

        if (hierarchicalClusteringRadioButton.isSelected()) {
            res = "HIERARCHICALCLUSTERING";
        } else if (KMeansClusteringRadioButton.isSelected()) {
            res = "KMEANSCLUSTERING";
        } else {
            LOGGER.warning("The initial clustering model is null!");
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
        } else {
            LOGGER.warning("The dynamic model is null!");
        }

        return res;
    }

    /**
     * Set data soruce
     * @param datatype dataset type
     */
    private void setDataSource(String datatype) {

        if (datatype == null || datatype.length() == 0) {
            System.out.println("        Error: data type is null!");
            return;
        }

        String[] params = datatype.split(" ");

        String param = params[0].toUpperCase();
        if (param.equals("HYPNOGRAM")) {
            hypnogramDatasetRadioButton.setSelected(true);
            Config.setHYPNOGRAMDATASETFILEPATH(params[1]);
            this.stateNumberTextField.setText(params[2]);
            Config.setDATAFORMAT(Integer.parseInt(params[3]));
        } else if (param.equals("MSNBC")) {
            webUserNavigationBehaviorRadioButton.setSelected(true);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(params[1]);
            this.stateNumberTextField.setText(params[2]);
            Config.setDATAFORMAT(0);
        } else {
            LOGGER.warning("The input data source is invalid!");
        }
    }

    /**
     * Set dynamic time warping
     * @param dtwType dynamic time warping type
     */
    private void setDynamicTimeWarping(String dtwType) {

        if (dtwType == null || dtwType.length() == 0) {
            System.out.println("        Error: DTW type is null!");
            return;
        }

        String dtw = dtwType.toUpperCase();
        if (dtw.equals("ORIGINALDTW")) {
            originalDynamicTimeWarpingRadioButton.setSelected(true);
        } else if (dtw.equals("MATLABORIGINALDTW")) {
            matlabDynamicTimeWarpingRadioButton.setSelected(true);
        } else if (dtw.equals("SAKOECHIBADTW")) {
            sakoeChibaDynamicTimeRadioButton.setSelected(true);
        } else if (dtw.equals("ITAKURAPARALLELOGRAMDTW")) {
            itakuraParallelogramDynamicTimeRadioButton.setSelected(true);
        } else if (dtw.equals("FASTOPTIMALDTW")) {
            fastOptimalDynamicTimeRadioButton.setSelected(true);
        } else if (dtw.equals("GLOBALWEIGHTEDDTW")) {
            deviatedDynamicTimeWarpingRadioButton.setSelected(true);
            Config.setDEVIATEDDTWTYPE("GLOBALWEIGHTEDDTW");
        } else if (dtw.equals("STEPWISEDEVIATEDDTW")) {
            deviatedDynamicTimeWarpingRadioButton.setSelected(true);
            Config.setDEVIATEDDTWTYPE("STEPWISEDEVIATEDDTW");
        } else {
            LOGGER.warning("The dynamic time warping is invalid!");
        }

    }

    /**
     * Set stopping criteria
     * @param stoppingCriteriaType stopping criteria type
     */
    private void setStoppingCriteria(String stoppingCriteriaType) {

        if (stoppingCriteriaType == null || stoppingCriteriaType.length() == 0) {
            System.out.println("        Stopping Criteria Type is null!");
            return;
        }

        String scType = stoppingCriteriaType.toUpperCase();

        if (scType.equals("RANDINDEX")) {
            randIndexRadioButton.setSelected(true);
        } else if (scType.equals("ADJUSTEDRANDINDEX")) {
            adjustedRandIndexRadioButton.setSelected(true);
        } else if (scType.equals("NORMALIZEDMUTUALINFORMATION")) {
            normalMutualInformationRadioButton.setSelected(true);
        } else if (scType.equals("PURITY")) {
            purityRadioButton.setSelected(true);
        } else {
            LOGGER.warning("The stopping criteria is invalid!");
        }

    }

    /**
     * Set initial clustering model
     * @param initialClusterType  a string name of initial clustering model
     */
    private void setInitialClusteringModel(String initialClusterType) {

        if (initialClusterType == null || initialClusterType.length() == 0) {
            System.out.println("        The initial cluster type is null!");
            return;
        }

        String[] params = initialClusterType.split(Config.getCONFIGSEPARATOR());
        String icType = params[0].toUpperCase();

        if (icType.equals("HIERARCHICALCLUSTERING")) {
            hierarchicalClusteringRadioButton.setSelected(true);
            Config.setHIERARCHICALLINKAGETYPE(params[1]);
        } else if (icType.equals("KMEANSCLUSTERING")) {
            KMeansClusteringRadioButton.setSelected(true);
            Config.setHIERARCHICALLINKAGETYPE("");
        } else {
            LOGGER.warning("The initial clustering model is invalid!");
        }

    }

    /**
     * Set model type
     * @param modelType  a string name of model type
     */
    private void setModelType(String modelType){

        if (modelType == null || modelType.length() == 0) {
            System.out.println("        The model type is null!");
            return;
        }

        String mt = modelType.toUpperCase();
        if (mt.equals("STATEBASEDDYNAMICMODELS")) {
            stateBasedDynamicModelRadioButton.setSelected(true);
        } else {
            LOGGER.warning("The model type is invalid!");
        }

    }

    /**
     * Set dynamic model
     * @param dynamicModelType  a string name of dynamic model
     */
    private void setDynamicModel(String dynamicModelType){

        if (dynamicModelType == null || dynamicModelType.length() == 0) {
            System.out.println("        The dynamic model type is null!");
            return;
        }

        String dmType = dynamicModelType.toUpperCase();
        if (dmType.equals("MARKOVCHAINMODEL")) {
            markovChainModelRadioButton.setSelected(true);
        } else if (dmType.equals("SEMIMARKOVCHAINMODEL")) {
            semiMarkovChainModelRadioButton.setSelected(true);
        } else if (dmType.equals("HIDDENMARKOVMODEL")) {
            hiddenMarkovModelRadioButton.setSelected(true);
        } else {
            LOGGER.warning("The dynamic model is invalid!");
        }
    }

    /**
     * Getter
     * @return state number text field
     */
    public JTextField getStateNumberTextField() {
        return stateNumberTextField;
    }

    /**
     * Setter
     * @param stateNumberTextField state number text field
     */
    public void setStateNumberTextField(JTextField stateNumberTextField) {
        this.stateNumberTextField = stateNumberTextField;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        DCDMCGUI test = new DCDMCGUI();
        // redirect standard output into text area
        ConsoleGUI console = new ConsoleGUI();
        try {
            IOOperation.console(console.getConsoleTextArea());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

