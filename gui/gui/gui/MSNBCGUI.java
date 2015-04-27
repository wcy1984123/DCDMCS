package gui;

import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 26/Apr/2015
 * Time: 17:28
 * System Time: 5:28 PM
 */

/**
 * GUI for MSNBC dataset radio button
 */
public class MSNBCGUI extends JPanel implements ActionListener{
    private static final Logger LOGGER = Logger.getLogger(MSNBCGUI.class.getName());

    private final String originalDataset = "ORIGINALDATASET";
    private final String lessThan11Dataset = "LESSTHAN11DATASET";
    private final String lessThan13Dataset = "LESSTHAN13DATASET";
    private final String lessThan14Dataset = "LESSTHAN14DATASET";
    private final String higherThan10LessThan500Dataset = "HIGHERTHAN10LESSTHAN500DATASET";
    private final String higherThan12LessThan500Dataset = "HIGHERTHAN12LESSTHAN500DATASET";
    private final String higherThan13LessThan500Dataset = "HIGHERTHAN13LESSTHAN500DATASET";
    private final ButtonGroup buttonGroup;
    private static DCDMCGUI dcdmcgui;
    private static JFrame jFrame = null;

    public MSNBCGUI() {
        super(new BorderLayout());

        //Create the radio buttons.
        JRadioButton originalButton = new JRadioButton("Original Dataset");
        originalButton.setMnemonic(KeyEvent.VK_O);
        originalButton.setActionCommand(originalDataset);
        originalButton.setToolTipText("It consists of sequences of all lengths.");

        JRadioButton lessThan11Button = new JRadioButton("Short Visiting Dataset (Less Than 11)");
        lessThan11Button.setMnemonic(KeyEvent.VK_S);
        lessThan11Button.setActionCommand(lessThan11Dataset);
        lessThan11Button.setToolTipText("It consists of sequences of less than 11.");

        JRadioButton lessThan13Button = new JRadioButton("Short Visiting Dataset (Less Than 13)");
        lessThan13Button.setMnemonic(KeyEvent.VK_N);
        lessThan13Button.setActionCommand(lessThan13Dataset);
        lessThan13Button.setToolTipText("It consists of sequences of less than 13.");

        JRadioButton lessThan14Button = new JRadioButton("Short Visiting Dataset (Less Than 14)");
        lessThan14Button.setMnemonic(KeyEvent.VK_R);
        lessThan14Button.setActionCommand(lessThan14Dataset);
        lessThan14Button.setToolTipText("It consists of sequences of less than 14.");

        JRadioButton higherThan10LessThan500Button = new JRadioButton("Long Visiting Dataset (Higher Than 10 & Less Than 500)");
        higherThan10LessThan500Button.setMnemonic(KeyEvent.VK_L);
        higherThan10LessThan500Button.setActionCommand(higherThan10LessThan500Dataset);
        higherThan10LessThan500Button.setToolTipText("It consists of sequences of higher than 10 and less than 500.");

        JRadioButton higherThan12LessThan500Button = new JRadioButton("Long Visiting Dataset (Higher Than 12 & Less Than 500)");
        higherThan12LessThan500Button.setMnemonic(KeyEvent.VK_O);
        higherThan12LessThan500Button.setActionCommand(higherThan12LessThan500Dataset);
        higherThan12LessThan500Button.setToolTipText("It consists of sequences of higher than 12 and less than 500.");

        JRadioButton higherThan13LessThan500Button = new JRadioButton("Long Visiting Dataset (Higher Than 13 & Less Than 500)");
        higherThan13LessThan500Button.setMnemonic(KeyEvent.VK_P);
        higherThan13LessThan500Button.setActionCommand(higherThan13LessThan500Dataset);
        higherThan13LessThan500Button.setToolTipText("It consists of sequences of higher than 13 and less than 500.");

        //Group the radio buttons.
        buttonGroup = new ButtonGroup();
        buttonGroup.add(originalButton);
        buttonGroup.add(lessThan11Button);
        buttonGroup.add(lessThan13Button);
        buttonGroup.add(lessThan14Button);
        buttonGroup.add(higherThan10LessThan500Button);
        buttonGroup.add(higherThan12LessThan500Button);
        buttonGroup.add(higherThan13LessThan500Button);

        //Register a listener for the radio buttons.
        originalButton.addActionListener(this);
        lessThan11Button.addActionListener(this);
        lessThan13Button.addActionListener(this);
        lessThan14Button.addActionListener(this);
        higherThan10LessThan500Button.addActionListener(this);
        higherThan12LessThan500Button.addActionListener(this);
        higherThan13LessThan500Button.addActionListener(this);

        //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(originalButton);
        radioPanel.add(lessThan11Button);
        radioPanel.add(lessThan13Button);
        radioPanel.add(lessThan14Button);
        radioPanel.add(higherThan10LessThan500Button);
        radioPanel.add(higherThan12LessThan500Button);
        radioPanel.add(higherThan13LessThan500Button);

        add(radioPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // set up selected radio button according to existing deviatedDTW type value
        String msnbcFormat = Config.getDATAFORMAT() == 0 ? "" : String.valueOf(Config.getDATAFORMAT());
        if (msnbcFormat.equals("1")) {
            originalButton.setSelected(true);
        } else if (msnbcFormat.equals("2")) {
            lessThan11Button.setSelected(true);
        } else if (msnbcFormat.equals("3")) {
            lessThan13Button.setSelected(true);
        } else if (msnbcFormat.equals("4")) {
            lessThan14Button.setSelected(true);
        } else if (msnbcFormat.equals("5")) {
            higherThan10LessThan500Button.setSelected(true);
        } else if (msnbcFormat.equals("6")) {
            higherThan12LessThan500Button.setSelected(true);
        } else if (msnbcFormat.equals("7")) {
            higherThan13LessThan500Button.setSelected(true);
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
            Config.setDATAFORMAT(1);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcData.csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(lessThan11Dataset)) {
            Config.setDATAFORMAT(2);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(lessthan11).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(lessThan13Dataset)) {
            Config.setDATAFORMAT(3);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(lessthan13).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(lessThan14Dataset)) {
            Config.setDATAFORMAT(4);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(lessthan14).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(higherThan10LessThan500Dataset)) {
            Config.setDATAFORMAT(5);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(higherthan10lessthan500).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(higherThan12LessThan500Dataset)) {
            Config.setDATAFORMAT(6);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(higherthan12lessthan500).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals(higherThan13LessThan500Dataset)) {
            Config.setDATAFORMAT(7);
            Config.setWEBUSERNAVIGATIONBEHAVIORDATASETFILEPATH(new File("dataset" + File.separator + "msnbcDataRemoveOutliers(higherthan13lessthan500).csv").getAbsolutePath());
            dcdmcgui.getStateNumberTextField().setText("17");
        } else if (e.getActionCommand().equals("setButton")) {

            // simulate user click on the selected button in the given button group
            clickSelectedButton(buttonGroup);

            System.out.println();
            System.out.println("    ===================================");
            System.out.println("        MSNBC Format: " + (Config.getDATAFORMAT() == 0 ? "" : Config.getDATAFORMAT()));
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
        jFrame = new JFrame("Web User Navigation Behavior Dataset Settings");
        jFrame.setLayout(new GridBagLayout());
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new MSNBCGUI();

        MSNBCGUI.dcdmcgui = dcdmcgui;

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
