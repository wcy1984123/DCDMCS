package gui;

import Utilities.IOOperation;
import initializer.dtws.IDTW;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 29/Mar/2015
 * Time: 13:42
 * System Time: 1:42 PM
 */
public class ConsoleProgressGUI extends JFrame implements PropertyChangeListener{

    private static final Logger LOGGER = Logger.getLogger(ConsoleProgressGUI.class.getName());

    //------------------ GUI Variables --------------------//
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JProgressBar taskComplettionProgressBar;
    private JScrollPane consoleScroll;
    private Task task;

    //------------------- Data Variable -------------------//
    private int progressLength;
    private double[][] distanceMatrix;
    private IDTW idtw;
    private List<List<Double>> instances;
    private boolean flag;

    /**
     * Task is used to invoke a background thread of doing a lont-time task
     */
    class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            consoleTextArea.append("\n ||------- Distance Calculation Begins ------||\n");

            // Compute the distance matrix
            for (int i = 0; i < progressLength; i++) {
                for (int j = i + 1; j < progressLength; j++) {

                    // get the dynamic time warping distance between two sequences
                    distanceMatrix[i][j] = idtw.computeDistance(instances.get(i), instances.get(j));

                    // get the optimal warping path between two sequences
                    // List<List<Integer>> optimalPath = idtw.computePath(null, null);

                    // here assume it guarantees the symmetric feature for DTW
                    if (j < progressLength && i < progressLength) distanceMatrix[j][i] = distanceMatrix[i][j];

                }

                System.out.println("            The instance [ " + i + " ] is done.");

                // approximately estimate the time complexity of mapping 244 instances into 100 instances.
                if (i % 2 == 0) {
                    progress++;
                    setProgress(Math.min(progress, 100));
                }
            }
            setProgress(100);
            flag = true;
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            consoleTextArea.append("\n ||-------- Distance Calculation Ends -------||\n");

            // save distance matrix into file
            IOOperation.writeFile(distanceMatrix, Config.getDISTANCEMATRIXFILEPATH());
            flag = true;

            LOGGER.info("Initialization Ends");
            System.out.println();
            System.out.println("||------------ Initialization Ends ------------||");
            System.out.println();
        }
    }

    /**
     * true if it is finished, otherwise false.
     */
    public boolean isFinished() {
        return flag;
    }

    /**
     * Getter for distance matrix
     * @return distance matrix
     */
    public double[][] getDistanceMatrix() {
        return this.distanceMatrix;
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            taskComplettionProgressBar.setValue(progress);
            consoleTextArea.append(String.format(
                    "       Completed %d%% of task.\n", task.getProgress()));
        }
    }

    /**
     * class constructor
     * @param taskName task name
     * @param progressLength the maximum of progress length
     */
    public ConsoleProgressGUI(String taskName, int progressLength, List<List<Double>> instances, IDTW idtw) {
        super("Console Progress Monitor - " + taskName);

        //---------------------- Initialize Data Variable ---------------------//
        this.distanceMatrix = new double[progressLength][progressLength];
        this.progressLength = progressLength;
        this.idtw = idtw;
        this.instances = instances;
        this.flag = false;

        consolePanel.setOpaque(true);
        setContentPane(consolePanel);
        pack();

        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenDimension.width / 4, 2 * screenDimension.height / 3);
        taskComplettionProgressBar.setForeground(Color.green);
        taskComplettionProgressBar.setBackground(Color.LIGHT_GRAY);
        setLocation(screenDimension.width, 0);
        setVisible(true); // show gui

        // start computing distance matrix
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Initialize components
     */
    private void initComponents() {
        taskComplettionProgressBar.setValue(0);
        taskComplettionProgressBar.setStringPainted(true);
        consoleTextArea.setMargin(new Insets(5,5,5,5));
        consoleTextArea.setEditable(false);
    }



    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
