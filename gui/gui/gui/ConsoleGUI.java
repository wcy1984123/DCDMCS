package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * Project: DCDMC
 * Package: gui
 * Date: 29/Mar/2015
 * Time: 13:42
 * System Time: 1:42 PM
 */
public class ConsoleGUI extends JFrame implements PropertyChangeListener {
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JProgressBar taskComplettionProgressBar;
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
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            setCursor(null); //turn off the wait cursor
            consoleTextArea.append("Done!\n");
        }
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            taskComplettionProgressBar.setValue(progress);
            consoleTextArea.append(String.format(
                    "Completed %d%% of task.\n", task.getProgress()));
        }
    }

    /**
     * class constructor
     * @param taskName task name
     * @param progressLength the maximum of progress length
     */
    public ConsoleGUI(String taskName, int progressLength) {
        super("Console Monitor");
        consolePanel.setOpaque(true);
        setContentPane(consolePanel);
        pack();


        // initialize components
        initComponents();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // show gui
    }

    /**
     * Initialize components
     */
    private void initComponents() {
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();
    }



    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        ConsoleGUI test = new ConsoleGUI("Test Progress Bar", 100);
    }
}
