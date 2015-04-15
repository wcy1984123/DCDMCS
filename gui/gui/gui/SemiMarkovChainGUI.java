package gui;

import starter.CONSTANTS;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

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
                    System.out.println("        Probability Density View: " + Config.isPROBABILITYDENSITYVIEW().toString().toUpperCase());

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
                    System.out.println("        Cumulative Distribution View: " + Config.isCUMULATIVEDISTRIBUTIONVIEW().toString().toUpperCase());
                }
            }
        });

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dcdmcgui.setAllComponentsEnabled(true);
                ProgressBarDemo2 progressBarDemo2 = new ProgressBarDemo2();
                progressBarDemo2.createAndShowGUI();
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
                    System.out.println("            Reset view configurations in Semi-Markov Chain GUI components successfully.");
                } else {
                    System.out.println("            Cancel to reset view configurations in Semi-Markov Chain GUI components.");
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

    /**
     * Progress bar class
     */
    private class ProgressBarDemo2 extends JPanel implements ActionListener, PropertyChangeListener {

        private JProgressBar progressBar;
        private JButton startButton;
        private JTextArea taskOutput;
        private Task task;

        class Task extends SwingWorker<Void, Void> {
            /*
             * Main task. Executed in background thread.
             */
            @Override
            public Void doInBackground() {
                Random random = new Random();
                int progress = 0;
                //Initialize progress property.
                setProgress(0);
                //Sleep for at least one second to simulate "startup".
                try {
                    Thread.sleep(1000 + random.nextInt(2000));
                } catch (InterruptedException ignore) {}
                while (progress < 100) {
                    //Sleep for up to one second.
                    try {
                        Thread.sleep(random.nextInt(1000));
                    } catch (InterruptedException ignore) {}
                    //Make random progress.
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
                return null;
            }

            /*
             * Executed in event dispatch thread
             */
            public void done() {
                Toolkit.getDefaultToolkit().beep();
                startButton.setEnabled(true);
                taskOutput.append("Done!\n");
            }
        }

        ProgressBarDemo2() {
            super(new BorderLayout());

            //Create the demo's UI.
            startButton = new JButton("Start");
            startButton.setActionCommand("start");
            startButton.addActionListener(this);

            progressBar = new JProgressBar(0, 100);
            progressBar.setValue(0);

            //Call setStringPainted now so that the progress bar height
            //stays the same whether or not the string is shown.
            progressBar.setStringPainted(true);

            taskOutput = new JTextArea(5, 20);
            taskOutput.setMargin(new Insets(5,5,5,5));
            taskOutput.setEditable(false);

            JPanel panel = new JPanel();
            panel.add(startButton);
            panel.add(progressBar);

            add(panel, BorderLayout.PAGE_START);
            add(new JScrollPane(taskOutput), BorderLayout.CENTER);
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        }

        /**
         * Invoked when the user presses the start button.
         */
        public void actionPerformed(ActionEvent evt) {
            progressBar.setIndeterminate(true);
            startButton.setEnabled(false);
            //Instances of javax.swing.SwingWorker are not reusuable, so
            //we create new instances as needed.
            task = new Task();
            task.addPropertyChangeListener(this);
            task.execute();
        }

        /**
         * Invoked when task's progress property changes.
         */
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                progressBar.setIndeterminate(false);
                progressBar.setValue(progress);
                taskOutput.append(String.format(
                        "Completed %d%% of task.\n", progress));
            }
        }

        /**
         * Create the GUI and show it. As with all GUI code, this must run
         * on the event-dispatching thread.
         */
        private void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("ProgressBarDemo2");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            //Create and set up the content pane.
            JComponent newContentPane = new ProgressBarDemo2();
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);
            frame.setLocationRelativeTo(null);
            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

    }
}
