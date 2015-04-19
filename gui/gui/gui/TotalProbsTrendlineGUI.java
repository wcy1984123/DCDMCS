package gui;

import Utilities.Utilities;
import adapters.XYLineChartApdater;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import starter.Config;
import umontreal.iro.lecuyer.charts.XYListSeriesCollection;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 19/Apr/2015
 * Time: 15:22
 * System Time: 3:22 PM
 */


/**
 * Class for total probabilities distribution during CDMC iterative process
 */
public class TotalProbsTrendlineGUI extends JPanel implements ActionListener {
    private final static Logger LOGGER = Logger.getLogger(TotalProbsTrendlineGUI.class.getName());
    private static List<Double> totalProbsTrendline;
    private GridBagConstraints gridBagConstraints;
    private static JFrame jFrame = null;

    /**
     * Class constructor
     */
    public TotalProbsTrendlineGUI() {

        // set up layout
        super(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;

        double[][] data = formatInstance(totalProbsTrendline);
        XYLineChartApdater chart = new XYLineChartApdater("CDMC Total Probabilities Trendline [ " + Config.getDYNAMICMODELTYPE() + " ]", "Iteration No.", "Total Probabilities", data);
        XYListSeriesCollection collec = chart.getSeriesCollection();
        collec.setColor(0, Color.BLUE);
        collec.setMarksType(0, "*");
        collec.setPlotStyle(0, "sharp plot");

        // change font and its size
        JFreeChart jc = chart.getChart();
        TextTitle tt = jc.getTitle();
        tt.setFont(new FontUIResource("DensityChartSmallFont", Font.ITALIC, 12)); // set up font
        chart.getXAxis().setLabels(1); // set up Y axis tick
        double tick = (data[1][0] - data[1][totalProbsTrendline.size() - 1]) / totalProbsTrendline.size();
        chart.getYAxis().setLabels(2 * tick);

        // add view to panel
        JFrame jFrame = chart.view(300, 400);
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

        // normalize data by max format
        double[] formatedData = Utilities.normalizeArrayByMax(instance);

        for (int i = 0; i < formatedData.length; i++) {
            data[0][i] = i;
            data[1][i] = formatedData[i];
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
     * @param totalProbsTrendline total probabilities trendlien data
     */
    public static void createAndShowGUI(final DCDMCGUI dcdmcgui, final List<Double> totalProbsTrendline) {
        // Create and set up the window
        jFrame = new JFrame("Total Probabilities Trendline [ " + Config.getDYNAMICMODELTYPE() + " ]");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // save cluster labels
        TotalProbsTrendlineGUI.totalProbsTrendline = totalProbsTrendline;

        // Create and set up the content pane.
        JComponent newContentPane = new TotalProbsTrendlineGUI();

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

    }

}