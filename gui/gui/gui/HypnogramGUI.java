package gui;

import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 11/Apr/2015
 * Time: 21:58
 * System Time: 9:58 PM
 */

/**
 * GUI for hypnogram radio button
 */
public class HypnogramGUI extends JPanel implements ActionListener {

    private static final Logger LOGGER = Logger.getLogger(HypnogramGUI.class.getName());

    private final String originalDataset = "ORIGINALDATASET";
    private final String wsDataset = "WAKESLEEPDATASET";
    private final String wnrDataset = "WAKENREMREMDATASET";
    private final String wdlDataset = "WAKEDEEPLIGHTDATASET";
    private final ButtonGroup buttonGroup;
    private static DCDMCGUI dcdmcgui;
    private static JFrame jFrame = null;

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
        buttonGroup = new ButtonGroup();
        buttonGroup.add(originalButton);
        buttonGroup.add(wsButton);
        buttonGroup.add(wnrButton);
        buttonGroup.add(wdlButton);

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

        // set up selected radio button according to existing hypnogram format
        String hypnogramFormat = Config.getDATAFORMAT() == 0 ? "" : String.valueOf(Config.getDATAFORMAT());
        if (hypnogramFormat.equals("5")) {
            originalButton.setSelected(true);
        } else if (hypnogramFormat.equals("2")) {
            wsButton.setSelected(true);
        } else if (hypnogramFormat.equals("3")) {
            wnrButton.setSelected(true);
        } else if (hypnogramFormat.equals("4")) {
            wdlButton.setSelected(true);
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
        if (e.getActionCommand().equals(originalDataset)) {
            Config.setDATAFORMAT(5);
            dcdmcgui.getStateNumberTextField().setText("5");
        } else if (e.getActionCommand().equals(wsDataset)) {
            Config.setDATAFORMAT(2);
            dcdmcgui.getStateNumberTextField().setText("2");
        } else if (e.getActionCommand().equals(wnrDataset)) {
            Config.setDATAFORMAT(3);
            dcdmcgui.getStateNumberTextField().setText("3");
        } else if (e.getActionCommand().equals(wdlDataset)) {
            Config.setDATAFORMAT(4);
            dcdmcgui.getStateNumberTextField().setText("3");
        } else if (e.getActionCommand().equals("setButton")) {

            // simulate user click on the selected button in the given button group
            clickSelectedButton(buttonGroup);

            System.out.println();
            System.out.println("    ===================================");
            System.out.println("        Hypnogram Format: " + (Config.getDATAFORMAT() == 0 ? "" : Config.getDATAFORMAT()));
            System.out.println("    ===================================");
            System.out.println();
            dcdmcgui.setAllComponentsEnabled(true);
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
        //Create and set up the window.
        jFrame = new JFrame("Hypnogram Dataset Settings");
        jFrame.setLayout(new GridBagLayout());
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new HypnogramGUI();

        HypnogramGUI.dcdmcgui = dcdmcgui;

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
