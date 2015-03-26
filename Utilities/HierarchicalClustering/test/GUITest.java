package test;

import hierarchicalclustering.Cluster;
import visualization.DendrogramPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Project: DCDMC
 * Package: test
 * Date: 24/Mar/2015
 * Time: 09:46
 * System Time: 9:46 AM
 */

public class GUITest {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 300);
        frame.setLocation(400, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel content = new JPanel();
        DendrogramPanel dp = new DendrogramPanel();

        frame.setContentPane(content);
        content.setBackground(Color.red);
        content.setLayout(new BorderLayout());
        content.add(dp, BorderLayout.CENTER);
        dp.setBackground(Color.WHITE);
        dp.setLineColor(Color.BLACK);
        dp.setScaleValueDecimals(0);
        dp.setScaleValueInterval(1);
        dp.setShowDistances(false);

        Cluster cluster = DendrogramPanel.createSampleCluster();
        dp.setModel(cluster);
        frame.setVisible(true);
    }

}
