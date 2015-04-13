package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(weibullDensityDistributionRadioButton);
        buttonGroup.add(exponentialDensityDistributionRadioButton);

        probabilityDensityViewCheckBox.setEnabled(false);
        probabilityDensityViewCheckBox.setSelected(true);
        weibullDensityDistributionRadioButton.setSelected(true);

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dcdmcgui.setAllComponentsEnabled(true);
                dispose();
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
