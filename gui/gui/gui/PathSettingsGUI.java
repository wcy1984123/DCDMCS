package gui;

import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import Utilities.Utilities;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 10/Apr/2015
 * Time: 13:38
 * System Time: 1:38 PM
 */
public class PathSettingsGUI extends JFrame{
    private JTextField datasetPathTextField;
    private JTextField configPathTextField;
    private JTextField initialClusterFilePathTextField;
    private JTextField finalCluserFilePathTextField;
    private JTextField distanceMatrixFilePathTextField;
    private JPanel inputPathSettingsPanel;
    private JPanel ouputPathSettingsPanel;
    private JPanel pathSettingsPanel;
    private JButton datasetPathButton;
    private JButton configPathButton;
    private JButton initialClusterFilePathButton;
    private JButton finalCluserFilePathButton;
    private JButton distanceMatrixFilePathButton;
    private JButton setButton;
    private JButton resetButton;

    /**
     * Class consructor
     */
    PathSettingsGUI(final DCDMCGUI parentGUI) {
        super("Paths Settings");

        int maxLength = Utilities.maxOfArray(new int[]{Config.getDATASETPATH().length(),
                                                       Config.getCONFIGPATH().length(),
                                                       Config.getINITIALCLUSTERSFILEPATH().length(),
                                                        Config.getFINALCLUSTERSFILEPATH().length(),
                                                        Config.getDISTANCEMATRIXFILEPATH().length()});

        datasetPathTextField.setText(Config.getDATASETPATH());
        datasetPathTextField.setMinimumSize(new Dimension(maxLength * 6, datasetPathTextField.getHeight()));
        configPathTextField.setText(Config.getCONFIGPATH());
        initialClusterFilePathTextField.setText(Config.getINITIALCLUSTERSFILEPATH());
        finalCluserFilePathTextField.setText(Config.getFINALCLUSTERSFILEPATH());
        distanceMatrixFilePathTextField.setText(Config.getDISTANCEMATRIXFILEPATH());

        setContentPane(pathSettingsPanel);
        pack();

        initComponents();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null); // center the current frame
        setVisible(true); // show gui

        datasetPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathSettingsGUI.this.actionPerformed(e, PathSettingsGUI.this.datasetPathTextField);
            }
        });

        configPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathSettingsGUI.this.actionPerformed(e, PathSettingsGUI.this.configPathTextField);
            }
        });

        initialClusterFilePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathSettingsGUI.this.actionPerformed(e, PathSettingsGUI.this.initialClusterFilePathTextField);
            }
        });

        finalCluserFilePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathSettingsGUI.this.actionPerformed(e, PathSettingsGUI.this.finalCluserFilePathTextField);
            }
        });

        distanceMatrixFilePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PathSettingsGUI.this.actionPerformed(e, PathSettingsGUI.this.distanceMatrixFilePathTextField);
            }
        });

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // update the parameters
                Config.setDATASETPATH(datasetPathTextField.getText());
                Config.setCONFIGPATH(configPathTextField.getText());
                Config.setINITIALCLUSTERSFILEPATH(initialClusterFilePathTextField.getText());
                Config.setFINALCLUSTERSFILEPATH(finalCluserFilePathTextField.getText());
                Config.setDISTANCEMATRIXFILEPATH(distanceMatrixFilePathTextField.getText());

                // close the current window
                parentGUI.setAllComponentsEnabled(true);
                dispose();
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                datasetPathTextField.setText(Config.getDATASETPATH());
                configPathTextField.setText(Config.getCONFIGPATH());
                initialClusterFilePathTextField.setText(Config.getINITIALCLUSTERSFILEPATH());
                finalCluserFilePathTextField.setText(Config.getFINALCLUSTERSFILEPATH());
                distanceMatrixFilePathTextField.setText(Config.getDISTANCEMATRIXFILEPATH());
            }
        });

        //Add window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parentGUI.setAllComponentsEnabled(true);
            }
        });

    }

    /**
     * Implement a event listener
     * @param e action event
     * @param jTextField a textfield
     */
    private void actionPerformed(ActionEvent e, JTextField jTextField) {
        //Create a file chooser

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showDialog(jTextField, "OK");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            jTextField.setText(file.getAbsolutePath());
            System.out.println("        Change [ " + jTextField.getName() + " ] to: [" + file.getAbsolutePath() + "] successfully.");

        } else {
            System.out.println("        Cancel to change file path.");
        }
    }

    /**
     * Initialize components
     */
    private void initComponents() {

    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        PathSettingsGUI pathSettingsGUI = new PathSettingsGUI(new DCDMCGUI());
    }

}
