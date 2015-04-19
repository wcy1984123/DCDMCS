package gui;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 18/Apr/2015
 * Time: 22:37
 * System Time: 10:37 PM
 */

import Utilities.Utilities;
import Utilities.IOOperation;
import adapters.BoxChartAdapter;
import adapters.BoxSeriesCollectionAdapter;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import starter.Config;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

/**
 * Class for probabilities for all instances GUI
 */
public class ProbsForAllInstancesGUI extends JPanel implements ActionListener{
    private final static Logger LOGGER = Logger.getLogger(ProbsForAllInstancesGUI.class.getName());
    private static List<List<Double>> probsForAllInstances;
    private GridBagConstraints gridBagConstraints;
    private static JFrame jFrame = null;

    /**
     * Class constructor
     */
    public ProbsForAllInstancesGUI() {

        // set up layout
        super(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        // process data
        int offset = 0; // indicate the offset of the next list in normalizedData structure
        double[] normalizedData = Utilities.normalizeListOfList(this.probsForAllInstances); // normalize data using (value - min) / (max - min)
        double[] data = Utilities.retrievePartialDataFromArray(normalizedData, offset, (offset + this.probsForAllInstances.get(0).size() - 1));

        // create box plot
        offset += this.probsForAllInstances.get(0).size();
        BoxChartAdapter chart = new BoxChartAdapter("Boxplot over Probabilities of Instances in Clusters [ " + Config.getDYNAMICMODELTYPE() + " ]", "Cluster No.", "Normalized Log-Probabilities", data);
        BoxSeriesCollectionAdapter collec = chart.getSeriesCollection();

        for (int i = 1; i < this.probsForAllInstances.size(); i++) {
            data = Utilities.retrievePartialDataFromArray(normalizedData, offset, (offset + this.probsForAllInstances.get(i).size() - 1));
            offset += this.probsForAllInstances.get(i).size();
            chart.add(data == null ? new double[]{0.0} : data);
        }

        // change font and its size
        JFreeChart jc = chart.getJFreeChart();
        TextTitle tt = jc.getTitle();
        tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font

        // add view to panel
        JFrame jFrame = chart.view(200, 300);
        Component component = jFrame.getComponent(0);
        add(component, gridBagConstraints);

        //Set button
        JButton setButton = new JButton("Close");
        setButton.setActionCommand("setButton");
        setButton.addActionListener(this);
        gridBagConstraints.gridy += component.getBounds().height;
        gridBagConstraints.weighty = 0;
        add(setButton, gridBagConstraints);

        // set up border
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("setButton")) {
            jFrame.dispose(); // close this frame
        } else {
            LOGGER.info("No corresponding action command!");
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @param dcdmcgui a parent gui
     */
    public static void createAndShowGUI(final DCDMCGUI dcdmcgui, final List<List<Double>> probsForAllInstances) {
        // Create and set up the window
        jFrame = new JFrame("Boxplot over Probabilities of Instances in Clusters [ " + Config.getDYNAMICMODELTYPE() + " ]");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // save cluster labels
        ProbsForAllInstancesGUI.probsForAllInstances = probsForAllInstances;

        // Create and set up the content pane.
        JComponent newContentPane = new ProbsForAllInstancesGUI();

        newContentPane.setOpaque(true); //content panes must be opaque
        jFrame.setContentPane(newContentPane);

        //Display the window.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.pack();
        jFrame.setSize(screenSize.width / 3, screenSize.height / 3);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

        double[] data1 = null;
        double[] data2 = null;

        List<List<Double>> probsForAllInstances = IOOperation.readProbsFromFile(Config.getFINALPROBSFORALLINSTANCESFILEPATH());

        data1 = Utilities.convertToOneDimensionalDoubleArray(probsForAllInstances.get(1));

        data2 = Utilities.convertToOneDimensionalDoubleArray(probsForAllInstances.get(2));

        data1 = Utilities.normalizeArray(data1);
        data2 = Utilities.normalizeArray(data2);

        BoxChartAdapter bc = new BoxChartAdapter("Boxplot1", "Series", "Y", data1, data2);
        JFrame jFrame = bc.view(600, 400);

        jFrame.setVisible(true);

    }
}
