package gui;

import Utilities.IOOperation;
import starter.Config;
import starter.InitialStarter;
import starter.Starter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
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
    private JPanel InitializationPanel;
    private JPanel DynamicModelsPanel;
    private JPanel DataSourcePanel;
    private JPanel StoppingCriteriaPanel;
    private JButton exitButton;
    private JPanel cdmcPanel;
    private JPanel initializerPanel;
    // ----------------------------------------------------------------------------//

    // -------------------------- Config File Variables -------------------------- //
    private String distanceMatrixFilePath;
    private String hypnogramDatasetFilePath;
    private String webUserNavigationBehaviorDatasetFilePath;
    // --------------------------------------------------------------------------- //

    // ------------------------- Model Parameter Options ------------------------- //
    private String deviatedDTWType; // Deviated Dynamic Time Warping Type Variable
    private String hierarchicalLinkageStrategy; // hierarchical clustering linkage strategy
    private String hypnogramFormat; // hypnogram format
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
                Starter starter = new Starter(parameters, DCDMCGUI.this.distanceMatrixFilePath);
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
                HierarchicalClusteringGUI hcls = new HierarchicalClusteringGUI();
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
                HypnogramGUI hd = new HypnogramGUI();
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

        // exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        hierarchicalClusteringRadioButton.addMouseListener(new MouseAdapter() {
        });
    }

    /**
     * Initialize components
     */
    private void initComponents() {

        // default configuration
        deviatedDTWType = Config.getDEVIATEDDTWTYPE();
        hierarchicalLinkageStrategy = Config.getHIERARCHICALLINKAGETYPE();
        hypnogramFormat = String.valueOf(Config.getDATAFORMAT());

        distanceMatrixFilePath = Config.getDISTANCEMATRIXFILEPATH();
        hypnogramDatasetFilePath = Config.getHYPNOGRAMDATASETFILEPATH();
        webUserNavigationBehaviorDatasetFilePath = Config.getWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH();
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
        InitializationPanel.setEnabled(enabled);
        DynamicModelsPanel.setEnabled(enabled);
        DataSourcePanel.setEnabled(enabled);
        StoppingCriteriaPanel.setEnabled(enabled);
        cdmcPanel.setEnabled(enabled);
        initializerPanel.setEnabled(enabled);
    }

    /**
     * Reset all components to default setting according to CONSTANTS configuration
     */
    private void resetAllComponents() {

        // reset basic settings
        clusterNumberTextField.setText(String.valueOf(Config.getCLUSTERNUM()));
        similarityThresholdTextField.setText(String.valueOf(Config.getSIMILARITY()));
        stateNumberTextField.setText(String.valueOf(Config.getSTATENUM()));

        // initialization
        setInitialClusteringModel(Config.getINITIALCLUSTERINGTYPE() + " " + Config.getHIERARCHICALLINKAGETYPE());
        setDynamicTimeWarping(Config.getDTWTYPE());

        // data source
        setDataSource(Config.getDATASETTYPE() + " " + Config.getDATASETPATH() + " " + Config.getSTATENUM() + " " + Config.getDATAFORMAT());

        // dynamic models
        setModelType(Config.getMODELINGMODE());
        setDynamicModel(Config.getDYNAMICMODELTYPE());

        // stopping criteria
        setStoppingCriteria(Config.getSTOPPINGCRITERIATYPE());
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

        //Create the menu bar.
        menuBar = new JMenuBar();

        //---------------------------------------- File Menu ---------------------------------------//
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //Import Config File
        menuItem = new JMenuItem("Import Config File ...", KeyEvent.VK_I);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser

                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(DCDMCGUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println("        Open File: [" + file.getAbsolutePath() + "] successfully.");
                    importConfigFile(file);


                } else {
                    System.out.println("        Cancel to open file.");
                }
            }
        });

        // Export Config File
        menuItem = new JMenuItem("Export Config File ...", KeyEvent.VK_E);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser

                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(DCDMCGUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File file = fc.getSelectedFile();
                    exportConfigFile(file);
                    System.out.println("        Save File: [" + file.getAbsolutePath() + "] successfully.");

                } else {
                    System.out.println("        Cancel to save file.");
                }
            }
        });

        menu.addSeparator();

        //a submenu

        submenu = new JMenu("Settings...");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("Paths...");
        submenu.add(menuItem);
        menu.add(submenu);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DCDMCGUI.this.setAllComponentsEnabled(false);
                PathSettingsGUI pathSettingsGUI = new PathSettingsGUI(DCDMCGUI.this);
            }
        });

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

        //-------------------------------------- Settings Menu --------------------------------------//
        menu = new JMenu("Edit");
        menuBar.add(menu);
        menuItem = new JMenuItem("Reset Configurations");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DCDMCGUI.this.setAllComponentsEnabled(false);
                Object[] options = {"Yes, please",
                        "No, thanks"};
                int n = JOptionPane.showOptionDialog(DCDMCGUI.this,
                        "Would you like to reset all configurations?",
                        "Caution",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);

                // press yes button
                if (n == 0) {
                    Config.resetConfig(); // reset all parameters
                    DCDMCGUI.this.resetAllComponents(); // reset all gui configuration
                    System.out.println("            Reset configurations in all components successfully.");
                } else {
                    System.out.println("            Cancel to reset configurations in all components.");
                }

                DCDMCGUI.this.setAllComponentsEnabled(true);
            }
        });

        menuItem = new JMenuItem("Show All Configurations");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame frame = new JFrame("Distributed Collective Dynamical Modeling & Clustering Configurations");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                //Create the content-pane-to-be.
                JPanel contentPane = new JPanel(new BorderLayout());
                contentPane.setOpaque(true);

                //Create a scrolled text area.
                JTextArea output = new JTextArea(28, 75);
                output.setEditable(false);
                output.append("\n");
                Config config = new Config(DCDMCGUI.this.getParameters());
                output.append(Config.toFormatAsString());
                output.setCaretPosition(output.getDocument().getLength());
                JScrollPane scrollPane = new JScrollPane(output);

                //Add the text area to the content pane.
                contentPane.add(scrollPane, BorderLayout.CENTER);

                // Add the button to the content pane.
                JButton jButton = new JButton("Close");
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }
                });
                contentPane.add(jButton, BorderLayout.PAGE_END);

                //Create and set up the content pane.
                frame.setContentPane(contentPane);

                //Display the window.
                Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
                frame.setSize((int)screenDimension.getWidth() / 2, (int)screenDimension.getWidth() / 2);
                frame.setLocationRelativeTo(null);
                frame.pack();
                frame.setVisible(true);
            }
        });

        //---------------------------------------- Help Menu ----------------------------------------//
        menu = new JMenu("Help");
        menuBar.add(menu);

        menuItem = new JMenuItem("Software website");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI("https://github.com/wcy1984123/DCDMCS"));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        menuItem = new JMenuItem("Software Version & Copyright");
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DCDMCGUI.this, Config.getVERSIONINFO(), "Software Version & Copyright Information", JOptionPane.WARNING_MESSAGE);
            }
        });


        menuItem = new JMenuItem("About us");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DCDMCGUI.this, "KNOWLEDGE DISCOVERY AND DATA MINING \n" +
                        "RESEARCH GROUP", "About us", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(menuItem);

        return menuBar;
    }

    /**
     * Import config file and set it up in DCDMC GUI
     * @param file file pointer
     */
    private void importConfigFile(File file) {

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
        setModelType(configs.get(5).split(" ")[0]);
        setDynamicModel(configs.get(5).split(" ")[1]);

        // set clustering model + strategy
        setInitialClusteringModel(configs.get(6));
    }

    /**
     * Export config file and set it up in DCDMC GUI
     * @param file file pointer
     */
    private void exportConfigFile(File file) {

        List<String> configs = new ArrayList<String>();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            List<String> parameters = getParameters();

            for (String line : parameters) {
                bw.write(line + "\n");
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
            res = "hypnogram" + " " + hypnogramDatasetFilePath;
        } else if (webUserNavigationBehaviorRadioButton.isSelected()) {
            res = "msnbc" + " " + webUserNavigationBehaviorDatasetFilePath;
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
            this.hypnogramDatasetFilePath = params[1];
            this.stateNumberTextField.setText(params[2]);
            this.hypnogramFormat = params[3];
        } else if (param.equals("MSNBC")) {
            webUserNavigationBehaviorRadioButton.setSelected(true);
            this.hypnogramDatasetFilePath = params[1];
            this.stateNumberTextField.setText(params[2]);
            this.hypnogramFormat = null;
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
        } else if (dtw.equals("GlobalWeightedDTW")) {
            deviatedDynamicTimeWarpingRadioButton.setSelected(true);
            this.deviatedDTWType = "GlobalWeightedDTW";
        } else if (dtw.equals("StepwiseDeviatedDTW")) {
            deviatedDynamicTimeWarpingRadioButton.setSelected(true);
            this.deviatedDTWType = "StepwiseDeviatedDTW";
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

        String[] params = initialClusterType.split(" ");
        String icType = params[0].toUpperCase();

        if (icType.equals("HIERARCHICALCLUSTERING")) {
            hierarchicalClusteringRadioButton.setSelected(true);
            this.hierarchicalLinkageStrategy = params[1];
        } else if (icType.equals("KMEANSCLUSTERING")) {
            KMeansClusteringRadioButton.setSelected(true);
            this.hierarchicalLinkageStrategy = null;
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
                Config.setDEVIATEDDTWTYPE(gwDTW);
                Config.setDTWTYPE(gwDTW);
            } else if (e.getActionCommand().equals(sdDTW)){
                DCDMCGUI.this.deviatedDTWType = sdDTW;
                Config.setDEVIATEDDTWTYPE(sdDTW);
                Config.setDTWTYPE(sdDTW);
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

