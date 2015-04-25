package gui;

import Utilities.IOOperation;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.util.logging.Logger;
import java.util.List;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 11/Apr/2015
 * Time: 23:12
 * System Time: 11:12 PM
 */
public class DCDMCMenuGUI {
    private static final Logger LOGGER = Logger.getLogger(DCDMCMenuGUI.class.getName());

    private final DCDMCGUI dcdmcgui;

    public DCDMCMenuGUI(DCDMCGUI dcdmcgui) {
        this.dcdmcgui = dcdmcgui;
    }

    /**
     * Create menu bar
     * @return a object of JMenuBar
     * https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/MenuDemoProject/src/components/MenuDemo.java
     */
    public JMenuBar createMenuBar() {

        JMenuBar menuBar;
        JMenu menu;
        JMenu submenu;
        JMenuItem menuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //---------------------------------------- File Menu ---------------------------------------//
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //Import Config File
        ImageIcon icon = createImageIcon("icons/import.png");
        menuItem = new JMenuItem("Import Config File ...", icon);
        menuItem.setMnemonic(KeyEvent.VK_I);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser

                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(DCDMCMenuGUI.this.dcdmcgui);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Open File: [" + file.getAbsolutePath() + "] successfully.");
                    System.out.println("    ===================================");
                    System.out.println();
                    DCDMCMenuGUI.this.dcdmcgui.importConfigFile(file);


                } else {
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Cancel to open file.");
                    System.out.println("    ===================================");
                    System.out.println();
                }
            }
        });

        // Export Config File
        icon = createImageIcon("icons/export.png");
        menuItem = new JMenuItem("Export Config File ...", icon);
        menuItem.setMnemonic(KeyEvent.VK_E);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create a file chooser

                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(DCDMCMenuGUI.this.dcdmcgui);

                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File file = fc.getSelectedFile();
                    DCDMCMenuGUI.this.dcdmcgui.exportConfigFile(file);
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Save File: [" + file.getAbsolutePath() + "] successfully.");
                    System.out.println("    ===================================");
                    System.out.println();

                } else {
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("        Cancel to save file.");
                    System.out.println("    ===================================");
                    System.out.println();
                }
            }
        });

        menu.addSeparator();

        //a submenu
        icon = createImageIcon("icons/settings.png");
        submenu = new JMenu("Settings...");
        submenu.setMnemonic(KeyEvent.VK_S);
        submenu.setIcon(icon);

        icon = createImageIcon("icons/path.png");
        menuItem = new JMenuItem("Paths...", icon);
        submenu.add(menuItem);
        menu.add(submenu);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DCDMCMenuGUI.this.dcdmcgui.setAllComponentsEnabled(false);
                PathSettingsGUI pathSettingsGUI = new PathSettingsGUI(DCDMCMenuGUI.this.dcdmcgui);
            }
        });

        menu.addSeparator();

        icon = createImageIcon("icons/exit.png");
        menuItem = new JMenuItem("Exit", icon);
        menuItem.setMnemonic(KeyEvent.VK_0);
        menuItem.addActionListener(
                new ActionListener() {
                    /**
                     * Action performed function
                     * @param e event
                     */
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // exit the application when window's close button is clicked
                        System.exit(0);
                    }
                }

        );
        menu.add(menuItem);

        //-------------------------------------- Settings Menu --------------------------------------//
        menu = new JMenu("Edit");
        menuBar.add(menu);
        icon = createImageIcon("icons/reset.png");
        menuItem = new JMenuItem("Reset Configurations", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DCDMCMenuGUI.this.dcdmcgui.setAllComponentsEnabled(false);
                Object[] options = {"Yes, please",
                        "No, thanks"};
                int n = JOptionPane.showOptionDialog(DCDMCMenuGUI.this.dcdmcgui,
                        "Would you like to reset all configurations?",
                        "Caution",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[1]);

                // press yes button
                if (n == 0) {
                    Config.resetConfig(); // reset all parameters
                    DCDMCMenuGUI.this.dcdmcgui.resetAllComponents(); // reset all gui configuration
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("            Reset all configurations successfully.");
                    System.out.println("    ===================================");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("    ===================================");
                    System.out.println("            Cancel to reset all configurations.");
                    System.out.println("    ===================================");
                    System.out.println();
                }

                DCDMCMenuGUI.this.dcdmcgui.setAllComponentsEnabled(true);
            }
        });

        menu.addSeparator();

        icon = createImageIcon("icons/showconfigs.png");
        menuItem = new JMenuItem("Show All Configurations", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllParameterConfigurations(dcdmcgui);
            }
        });


        //-------------------------------------- Analysis Menu --------------------------------------//
        menu = new JMenu("Analysis");

        menuBar.add(menu);
        icon = createImageIcon("icons/similaritytrendline.png");
        menuItem = new JMenuItem("Similarity Trendline", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Double> similarityTrendline = IOOperation.readSimilarityTrendline(Config.getSIMILARITYTRENDLINEFILEPATH());
                SimilarityTrendlineGUI.createAndShowGUI(dcdmcgui, similarityTrendline);
            }
        });

        menuBar.add(menu);
        icon = createImageIcon("icons/totalprobabilitiestrendline.png");
        menuItem = new JMenuItem("Total Probabilities Trendline", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Double> totalProbsTrendline = IOOperation.readSimilarityTrendline(Config.getTOTALPROBABILITIESTRENDLINEFILEPATH());
                TotalProbsTrendlineGUI.createAndShowGUI(dcdmcgui, totalProbsTrendline);
            }
        });

        menu.addSeparator();

        menuBar.add(menu);
        icon = createImageIcon("icons/finalclusterprobabilities.png");
        menuItem = new JMenuItem("Final Cluster Probabilities of Data Boxplot", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<List<Double>> probsForAllInstances = IOOperation.readProbsFromFile(Config.getFINALPROBSFORALLINSTANCESFILEPATH());
                ProbsForAllInstancesGUI.createAndShowGUI(dcdmcgui, probsForAllInstances);
            }
        });

        menu.addSeparator();

        menuBar.add(menu);
        icon = createImageIcon("icons/initialdatadistributiongraph.png");
        menuItem = new JMenuItem("Initial Data Cluster Distribution Graph", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> initialClusterLabels = IOOperation.readClusterLabels(Config.getINITIALCLUSTERSFILEPATH());
                DataClusterDistributionGraphGUI.createAndShowGUI(dcdmcgui, initialClusterLabels);
            }
        });

        menuBar.add(menu);
        icon = createImageIcon("icons/finaldatadistributiongraph.png");
        menuItem = new JMenuItem("Final Data Cluster Distribution Graph", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> finalClusterLabels = IOOperation.readClusterLabels(Config.getFINALCLUSTERSFILEPATH());
                DataClusterDistributionGraphGUI.createAndShowGUI(dcdmcgui, finalClusterLabels);
            }
        });


        //---------------------------------------- Help Menu ----------------------------------------//
        menu = new JMenu("Help");
        menuBar.add(menu);

        icon = createImageIcon("icons/website.png");
        menuItem = new JMenuItem("Software website", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI("https://github.com/wcy1984123/DCDMCS"));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        menu.addSeparator();

        icon = createImageIcon("icons/copyright.png");
        menuItem = new JMenuItem("Software Version & Copyright", icon);
        menu.add(menuItem);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DCDMCMenuGUI.this.dcdmcgui, Config.getVERSIONINFO(), "Software Version & Copyright Information", JOptionPane.WARNING_MESSAGE);
            }
        });

        menu.addSeparator();

        icon = createImageIcon("icons/me.png");
        menuItem = new JMenuItem("About me", icon);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(DCDMCMenuGUI.this.dcdmcgui, "CHIYING WANG \n" +
                        "WPI", "About me", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menu.add(menuItem);

        return menuBar;
    }

    public static void showAllParameterConfigurations(final DCDMCGUI dcdmcgui) {
        final JFrame frame = new JFrame("Distributed Collective Dynamical Modeling & Clustering Configurations");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        JTextPane output = new JTextPane();
        output.setContentType("text/html");
        output.setEditable(false);
        output.setText("\n");
        Config config = new Config(dcdmcgui.getParameters()); // update configurations based on parameters
        output.setText(Config.toFormatAsString());
        output.setCaretPosition(output.getDocument().getLength());
        JScrollPane scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        // Add the button to the content pane.
        JButton jButton = new JButton("Close");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        contentPane.add(jButton, BorderLayout.PAGE_END);

        //Create and set up the content pane.
        frame.setContentPane(contentPane);

        //Display the window.
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int)screenDimension.getWidth() / 2, (int)screenDimension.getWidth() / 2);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Return an iconimage
     * @param path image file path
     * @return an iconimage
     */
    private ImageIcon createImageIcon(String path) {
        String filePath = new File(path).getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon((new ImageIcon(filePath)).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
        return imageIcon;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

    }
}
