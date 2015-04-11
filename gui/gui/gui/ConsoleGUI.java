package gui;

import Utilities.IOOperation;

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
                JMenuItem jMenuItem = new JMenuItem("Clear");
                jPopupMenu.add(jMenuItem);
                jMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ConsoleGUI.this.consoleTextArea.setText(""); // clear text field
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
