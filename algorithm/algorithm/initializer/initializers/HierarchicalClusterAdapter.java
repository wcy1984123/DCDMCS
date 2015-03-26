package initializer.initializers;

import Utilities.Utilities;
import hierarchicalclustering.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 24/Mar/2015
 * Time: 12:05
 * System Time: 12:05 PM
 */

/**
 * Hierarchical cluster adapter works for mapping hierarchical clustering into cluster assignment
 */
public class HierarchicalClusterAdapter {

    private static final Logger LOGGER = Logger.getLogger(HierarchicalClusterAdapter.class.getName());
    private double[][] distances; // distance matrix
    private LinkageStrategy linkageStrategy;

    /**
     * class constructor
     * @param distances distance matrix of instances
     */
    public HierarchicalClusterAdapter(double[][] distances) {
        this.distances = distances;
        this.linkageStrategy = new AverageLinkageStrategy(); // linkage strategy at default
    }

    /**
     * Get cluster assignment starting with 0 as class labels
     * @return an array of cluster assignments
     */
    public int[] getClusterAssignment(int clusterNum) {

        int[] clusterAssignment = null;

        // get hierarchical cluster
        Cluster cluster = getClusterAlgorithm(this.linkageStrategy);

        // get a map of distance with corresponding instances
        Stack<ClusterWrapperNode> listClusterNodes = getCluster(cluster);

        if (cluster == null) {
            LOGGER.log(Level.INFO, "Hierarchical cluster is null!");
            return clusterAssignment;
        }

        if (listClusterNodes == null) {
            LOGGER.log(Level.INFO, "The list of hierarchical cluster nodes is null!");
            return clusterAssignment;
        }

        clusterAssignment = new int[this.distances.length];

        int curClusterNum = listClusterNodes.size();
        while(curClusterNum > clusterNum) {

            if (curClusterNum < 2) {
                LOGGER.log(Level.INFO, "There are at most one node in the queue!");
                break;
            }

            /*
                The method of combining clusters:
                    1. Every time pop up the first two nodes in the stack
                    2. Combine the first two nodes into one
                    3. Push back the new one into the stack
             */

            // get the first two nodes to combine them into one
            ClusterWrapperNode cn1 = listClusterNodes.pop();
            ClusterWrapperNode cn2 = listClusterNodes.pop();

            // add the new node into the head of the list
            cn1.combineClusterNodes(cn2);
            listClusterNodes.push(cn1);

            // reduce one cluster
            curClusterNum--;
        }

        int classLabel = 0;
        while(!listClusterNodes.isEmpty()) {
            ClusterWrapperNode node = listClusterNodes.pop();
            for (Integer seqNo : node.instances) {
                clusterAssignment[seqNo - 1] = classLabel;
            }
            
            classLabel++;
        }

        return clusterAssignment;
    }


    /**
     * A stack of instances in the wrapper class of the distance with corresponding instances
     * @param cluster hierarchical cluster instance
     * @return a stack of distances with corresponding instances
     */
    private Stack<ClusterWrapperNode> getCluster(Cluster cluster) {

        Stack<ClusterWrapperNode> clusterNodeList = null;

        if (cluster == null) {
            LOGGER.log(Level.INFO, "Hierarchical cluster is null!");
            return clusterNodeList;
        }

        clusterNodeList = new Stack<ClusterWrapperNode>();

        Queue<List<Cluster>> queue = new LinkedList<List<Cluster>>();
        queue.offer(new ArrayList<Cluster>(Arrays.asList(cluster)));

        Double levelDistance = 0.0; // the distance from the leaf node
        while(!queue.isEmpty()) {

            List<Cluster> curClusterList = queue.poll();

            //---------- If the list is the list of only containing leaf nodes -----------//
            boolean flag = true;
            List<Integer> levelList = new ArrayList<Integer>();
            for (Cluster curCluster : curClusterList) {
                if (!curCluster.isLeaf()) {
                    flag = false;
                    break;
                }
                String name = curCluster.getName(); // the leaf node name
                levelList.add(Integer.parseInt(name));
            }

            if (flag) {
                if (levelList != null && levelList.size() > 0) {
                    clusterNodeList.push(new ClusterWrapperNode(levelDistance, levelList));
                }
                continue; // move to the next list in the queue
            }

            //---------- If the list is not the list of only containing left nodes ----------//
            List<Cluster> levelClusterList = new ArrayList<Cluster>();
            for (Cluster curCluster : curClusterList) {
                // if the current cluster node is leaf
                if (curCluster.isLeaf()) {
                    levelClusterList.add(curCluster);

                } else {

                    // add the list of leaf nodes so far as a list into the queue
                    if (levelClusterList != null && levelClusterList.size() > 0) {
                        queue.offer(levelClusterList);
                        levelClusterList = new ArrayList<Cluster>();
                    }

                    levelDistance = curCluster.getDistanceValue(); // update level distance

                    // get the children of the current non-leaf node as one single list
                    List<Cluster> tempClusterNodeList = new ArrayList<Cluster>();
                    for (Cluster child : curCluster.getChildren()) {
                        tempClusterNodeList.add(child);
                    }
                    queue.offer(tempClusterNodeList);
                }

            }

        }

        return clusterNodeList;
    }

