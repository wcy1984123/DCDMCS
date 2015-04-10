package gui;

import Utilities.IOOperation;
import starter.InitialStarter;
import starter.Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
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
    private JButton startButton;
    private JButton runButton;
    // ----------------------------------------------------------------------------//


    // ------------------------- Model Parameter Options ------------------------- //
    String deviatedDTWType; // Deviated Dynamic Time Warping Type Variable
    String hierarchicalLinkageStrategy; // hierarchical clustering linkage strategy
    String hypnogramFormat; // hypnogram format
    // ----------------------------------------------------------------------------//


    /**
     * class constructor
     */
    public DCDMCGUI() {
        super("Distributed Collective Dynamic Modeling & Clustering");
        setJMenuBar(createMenuBar());
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
                Starter starter = new Starter(parameters, "/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/results/DistanceMatrix.txt");
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
                DeviatedDTWGUI ddtw = new DeviatedDTWGUI();
                DCDMCGUI.this.setAllComponentsEnabled(false);
                ddtw.createAndShowGUI();
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
                HierarchicalClusteringGUI hcls = new HierarchicalClusteringGUI();
                DCDMCGUI.this.setAllComponentsEnabled(false);
                hcls.createAndShowGUI();
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
                HypnogramGUI hd = new HypnogramGUI();
                DCDMCGUI.this.setAllComponentsEnabled(false);
                hd.createAndShowGUI();
            }
        });
    }

    /**
     * Initialize components
     */
    private void initComponents() {
        deviatedDTWType = "GLOBALWEIGHTEDDTW";
        hierarchicalLinkageStrategy = "AVERAGELINKAGESTRATEGY";
        hypnogramFormat = "3";
    }

    /**
     * set all components enabled
     * @param enabled true if it is enabled; otherwise not enabled
     */
    private void setAllComponentsEnabled(boolean enabled) {

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
        otherDataSourceTextField.setEnabled(enabled);
        stateNumberTextField.setEnabled(enabled);
        markovChainModelRadioButton.setEnabled(enabled);
        semiMarkovChainModelRadioButton.setEnabled(enabled);
        hiddenMarkovModelRadioButton.setEnabled(enabled);
        hiddenStateDurationMarkovRadioButton.setEnabled(enabled);
        normalMutualInformationRadioButton.setEnabled(enabled);
        adjustedRandIndexRadioButton.setEnabled(enabled);
        randIndexRadioButton.setEnabled(enabled);
        stateBasedDynamicModelRadioButton.setEnabled(enabled);
        purityRadioButton.setEnabled(enabled);
        hiddenSemiMarkovChainRadioButton.setEnabled(enabled);
        startButton.setEnabled(enabled);
        DCDMCPanel.setEnabled(enabled);
    }

    /**
     * Create menu bar
     * @return a object of JMenuBar
     * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
     */
    public JMenuBar createMenuBar() {

        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Import Config File ...", KeyEvent.VK_T);
        menu.add(menuItem);

        menu.addSeparator();

        //a submenu
        submenu = new JMenu("Data Preprocess");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Calculate Distance Matrix");
        submenu.add(menuItem);
        menu.add(submenu);

        submenu = new JMenu("Settings...");
        submenu.setMnemonic(KeyEvent.VK_1);

        menuItem = new JMenuItem("Save Paths...");
        submenu.add(menuItem);
        menu.add(submenu);

        menu.addSeparator();

        menuItem = new JMenuItem("Exit", KeyEvent.VK_0);
        menuItem.addActionListener(
                new ActionListener() {
                    /**
                     * Action performed function
                     * @param e event
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // exit the application when window's close button is clicked
                        System.exit(0);
                    }
                }

        );
        menu.add(menuItem);

        //Build second menu in the menu bar.
        menu = new JMenu("Help");
        menuBar.add(menu);
        menuItem = new JMenuItem("Version");
        menu.add(menuItem);
        menuItem = new JMenuItem("About Us");
        menu.add(menuItem);

        return menuBar;
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

        // data source + state number + dataset format
        String dataSource = getDataSource();
        String stateNumber = this.stateNumberTextField.getText();
        configs.add(dataSource + " " + stateNumber + " " + this.hypnogramFormat);

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

        // clustering model + strategy
        String clusteringModel = getInitialClusteringModel();
        String linkageStrategy = this.hierarchicalLinkageStrategy;
        configs.add(clusteringModel + " " + linkageStrategy);

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
            if (deviatedDTWType.equals("GlobalWeightedDTW")) {
                res = "GlobalWeightedDTW";
            } else {
                res = "StepwiseDeviatedDTW";
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
        // redirect standard output into text area
        ConsoleGUI console = new ConsoleGUI();
        try {
            IOOperation.console(console.getConsoleTextArea());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class for GUI of deviated dynamic time warping
     */
    private class DeviatedDTWGUI extends JPanel implements ActionListener{
        private final String gwDTW = "GLOBALWEIGHTEDDTW";
        private final String sdDTW = "STEPWISEDEVIATEDDTW";

        JFrame jFrame = null;

        public DeviatedDTWGUI() {
            super(new BorderLayout());

            //Create the radio buttons.
            JRadioButton gwDTWButton = new JRadioButton("Global Weighted Dynamic Time Warping");
            gwDTWButton.setMnemonic(KeyEvent.VK_G);
            gwDTWButton.setActionCommand(gwDTW);

            JRadioButton sdDTWButton = new JRadioButton("Stepwise Deviated Dynamic Time Warping");
            sdDTWButton.setMnemonic(KeyEvent.VK_S);
            sdDTWButton.setActionCommand(sdDTW);

            //Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(gwDTWButton);
            group.add(sdDTWButton);

            //Register a listener for the radio buttons.
            gwDTWButton.addActionListener(this);
            sdDTWButton.addActionListener(this);


            //Put the radio buttons in a column in a panel.
            JPanel radioPanel = new JPanel(new GridLayout(0, 1));
            radioPanel.add(gwDTWButton);
            radioPanel.add(sdDTWButton);

            add(radioPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // set up selected radio button according to existing deviatedDTW type value
            if (DCDMCGUI.this.deviatedDTWType.equals(gwDTW)) {
                gwDTWButton.setSelected(true);
            } else {
                sdDTWButton.setSelected(true);
            }
        }

        /** Listens to the radio buttons. */
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(gwDTW)) {
                DCDMCGUI.this.deviatedDTWType = gwDTW;
            } else if (e.getActionCommand().equals(sdDTW)){
                DCDMCGUI.this.deviatedDTWType = sdDTW;
            } else if (e.getActionCommand().equals("setButton")) {
                System.out.println("\n===================================");
                System.out.println("        Deviated DTW: " + DCDMCGUI.this.deviatedDTWType);
                System.out.println("===================================\n");
                DCDMCGUI.this.setAllComponentsEnabled(true);
                jFrame.dispose(); // close this frame
            } else {
                LOGGER.info("No corresponding action command!");
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private void createAndShowGUI() {
            //Create and set up the window.
            jFrame = new JFrame("Deviated Dynamic Time Warping Settings");
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new DeviatedDTWGUI();

            //Set button
            JButton setButton = new JButton("Set");
            setButton.setActionCommand("setButton");
            setButton.addActionListener(this);
            newContentPane.add(setButton, BorderLayout.PAGE_END);

            newContentPane.setOpaque(true); //content panes must be opaque
            jFrame.setContentPane(newContentPane);

            //Display the window.
            jFrame.pack();
            jFrame.setPreferredSize(jFrame.getPreferredSize());
            jFrame.setMaximumSize(jFrame.getPreferredSize());
            jFrame.setMinimumSize(jFrame.getPreferredSize());
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
        }
    }


    /**
     * Class for GUI of hierarchical clustering
     */
    private class HierarchicalClusteringGUI extends JPanel implements ActionListener{
        private final String averageLinkageStrategy = "AVERAGELINKAGESTRATEGY";
        private final String completeLinkageStrategy = "COMPLETELINKAGESTRATEGY";
        private final String singleLinkageStrategy = "SINGLELINKAGESTRATEGY";
        private final String weightedLinkageStrategy = "WEIGHTEDLINKAGESTRATEGY";

        JFrame jFrame = null;

        public HierarchicalClusteringGUI() {
            super(new BorderLayout());

            //Create the radio buttons.
            JRadioButton averageStrategyButton = new JRadioButton("Average Linkage Strategy");
            averageStrategyButton.setMnemonic(KeyEvent.VK_A);
            averageStrategyButton.setActionCommand(averageLinkageStrategy);
            averageStrategyButton.setToolTipText("In Average Linkage Clustering, the distance between two items x and y is the mean of all pairwise distances between items contained in x and y.");

            JRadioButton completeStrategyButton = new JRadioButton("Complete Linkage Strategy");
            completeStrategyButton.setMnemonic(KeyEvent.VK_C);
            completeStrategyButton.setActionCommand(completeLinkageStrategy);
            completeStrategyButton.setToolTipText("In Complete Linkage Clustering the distance between two items x and y is the maximum of all pairwise distances between items contained in x and y. \nAs in single linkage clustering, no other distances need to be calculated once the distance matrix is known.");

            JRadioButton singleStrategyButton = new JRadioButton("Single Linkage Strategy");
            singleStrategyButton.setMnemonic(KeyEvent.VK_S);
            singleStrategyButton.setActionCommand(singleLinkageStrategy);
            singleStrategyButton.setToolTipText("In Single Linkage Clustering the distance between two items x and y is the minimum of all pairwise distances between items contained in x and y. \nUnlike centroid linkage clustering, in single linkage clustering no further distances need to be calculated once the distance matrix is known.");

            JRadioButton weightedStrategyButton = new JRadioButton("Weighted Linkage Strategy");
            weightedStrategyButton.setMnemonic(KeyEvent.VK_W);
            weightedStrategyButton.setActionCommand(weightedLinkageStrategy);
            weightedStrategyButton.setToolTipText("In Weighted Linkage Clustering, a weight is assigned to each pseudo-item, and this weight is used to compute the distances between this pseudo-item and all remaining items or pseudo-items using the same similarity metric as was used to calculate the initial similarity matrix.");

            //Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(averageStrategyButton);
            group.add(completeStrategyButton);
            group.add(singleStrategyButton);
            group.add(weightedStrategyButton);

            //Register a listener for the radio buttons.
            averageStrategyButton.addActionListener(this);
            completeStrategyButton.addActionListener(this);
            singleStrategyButton.addActionListener(this);
            weightedStrategyButton.addActionListener(this);


            //Put the radio buttons in a column in a panel.
            JPanel radioPanel = new JPanel(new GridLayout(0, 1));
            radioPanel.add(averageStrategyButton);
            radioPanel.add(completeStrategyButton);
            radioPanel.add(singleStrategyButton);
            radioPanel.add(weightedStrategyButton);

            add(radioPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

            // set up selected radio button according to existing deviatedDTW type value
            if (DCDMCGUI.this.hierarchicalLinkageStrategy.equals(averageLinkageStrategy)) {
                averageStrategyButton.setSelected(true);
            } else if (DCDMCGUI.this.hierarchicalLinkageStrategy.equals(completeLinkageStrategy)) {
                completeStrategyButton.setSelected(true);
            } else if (DCDMCGUI.this.hierarchicalLinkageStrategy.equals(singleLinkageStrategy)) {
                singleStrategyButton.setSelected(true);
            } else if (DCDMCGUI.this.hierarchicalLinkageStrategy.equals(weightedLinkageStrategy)) {
                weightedStrategyButton.setSelected(true);
            } else {

            }
        }

        /** Listens to the radio buttons. */
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(averageLinkageStrategy)) {
                DCDMCGUI.this.hierarchicalLinkageStrategy = averageLinkageStrategy;
            } else if (e.getActionCommand().equals(completeLinkageStrategy)) {
                DCDMCGUI.this.hierarchicalLinkageStrategy = completeLinkageStrategy;
            } else if (e.getActionCommand().equals(singleLinkageStrategy)) {
                DCDMCGUI.this.hierarchicalLinkageStrategy = singleLinkageStrategy;
            } else if (e.getActionCommand().equals(weightedLinkageStrategy)) {
                DCDMCGUI.this.hierarchicalLinkageStrategy = weightedLinkageStrategy;
            } else if (e.getActionCommand().equals("setButton")) {
                System.out.println("\n===================================");
                System.out.println("        Linkage Strategy: " + DCDMCGUI.this.hierarchicalLinkageStrategy);
                System.out.println("===================================\n");
                DCDMCGUI.this.setAllComponentsEnabled(true);
                jFrame.dispose(); // close this frame
            } else {
                LOGGER.info("No corresponding action command!");
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private void createAndShowGUI() {
            //Create and set up the window
            jFrame = new JFrame("Hierarchical Linkage Strategy Settings");
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new HierarchicalClusteringGUI();

            //Set button
            JButton setButton = new JButton("Set");
            setButton.setActionCommand("setButton");
            setButton.addActionListener(this);
            newContentPane.add(setButton, BorderLayout.PAGE_END);

            newContentPane.setOpaque(true); //content panes must be opaque
            jFrame.setContentPane(newContentPane);

            //Display the window.
            jFrame.pack();
            jFrame.setPreferredSize(jFrame.getPreferredSize());
            jFrame.setMaximumSize(jFrame.getPreferredSize());
            jFrame.setMinimumSize(jFrame.getPreferredSize());
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
        }
    }


    /**
     * Class for GUI of deviated dynamic time warping
     */
    private class HypnogramGUI extends JPanel implements ActionListener{
        private final String originalDataset = "ORIGINALDATASET";
        private final String wsDataset = "WAKESLEEPDATASET";
        private final String wnrDataset = "WAKENREMREMDATASET";
        private final String wdlDataset = "WAKEDEEPLIGHTDATASET";

        JFrame jFrame = null;

        public HypnogramGUI() {
            super(new BorderLayout());

            //Create the radio buttons.
            JRadioButton originalButton = new JRadioButton("Original Dataset");
            originalButton.setMnemonic(KeyEvent.VK_O);
            originalButton.setActionCommand(originalDataset);
            originalButton.setToolTipText("It consists of 5 sleep stages: stage 1, 2, 3, 5, and wake stages.");

            JRadioButton wsButton = new JRadioButton("Wake-Sleep Dataset");
            wsButton.setMnemonic(KeyEvent.VK_W);
            wsButton.setActionCommand(wsDataset);
            wsButton.setToolTipText("It consists of 2 sleep stages: wake and sleep stages.");

            JRadioButton wnrButton = new JRadioButton("Wake-NREM-REM Dataset");
            wnrButton.setMnemonic(KeyEvent.VK_N);
            wnrButton.setActionCommand(wnrDataset);
            wnrButton.setToolTipText("It consists of 3 sleep stages: wake, NREM, and REM stages.");

            JRadioButton wdlButton = new JRadioButton("Wake-Deep-Light Dataset");
            wdlButton.setMnemonic(KeyEvent.VK_R);
            wdlButton.setActionCommand(wdlDataset);
            wdlButton.setToolTipText("It consists of 3 sleep stages: wake, deep, and light stages.");

            //Group the radio buttons.
            ButtonGroup group = new ButtonGroup();
            group.add(originalButton);
            group.add(wsButton);
            group.add(wnrButton);
            group.add(wdlButton);

            //Register a listener for the radio buttons.
            originalButton.addActionListener(this);
            wsButton.addActionListener(this);
            wnrButton.addActionListener(this);
            wdlButton.addActionListener(this);

            //Put the radio buttons in a column in a panel.
            JPanel radioPanel = new JPanel(new GridLayout(0, 1));
            radioPanel.add(originalButton);
            radioPanel.add(wsButton);
            radioPanel.add(wnrButton);
            radioPanel.add(wdlButton);

            add(radioPanel, BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // set up selected radio button according to existing deviatedDTW type value
            if (DCDMCGUI.this.hypnogramFormat.equals("5")) {
                originalButton.setSelected(true);
            } else if (DCDMCGUI.this.hypnogramFormat.equals("2")) {
                wsButton.setSelected(true);
            } else if (DCDMCGUI.this.hypnogramFormat.equals("3")) {
                wnrButton.setSelected(true);
            } else if (DCDMCGUI.this.hypnogramFormat.equals("4")) {
                wdlButton.setSelected(true);
            } else {

            }
        }

        /** Listens to the radio buttons. */
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals(originalDataset)) {
                DCDMCGUI.this.hypnogramFormat = "5";
                DCDMCGUI.this.stateNumberTextField.setText("5");
            } else if (e.getActionCommand().equals(wsDataset)) {
                DCDMCGUI.this.hypnogramFormat = "2";
                DCDMCGUI.this.stateNumberTextField.setText("2");
            } else if (e.getActionCommand().equals(wnrDataset)) {
                DCDMCGUI.this.hypnogramFormat = "3";
                DCDMCGUI.this.stateNumberTextField.setText("3");
            } else if (e.getActionCommand().equals(wdlDataset)) {
                DCDMCGUI.this.hypnogramFormat = "4";
                DCDMCGUI.this.stateNumberTextField.setText("3");
            } else if (e.getActionCommand().equals("setButton")) {
                System.out.println("\n===================================");
                System.out.println("        Hypnogram Format: " + DCDMCGUI.this.hypnogramFormat);
                System.out.println("===================================\n");
                DCDMCGUI.this.setAllComponentsEnabled(true);
                jFrame.dispose(); // close this frame
            } else {
                LOGGER.info("No corresponding action command!");
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event-dispatching thread.
         */
        private void createAndShowGUI() {
            //Create and set up the window.
            jFrame = new JFrame("Hypnogram Dataset Settings");
            jFrame.setLayout(new GridBagLayout());
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new HypnogramGUI();

            //Set button
            JButton setButton = new JButton("Set");
            setButton.setActionCommand("setButton");
            setButton.addActionListener(this);
            newContentPane.add(setButton, BorderLayout.PAGE_END);

            newContentPane.setOpaque(true); //content panes must be opaque
            jFrame.setContentPane(newContentPane);

            //Display the window.
            jFrame.pack();
            jFrame.setPreferredSize(jFrame.getPreferredSize());
            jFrame.setMaximumSize(jFrame.getPreferredSize());
            jFrame.setMinimumSize(jFrame.getPreferredSize());
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);
        }
    }

}

