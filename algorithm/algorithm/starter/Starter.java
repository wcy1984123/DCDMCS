package starter;

import Utilities.IOOperation;
import Utilities.Utilities;
import adapters.HistogramChartAdapter;
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
import umontreal.iro.lecuyer.charts.HistogramSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
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
    private int[] finalClusterLabels; // final cluster labels
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

            ProgressBar progressBar = new ProgressBar();

            /**
             * Print out information
             * @param info printed-out information
             */
            private void printInBackground(String info) {
                System.out.println(info);
                // publish(info);
            }

            @Override
            protected Void doInBackground() throws Exception {

                progressBar.createAndShowGUI();

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
                finalClusterLabels = currentClusterLabels;
                IOOperation.writeFile(Starter.this.initialClusterLalels, Config.getINITIALCLUSTERSFILEPATH());
                IOOperation.writeFile(currentClusterLabels, Config.getFINALCLUSTERSFILEPATH());

                // Just in case if initial clusters have provided a good enough clustering, it never goes into the above CDMC loop
                if(currentClusterLabels == null) {
                    // build dynamic model over initial clusters
                    LOGGER.warning("        Never Enter Into CDMC Algorithm!");
                    Starter.this.mIModels.trainDynamicModels(instances, Config.getCLUSTERNUM(), previousClusterLabels,  MODELTYPE.valueOf(Config.getDYNAMICMODELTYPE()));
                }

                // visualize initial and final cluster distributions
                Starter.this.visualizeClusterDistributionVariation();

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

            /*
             * Executed in event dispatch thread
             */
            @Override
            public void done() {
                progressBar.dispose();
                Toolkit.getDefaultToolkit().beep();
            }
        };

        task.execute();

    }

    /**
     * Progress bar class
     */
    private class ProgressBar extends JPanel{

        //Create and set up the window.
        JFrame frame;

        Dimension progressBarSize;

        private JProgressBar progressBar;

        ProgressBar() {
            super(new BorderLayout());

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            progressBarSize = new Dimension(screenSize.width / 3, 20);
            progressBar = new JProgressBar(0, 300);
            progressBar.setValue(300);
            progressBar.setPreferredSize(progressBarSize);

            //Call setStringPainted now so that the progress bar height
            //stays the same whether or not the string is shown.
            progressBar.setStringPainted(true);

            JPanel panel = new JPanel();
            panel.add(progressBar);

            add(panel, BorderLayout.PAGE_START);
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            progressBar.setIndeterminate(true);
        }

        /**
         * Create the GUI and show it. As with all GUI code, this must run
         * on the event-dispatching thread.
         */
        private void createAndShowGUI() {
            //Create and set up the window.
            frame = new JFrame("Collective Dynamical Modeling & Clustering");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new ProgressBar();
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension originalPoint = new Dimension(screenSize.width / 3, screenSize.height / 2);
            frame.setLocation(originalPoint.width, originalPoint.height);
            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        /**
         * Close progress bar
         */
        public void dispose() {
            frame.dispose();
        }

    }

    /**
     * Visualize
     */
    private void visualizeClusterDistributionVariation() {
        JFrame jFrameInitialClusterDistribution = visualizeClusterDistribution(this.initialClusterLalels, "Initial Cluster Distribution");
        JFrame jFrameFinalClusterDistribution = visualizeClusterDistribution(this.finalClusterLabels, "Final Cluster Distribution");

        JFrame jFrame = new JFrame("Histogram Chart: Initial vs. Final Cluster Distribution");
        JPanel jPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        Component jComponentInitialClusterDistribution = jFrameInitialClusterDistribution.getComponent(0);
        Component jComponentFinalClusterDistribution = jFrameFinalClusterDistribution.getComponent(0);
        jPanel.add(jComponentInitialClusterDistribution, c);
        c.gridy = jComponentInitialClusterDistribution.getBounds().y;
        jPanel.add(jComponentFinalClusterDistribution, c);
        jPanel.setOpaque(true);
        jFrame.setContentPane(jPanel);

        // set location
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(screenDimension.width / 3, 0);

        // visualize cluster distribution
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    /**
     * Visualize initial cluster distribution
     * @param clusterDistribution cluster distribution
     * @return a frame of cluster distribution
     */
    private JFrame visualizeClusterDistribution(int[] clusterDistribution, String name) {
        // build cluster labels distribution for GUI display
        double[] initialClusterDistribution = Utilities.convertToOneDimensionalDoubleArray(clusterDistribution);

        // cluster label : count
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < initialClusterDistribution.length; i++) {
            int key = clusterDistribution[i];
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
            initialClusterDistribution[i] = 2 * initialClusterDistribution[i] + 1.0;
        }

        // compute the maximum number of a cluster
        int maxSizeInOneClusters = 0;
        for (Integer key : map.keySet()) {
            maxSizeInOneClusters = Math.max(maxSizeInOneClusters, map.get(key));
        }

        // visualize initial cluster distribution
        HistogramChartAdapter chart;
        String modelName = this.mIModels.getModelName();
        chart = new HistogramChartAdapter(name + " [" + modelName + "]", "Clusters", "Frequency", initialClusterDistribution);
        HistogramSeriesCollection collec = chart.getSeriesCollection();
        collec.setBins(0, 2 * Config.getCLUSTERNUM());
        collec.setColor(0, new Color(179, 232, 172));

        double[] bounds = { 0, 2 * Config.getCLUSTERNUM(), 0, maxSizeInOneClusters};
        chart.setManualRange(bounds);

        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame jFrame = chart.view(screenDimension.width / 3, screenDimension.height / 10);
        return jFrame;
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
