package gui;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 18/Apr/2015
 * Time: 15:28
 * System Time: 3:28 PM
 */

import Utilities.IOOperation;
import adapters.XYLineChartApdater;
import dao.DATATYPE;
import dao.DaoFactory;
import dao.IDAO;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import starter.Config;
import umontreal.iro.lecuyer.charts.XYListSeriesCollection;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;
import java.util.List;

/**
 * Class for visualizing data cluster distributions
 */
public class DataClusterDistributionGraphGUI extends JPanel implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(DataClusterDistributionGraphGUI.class.getName());
    private List<Integer> clusterLabels;
    private IDAO idao;
    private List<List<Double>> instances;
    private GridBagConstraints gridBagConstraints;
    private static JFrame jFrame = null;

    /**
     * Class constructor
     */
    public DataClusterDistributionGraphGUI() {

        // set up layout
        super(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        // get final cluster labels
        this.clusterLabels = IOOperation.readClusterLabels(Config.getFINALCLUSTERSFILEPATH());

        // get data source
        this.idao = DaoFactory.getInstance().createData(DATATYPE.valueOf(Config.getDATASETTYPE()));
        this.instances = this.idao.getDataSourceAsLists(Config.getDATASETPATH(), String.valueOf(Config.getSTATENUM()));

        // color array
        Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.PINK, Color.YELLOW};


        // get data distribution for each cluster label
        int clusterNum = Config.getCLUSTERNUM();
        for (int i = 0; i < clusterNum; i++) {
            JFrame jFrame = createOneClusterDistributionFrame(i, colors[i % colors.length]);
            Component jComponent = jFrame.getComponent(0);
            add(jComponent, gridBagConstraints);
            gridBagConstraints.gridy = gridBagConstraints.gridy + jComponent.getBounds().y;
        }

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
     * Create one cluster distribution
     * @return a frame of one cluster distribution
     */
    private JFrame createOneClusterDistributionFrame(int modelSeq, Color color) {

        double[][] origianl = new double[2][1]; // a null point
        origianl[0][0] = 0;
        origianl[1][0] = 0;
        XYLineChartApdater chart = new XYLineChartApdater("", "Time", "Value", origianl);
        XYListSeriesCollection collec = chart.getSeriesCollection();
        collec.setColor(0, color);

        int count = 0; // actual number of instances in a given cluster
        int maximumLength = 0;
        for (int i = 0; i < instances.size(); i++) {
            maximumLength = Math.max(maximumLength, instances.get(i).size());
            if (clusterLabels.get(i) == modelSeq) {

                // get data instance
                double[][] data = formatInstance(instances.get(i));
                chart.add(data);

                collec = chart.getSeriesCollection();
                collec.setColor(count++, color);
            }
        }

        // set up title
        chart.setTitle("Cluster [ " + (modelSeq + 1) + " ] - " + Config.getSTATENUM() + " States {" + count + " Instances}");

        // set x and y axis range and ticks
        chart.setManualRange(new double[]{0, maximumLength, 1, Config.getSTATENUM()});
        chart.getYAxis().setLabels(1);

        // change font and its size
        JFreeChart jc = chart.getChart();
        TextTitle tt = jc.getTitle();
        tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font

        return chart.view(300, 400);
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
    public static void createAndShowGUI(final DCDMCGUI dcdmcgui) {
        //Create and set up the window
        jFrame = new JFrame("Data Cluster Distribution Chart");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new DataClusterDistributionGraphGUI();

        newContentPane.setOpaque(true); //content panes must be opaque
        jFrame.setContentPane(newContentPane);

        //Display the window.
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }

}