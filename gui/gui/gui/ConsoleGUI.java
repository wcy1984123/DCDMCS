package gui;

import Utilities.IOOperation;
import Utilities.Utilities;
import starter.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 30/Mar/2015
 * Time: 15:42
 * System Time: 3:42 PM
 */
public class ConsoleGUI extends JFrame{
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JScrollPane consoleScroll;

    /**
     * class constructor
     */
    public ConsoleGUI() {
        super("Console");
        setContentPane(consolePanel);
        pack();

        consoleTextArea.setBackground(Color.BLACK); // set background black
        consoleTextArea.setForeground(Color.WHITE); // set font while

        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenDimension.width / 4, 2 * screenDimension.height / 3);
        setVisible(true); // show gui
        consoleTextArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPopupMenu jPopupMenu = new JPopupMenu();

                // clear console info
                ImageIcon icon = Utilities.createImageIcon("icons/clearconsole.png");
                JMenuItem jMenuItem = new JMenuItem("Clear", icon);
                jPopupMenu.add(jMenuItem);
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ConsoleGUI.this.consoleTextArea.setText(""); // clear text field
                    }
                });

                jPopupMenu.addSeparator();

                // save console info into file
                icon = Utilities.createImageIcon("icons/saveconsole.png");
                jMenuItem = new JMenuItem("Save", icon);
                jPopupMenu.add(jMenuItem);
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //get text in the text area
                        String text = ConsoleGUI.this.consoleTextArea.getText();
                        if (text != null) {
                            IOOperation.writeFile(text, Config.getSAVECONSOLETODISKFILEPATH());
                            System.out.println();
                            System.out.println("===================================");
                            System.out.println("        Save console successfully!");
                            System.out.println("===================================");
                            System.out.println();
                        } else {
                            System.out.println();
                            System.out.println("===================================");
                            System.out.println("        Fail to save console!");
                            System.out.println("===================================");
                            System.out.println();
                        }
                    }
                });

                jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    /**
     * Initialize components
     */
    private void initComponents() {

    }

    /**
     * Getter of the text area
     * @return
     */
    public JTextArea getConsoleTextArea() {
        return consoleTextArea;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        ConsoleGUI test = new ConsoleGUI();
        System.out.println("Test console GUI Before!");
        try {
            IOOperation.console(test.getConsoleTextArea());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Test console GUI After!");
    }
}
