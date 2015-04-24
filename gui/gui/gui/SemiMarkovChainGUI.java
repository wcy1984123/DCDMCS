package gui;

import starter.CONSTANTS;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 13/Apr/2015
 * Time: 08:48
 * System Time: 8:48 AM
 */
public class SemiMarkovChainGUI extends JFrame{
    private JTabbedPane generalTabbedPane;
    private JButton setButton;
    private JPanel semiMarkovChainJPanel;
    private JPanel curveFittingFunctionsJPanel;
    private JPanel viewOptionsJPanel;
    private JCheckBox probabilityDensityViewCheckBox;
    private JCheckBox cumulativeDistributionViewCheckBox;
    private JPanel viewOptionsSubPanel;
    private JPanel curveFittingFunctionsSubPanel;
    private JRadioButton weibullDensityDistributionRadioButton;
    private JRadioButton exponentialDensityDistributionRadioButton;
    private JButton resetButton;

    public SemiMarkovChainGUI(final DCDMCGUI dcdmcgui) {

        setContentPane(semiMarkovChainJPanel);

        probabilityDensityViewCheckBox.setActionCommand("");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(weibullDensityDistributionRadioButton);
        buttonGroup.add(exponentialDensityDistributionRadioButton);

        // set up selected checkbox according to existing selection
        final Boolean probabilityDensityView = Config.isPROBABILITYDENSITYVIEW();
        probabilityDensityViewCheckBox.setEnabled(false);
        if (probabilityDensityView == true) {
            probabilityDensityViewCheckBox.setSelected(true);
        } else {
            probabilityDensityViewCheckBox.setSelected(false);
        }

        Boolean cumulativeDistributionView = Config.isCUMULATIVEDISTRIBUTIONVIEW();
        if (cumulativeDistributionView == true) {
            cumulativeDistributionViewCheckBox.setSelected(true);
        } else {
            cumulativeDistributionViewCheckBox.setSelected(false);
        }

        weibullDensityDistributionRadioButton.setSelected(true);

        // listen for the state change and then modify configuration values
        probabilityDensityViewCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItemSelectable() == probabilityDensityViewCheckBox) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Config.setPROBABILITYDENSITYVIEW(true);
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        Config.setPROBABILITYDENSITYVIEW(false);
                    }
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Probability Density View: " + Config.isPROBABILITYDENSITYVIEW().toString().toUpperCase());
                    System.out.println("    ===================================");
                    System.out.println();
                }
            }
        });

        cumulativeDistributionViewCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getItemSelectable() == cumulativeDistributionViewCheckBox) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        Config.setCUMULATIVEDISTRIBUTIONVIEW(true);
                    } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                        Config.setCUMULATIVEDISTRIBUTIONVIEW(false);
                    }
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Cumulative Distribution View: " + Config.isCUMULATIVEDISTRIBUTIONVIEW().toString().toUpperCase());
                    System.out.println("    ===================================");
                    System.out.println();
                }
            }
        });

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dcdmcgui.setAllComponentsEnabled(true);
                dispose();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Yes, please",
                        "No, thanks"};
                int n = JOptionPane.showOptionDialog(SemiMarkovChainGUI.this,
                        "Would you like to reset current configurations?",
                        "Caution",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);

                // press yes button
                if (n == 0) {
                    Config.setPROBABILITYDENSITYVIEW(CONSTANTS.PROBABILITYDENSITYVIEW);
                    Config.setCUMULATIVEDISTRIBUTIONVIEW(CONSTANTS.CUMULATIVEDISTRIBUTIONVIEW);
                    probabilityDensityViewCheckBox.setSelected(Config.isPROBABILITYDENSITYVIEW());
                    cumulativeDistributionViewCheckBox.setSelected(Config.isCUMULATIVEDISTRIBUTIONVIEW());
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("            Reset view configurations in Semi-Markov Chain GUI components successfully.");
                    System.out.println("    ===================================");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("            Cancel to reset view configurations in Semi-Markov Chain GUI components.");
                    System.out.println("    ===================================");
                    System.out.println();
                }
            }
        });

        //Add window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                dcdmcgui.setAllComponentsEnabled(true);
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(300, 200));
        setMaximumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

}
