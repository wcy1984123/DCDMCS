package gui;

import javax.swing.*;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 29/Mar/2015
 * Time: 13:42
 * System Time: 1:42 PM
 */
public class ConsoleGUI extends JFrame {
    private JTextArea consoleTextArea;
    private JPanel consolePanel;

    public ConsoleGUI() {
        super("Console Monitor");
        setContentPane(consolePanel);
        pack();


        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // show gui
    }

    private void initComponents() {

    }
}
