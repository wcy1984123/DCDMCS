package gui;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 11/Apr/2015
 * Time: 22:28
 * System Time: 10:28 PM
 */

import initializer.dtws.DeviatedDTW;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

/**
 * Class for GUI of deviated dynamic time warping
 */

public class DeviatedDTWGUI extends JPanel implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(DeviatedDTW.class.getName());

    private final String gwDTW = "GLOBALWEIGHTEDDTW";
    private final String sdDTW = "STEPWISEDEVIATEDDTW";
    private final DCDMCGUI dcdmcgui;
    private JFrame jFrame = null;

    public DeviatedDTWGUI(DCDMCGUI dcdmcgui) {
        super(new BorderLayout());

        this.dcdmcgui = dcdmcgui;

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
        if (Config.getDEVIATEDDTWTYPE().equals(gwDTW)) {
            gwDTWButton.setSelected(true);
        } else {
            sdDTWButton.setSelected(true);
        }
    }

    /** Listens to the radio buttons. */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(gwDTW)) {
           Config.setDEVIATEDDTWTYPE(gwDTW);
            // update current configuration
            Config.setDEVIATEDDTWTYPE(gwDTW);
            Config.setDTWTYPE(gwDTW);
        } else if (e.getActionCommand().equals(sdDTW)){
            Config.setDEVIATEDDTWTYPE(sdDTW);
            // update current configuration
            Config.setDEVIATEDDTWTYPE(sdDTW);
            Config.setDTWTYPE(sdDTW);
        } else if (e.getActionCommand().equals("setButton")) {
            System.out.println("\n===================================");
            System.out.println("        Deviated DTW: " + Config.getDEVIATEDDTWTYPE());
            System.out.println("===================================\n");
            this.dcdmcgui.setAllComponentsEnabled(true);
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
    public void createAndShowGUI() {
        //Create and set up the window.
        jFrame = new JFrame("Deviated Dynamic Time Warping Settings");
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new DeviatedDTWGUI(this.dcdmcgui);

        //Set button
        JButton setButton = new JButton("Set");
        setButton.setActionCommand("setButton");
        setButton.addActionListener(this);
        newContentPane.add(setButton, BorderLayout.PAGE_END);

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

