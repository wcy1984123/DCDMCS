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
import adapters.XYLineChartApdater;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import starter.Config;
import umontreal.iro.lecuyer.charts.BoxChart;
import umontreal.iro.lecuyer.charts.BoxSeriesCollection;
import umontreal.iro.lecuyer.charts.XYListSeriesCollection;

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

        double[] data = Utilities.convertToOneDimensionalDoubleArray(this.probsForAllInstances.get(0));
//        for (int i = 0; i < data.length; i++) {
//            data[i] = data[i] * -1;
//        }
        BoxChartAdapter chart = new BoxChartAdapter("Box Plot Over Probabilities Of All Instances", "Cluster No.", "Y", data);
        BoxSeriesCollection collec = chart.getSeriesCollection();
        collec.setColor(0, Config.getCOLORCOLLECTION()[0]);
        for (int i = 1; i < this.probsForAllInstances.size(); i++) {
            data = Utilities.convertToOneDimensionalDoubleArray(this.probsForAllInstances.get(i));
            if (data == null) chart.add(new double[]{0.0});
            else chart.add(data);
            collec.setColor(i, Config.getCOLORCOLLECTION()[i % Config.getCOLORCOLLECTION().length]);
        }

        chart.setFillBox(true);
        // change font and its size
        JFreeChart jc = chart.getJFreeChart();
        TextTitle tt = jc.getTitle();
        tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font

        // add view to panel
        add(chart.view(300, 400).getComponent(0), gridBagConstraints);

        //Set button
        JButton setButton = new JButton("Close");
        setButton.setActionCommand("setButton");
        setButton.addActionListener(this);
        gridBagConstraints.weighty = 0;
        add(setButton, gridBagConstraints);

        // set up border
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    /**
     * Convert instance to data visualize the instance in chart.
     * @param instance an instance
     * @return convert an instance to data for GUI
     */
    private double[][] formatInstance(List<Double> instance) {
        double[][] data = null;
        if (instance == null) {
            LOGGER.info("The instance is null!");
            return data;
        }

        if (instance.size() == 0) {
            LOGGER.info("The instance is empty!");
            return data;
        }

        data = new double[2][instance.size()];

        for (int i = 0; i < instance.size(); i++) {
            data[0][i] = i;
            data[1][i] = instance.get(i);
        }

        return data;
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
        jFrame = new JFrame("Box Plot Over Probabilities Of All Instances");
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

        double[] data1 = new double[]{0.1, 0.2, 0.1, 0.3};
        double[] data2 = new double[]{0.1, 0.4, 0.2};

//        BoxChartAdapter bc = new BoxChartAdapter("Boxplot1", "Series", "Y", data1, data2);
//        JFrame jFrame = bc.view(600, 400);
//
//        jFrame.setVisible(true);

        List<List<Double>> probsForAllInstances = IOOperation.readProbsFromFile(Config.getFINALPROBSFORALLINSTANCESFILEPATH());

        data1 = Utilities.convertToOneDimensionalDoubleArray(probsForAllInstances.get(1));

        data2 = Utilities.convertToOneDimensionalDoubleArray(probsForAllInstances.get(2));
        BoxChartAdapter bc = new BoxChartAdapter("Boxplot1", "Series", "Y", data1, data2);
        JFrame jFrame = bc.view(600, 400);

        jFrame.setVisible(true);

    }
}