    /**
     * Generate a hierarchical clustering instance
     * @param linkageStrategy linkage strategy
     * @return a hierarchical clustering instance
     */
    private Cluster getClusterAlgorithm(LinkageStrategy linkageStrategy) {

        Cluster cluster = null;
        if (this.distances == null) {
            LOGGER.log(Level.INFO, "Distance matrix is null!");
            return cluster;
        }

        if (this.distances.length == 0 || this.distances[0].length == 0) {
            LOGGER.log(Level.INFO, "Distance matrix is empty!");
            return cluster;
        }

        int ROW = this.distances.length;
        int COLUMN = this.distances[0].length;

        if (ROW != COLUMN) {
            LOGGER.log(Level.INFO, "The dimensions in distance matrix is not consistent!");
        }

        String[] names = new String[ROW]; // name instances starting with 1
        for (int i = 0; i < names.length; i++) {
            names[i] = String.valueOf(i + 1);
        }


        ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();

        // do hierarchical clustering algorithm
        cluster = alg.performClustering(this.distances, names, linkageStrategy);

        return cluster;

    }

    /**
     * Construct a class including both the distance and its corresponding instances
     */
    private class ClusterWrapperNode implements Comparator<ClusterWrapperNode>{
        Double distance;
        List<Integer> instances;

        ClusterWrapperNode(Double distance, List<Integer> instances) {
            this.distance = distance;
            this.instances = instances;
        }

        /**
         * compare two cluster nodes
         * @param o1 cluster node 1
         * @param o2 clsuter node 2
         * @return 0 if they are euqal, 1 if o1 is bigger than o2, otherwise -1.
         */
        @Override
        public int compare(ClusterWrapperNode o1, ClusterWrapperNode o2) {
            double diff = o1.distance - o2.distance;
            if (diff > 0) return 1;
            else if (diff < 0) return -1;
            else return 0;
        }

        /**
         * Combine two cluster nodes into one
         * @param cn the other cluster node
         * @return one cluster nodes
         */
        ClusterWrapperNode combineClusterNodes(ClusterWrapperNode cn) {
            this.instances.addAll(cn.instances);
            return new ClusterWrapperNode(Math.max(this.distance, cn.distance), this.instances);
        }
    }

    /**
     * Setter for LinkageStrategy
     * @param linkageStrategy linkage Strategy
     */
    public void setLinkageStrategy(LinkageStrategy linkageStrategy) {
        this.linkageStrategy = linkageStrategy;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        double[][] distances = new double[][] { { 0, 1, 9, 7, 11, 14, 1},
                                                { 1, 0, 4, 3, 8, 10, 1},
                                                { 9, 4, 0, 9, 2, 8, 1},
                                                { 7, 3, 9, 0, 6, 13, 1},
                                                { 11, 8, 2, 6, 0, 10, 1},
                                                { 14, 10, 8, 13, 10, 0, 1},
                                                { 3, 6, 7, 13, 10, 0, 1}};

        HierarchicalClusterAdapter test = new HierarchicalClusterAdapter(distances);
        int[] clusterAssignments = test.getClusterAssignment(5);
        Utilities.printArray(clusterAssignments);

        clusterAssignments = test.getClusterAssignment(4);
        Utilities.printArray(clusterAssignments);

        clusterAssignments = test.getClusterAssignment(3);
        Utilities.printArray(clusterAssignments);

        clusterAssignments = test.getClusterAssignment(2);
        Utilities.printArray(clusterAssignments);
    }
}

