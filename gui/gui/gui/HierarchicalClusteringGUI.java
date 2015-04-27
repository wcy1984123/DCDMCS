package gui;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 11/Apr/2015
 * Time: 22:20
 * System Time: 10:20 PM
 */

import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Class for GUI of hierarchical clustering
 */

public class HierarchicalClusteringGUI extends JPanel implements ActionListener {

    private final static Logger LOGGER = Logger.getLogger(HierarchicalClusteringGUI.class.getName());
    private final String averageLinkageStrategy = "AVERAGELINKAGESTRATEGY";
    private final String completeLinkageStrategy = "COMPLETELINKAGESTRATEGY";
    private final String singleLinkageStrategy = "SINGLELINKAGESTRATEGY";
    private final String weightedLinkageStrategy = "WEIGHTEDLINKAGESTRATEGY";
    private final ButtonGroup buttonGroup;
    private static DCDMCGUI dcdmcgui;
    private static JFrame jFrame = null;

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
        buttonGroup = new ButtonGroup();
        buttonGroup.add(averageStrategyButton);
        buttonGroup.add(completeStrategyButton);
        buttonGroup.add(singleStrategyButton);
        buttonGroup.add(weightedStrategyButton);

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
        String hierarchicalLinkageStrategy = Config.getHIERARCHICALLINKAGETYPE();
        if (hierarchicalLinkageStrategy.equals(averageLinkageStrategy)) {
            averageStrategyButton.setSelected(true);
        } else if (hierarchicalLinkageStrategy.equals(completeLinkageStrategy)) {
            completeStrategyButton.setSelected(true);
        } else if (hierarchicalLinkageStrategy.equals(singleLinkageStrategy)) {
            singleStrategyButton.setSelected(true);
        } else if (hierarchicalLinkageStrategy.equals(weightedLinkageStrategy)) {
            weightedStrategyButton.setSelected(true);
        } else {

        }

        //Set button
        JButton setButton = new JButton("Set");
        setButton.setActionCommand("setButton");
        setButton.addActionListener(this);
        add(setButton, BorderLayout.PAGE_END);
    }

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(averageLinkageStrategy)) {
            Config.setHIERARCHICALLINKAGETYPE(averageLinkageStrategy);
        } else if (e.getActionCommand().equals(completeLinkageStrategy)) {
            Config.setHIERARCHICALLINKAGETYPE(completeLinkageStrategy);
        } else if (e.getActionCommand().equals(singleLinkageStrategy)) {
            Config.setHIERARCHICALLINKAGETYPE(singleLinkageStrategy);
        } else if (e.getActionCommand().equals(weightedLinkageStrategy)) {
            Config.setHIERARCHICALLINKAGETYPE(weightedLinkageStrategy);
        } else if (e.getActionCommand().equals("setButton")) {

            // simulate user click on the selected button in the given button group
            clickSelectedButton(buttonGroup);

            System.out.println();
            System.out.println("    ===================================");
            System.out.println("        Linkage Strategy: " + Config.getHIERARCHICALLINKAGETYPE());
            System.out.println("    ===================================");
            System.out.println();
            this.dcdmcgui.setAllComponentsEnabled(true);
            jFrame.dispose(); // close this frame
        } else {
            LOGGER.info("No corresponding action command!");
        }
    }

    /**
     * Simulate user click on the selected button in the given button group
     * @param buttonGroup button group
     */
    public void clickSelectedButton(ButtonGroup buttonGroup) {
        Enumeration<AbstractButton> buttons = buttonGroup.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                button.doClick(); // simulate user click
                break;
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI(final DCDMCGUI dcdmcgui) {
        //Create and set up the window
        jFrame = new JFrame("Hierarchical Linkage Strategy Settings");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new HierarchicalClusteringGUI();

        HierarchicalClusteringGUI.dcdmcgui = dcdmcgui;

        newContentPane.setOpaque(true); //content panes must be opaque
        jFrame.setContentPane(newContentPane);

        //Add window listener
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dcdmcgui.setAllComponentsEnabled(true);
            }
        });

        //Display the window.
        jFrame.pack();
        jFrame.setPreferredSize(jFrame.getPreferredSize());
        jFrame.setMaximumSize(jFrame.getPreferredSize());
        jFrame.setMinimumSize(jFrame.getPreferredSize());
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

}
