package Utilities;

/**
 * Project: DCDMC
 * Package: Utilities
 * Date: 27/Mar/2015
 * Time: 08:03
 * System Time: 8:03 AM
 */

import starter.Config;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility functions for dynamic models
 */
public class Models {

    private static final Logger LOGGER = Logger.getLogger(Models.class.getName());

    /**
     * Count state transition of a sequence in a matrix
     * @param seq a sequence of data whose index starts with 1
     * @return a matrix of state transition
     */
    public static int[][] countStateTransitionForOneSequence(int[] seq) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seq == null || seq.length < 2) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        // count state transition
        for (int i = 0; i < seq.length - 1; i++) {
            stateTransition[seq[i] - 1][seq[i + 1] - 1]++;
        }

        return stateTransition;
    }

    /**
     * Count state transition of a sequence (no state transition from one to iteself) in a matrix
     * @param seq a sequence of data whose index starts with 1
     * @return a matrix of state transition (no state transition from one to iteself)
     */
    public static int[][] countNoSelfStateTransitionForOneSequence(int[] seq) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seq == null || seq.length < 2) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        // count state transition
        for (int i = 0; i < seq.length - 1; i++) {
            int pre = seq[i] - 1;
            int cur = seq[i + 1] - 1;
            // only count the state transition from one state to another different state
            if (pre != cur) stateTransition[pre][cur]++;
        }

        return stateTransition;
    }

    /**
     * Count state transition of a sequence in a matrix
     * @param seq a sequence of data whose index starts with 1
     * @return a matrix of state transition
     */
    public static int[][] countStateTransitionForOneSequence(List<Integer> seq) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seq == null || seq.size() < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        // count state transition
        for (int i = 0; i < seq.size() - 1; i++) {
            stateTransition[seq.get(i) - 1][seq.get(i + 1) - 1]++;
        }

        return stateTransition;
    }

    /**
     * Count state transition (no self transition) of a sequence in a matrix
     * @param seq a sequence of data whose index starts with 1
     * @return a matrix of state transition (no self transition)
     */
    public static int[][] countNoSelfStateTransitionForOneSequence(List<Integer> seq) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seq == null || seq.size() < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        // count state transition
        for (int i = 0; i < seq.size() - 1; i++) {
            int pre = seq.get(i) - 1;
            int cur = seq.get(i + 1) -1;
            // only count the state transition from one state to another different state
            if (pre != cur) stateTransition[pre][cur]++;
        }

        return stateTransition;
    }

    /**
     * Count state transition of a sequence in a matrix
     * @param seqs a array of sequences whose index starts with 1
     * @return a matrix of state transition
     */
    public static int[][] countStateTransitionForSequences(int[][] seqs) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null!");
            return stateTransition;
        }

        if (seqs.length == 0 || seqs[0].length == 0) {
            LOGGER.info("The sequence is empty!");
            return stateTransition;
        }

        int ROW = seqs.length;
        int COLUMN = seqs[0].length;

        // count state transition
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN - 1; j++) {
                stateTransition[seqs[i][j] - 1][seqs[i][j + 1] - 1]++;
            }
        }

        return stateTransition;
    }

    /**
     * Count state transition (no self transition) of a sequence in a matrix
     * @param seqs a array of sequences whose index starts with 1
     * @return a matrix of state transition (no self transition)
     */
    public static int[][] countNoSelfStateTransitionForSequences(int[][] seqs) {

        // a sequence of data whose index starts with 1
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null!");
            return stateTransition;
        }

        if (seqs.length == 0 || seqs[0].length == 0) {
            LOGGER.info("The sequence is empty!");
            return stateTransition;
        }

        int ROW = seqs.length;
        int COLUMN = seqs[0].length;

        // count state transition
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN - 1; j++) {
                int pre = seqs[i][j] - 1;
                int cur = seqs[i][j + 1] - 1;
                if (pre != cur) stateTransition[pre][cur]++;
            }
        }

        return stateTransition;
    }

    /**
     * Count state transition of a sequence in a matrix
     * @param seqs a array of sequences whose index starts with 1
     * @return a matrix of state transition
     */
    public static int[][] countStateTransitionForSequences(List<List<Integer>> seqs) {

        // a sequence of data whose index starts with 0
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        if (seqs.size() == 0) {
            LOGGER.info("The sequence is empty!");
            return stateTransition;
        }

        int ROW = seqs.size();

        // count state transition
        for (int i = 0; i < ROW; i++) {
            int COLUMN = seqs.get(i).size();
            for (int j = 0; j < COLUMN - 1; j++) {
                stateTransition[seqs.get(i).get(j) - 1][seqs.get(i).get(j + 1) - 1]++;
            }
        }

        return stateTransition;
    }

    /**
     * Count state transition (no self transition) of a sequence in a matrix
     * @param seqs a array of sequences whose index starts with 1
     * @return a matrix of state transition (no self transition)
     */
    public static int[][] countNoSelfStateTransitionForSequences(List<List<Integer>> seqs) {

        // a sequence of data whose index starts with 0
        int[][] stateTransition = new int[Config.getSTATENUM()][Config.getSTATENUM()];
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateTransition;
        }

        if (seqs.size() == 0) {
            LOGGER.info("The sequence is empty!");
            return stateTransition;
        }

        int ROW = seqs.size();

        // count state transition
        for (int i = 0; i < ROW; i++) {
            int COLUMN = seqs.get(i).size();
            for (int j = 0; j < COLUMN - 1; j++) {
                int pre = seqs.get(i).get(j) - 1;
                int cur = seqs.get(i).get(j + 1) - 1;
                if (pre != cur) stateTransition[pre][cur]++;
            }
        }

        return stateTransition;
    }

    /**
     * Count state duration of a sequence in a map of map
     * @param seq a sequence of data whose index starts with 1
     * @return a map of map of state durations
     */
    public static Map<Integer, Map<Integer, Integer>> countStateDurationForOneSequence(int[] seq) {

        /*
            Outer map: Integer - a given state No.
                       Map<Integer, Integer> - state duration for the given state No.

            Inner map: Integer - a given state duration value.
                       Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Map<Integer, Integer>> stateDurations = null;
        if (seq == null || seq.length < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateDurations;
        }

        stateDurations = new HashMap<Integer, Map<Integer, Integer>>();
        int N = seq.length;
        int i = 0;
        while(i < N) {
            int curState = seq[i];
            int count = 1;
            int j = i + 1;
            while(j < N && seq[j] == curState) {
                count++; // count the duration for a given state
                j++;
            }

            if (!stateDurations.containsKey(curState)) {
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                map.put(count, 1);
                stateDurations.put(curState, map);
            } else {
                Map<Integer, Integer> curStateDurations = stateDurations.get(curState);
                if (!curStateDurations.containsKey(count)) {
                    curStateDurations.put(count, 1);
                } else {
                    int frequency = curStateDurations.get(count);
                    curStateDurations.put(count, frequency + 1);
                }
            }

            i = j; // move i
        }

        return stateDurations;
    }

    /**
     * Count state duration of a sequence in a map of map
     * @param seq a sequence of data whose index starts with 1
     * @return a map of map of state durations
     */
    public static Map<Integer, Map<Integer, Integer>> countStateDurationForOneSequence(List<Integer> seq) {

        /*
            Outer map: Integer - a given state No.
                       Map<Integer, Integer> - state duration for the given state No.

            Inner map: Integer - a given state duration value.
                       Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Map<Integer, Integer>> stateDurations = null;
        if (seq == null || seq.size() < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateDurations;
        }

        stateDurations = new HashMap<Integer, Map<Integer, Integer>>();
        int N = seq.size();
        int i = 0;
        while(i < N) {
            int curState = seq.get(i);
            int count = 1;
            int j = i + 1;
            while(j < N && seq.get(j) == curState) {
                count++; // count the duration for a given state
                j++;
            }

            if (!stateDurations.containsKey(curState)) {
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                map.put(count, 1);
                stateDurations.put(curState, map);
            } else {
                Map<Integer, Integer> curStateDurations = stateDurations.get(curState);
                if (!curStateDurations.containsKey(count)) {
                    curStateDurations.put(count, 1);
                } else {
                    int frequency = curStateDurations.get(count);
                    curStateDurations.put(count, frequency + 1);
                }
            }

            i = j; // move i
        }

        return stateDurations;
    }

    /**
     * Count state duration of a sequence for a given state
     * @param seq a sequence of data whose index starts with 1
     * @param state a given state
     * @return a map of state durations for a given state
     */
    public static Map<Integer, Integer> countStateDurationForOneSequence(int[] seq, int state) {

        /*
            Map: Integer - a given state duration value.
                 Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Integer> stateDurations = null;
        if (seq == null || seq.length < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateDurations;
        }

        // compute all durations for all states
        Map<Integer, Map<Integer, Integer>> map = countStateDurationForOneSequence(seq);

        // get durations for a given state
        stateDurations = map.get(state);

        return stateDurations;
    }

    /**
     * Count state duration of a sequence for a given state
     * @param seq a sequence of data whose index starts with 1
     * @param state a given state
     * @return a map of state durations for a given state
     */
    public static Map<Integer, Integer> countStateDurationForOneSequence(List<Integer> seq, int state) {

        /*
            Map: Integer - a given state duration value.
                 Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Integer> stateDurations = null;
        if (seq == null || seq.size() < 1) {
            LOGGER.log(Level.INFO, "The sequence is null or the length of the sequence is less than 2!");
            return stateDurations;
        }

        // compute all durations for all states
        Map<Integer, Map<Integer, Integer>> map = countStateDurationForOneSequence(seq);

        // get durations for a given state
        stateDurations = map.get(state);

        return stateDurations;
    }

    /**
     * Count state duration of sequences in a map of map
     * @param seqs sequences of data whose index starts with 1
     * @return a map of map of state durations
     */
    public static Map<Integer, Map<Integer, Integer>> countStateDurationForSequences(int[][] seqs) {

        /*
            Outer map: Integer - a given state No.
                       Map<Integer, Integer> - state duration for the given state No.

            Inner map: Integer - a given state duration value.
                       Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Map<Integer, Integer>> stateDurations = null;
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null!");
            return stateDurations;
        }

        if (seqs.length == 0 || seqs[0].length == 0) {
            LOGGER.log(Level.INFO, "The sequence is empty!");
            return stateDurations;
        }

        stateDurations = new HashMap<Integer, Map<Integer, Integer>>();
        int ROW = seqs.length;

        for (int k = 0; k < ROW; k++) {
            int N = seqs[k].length;
            int i = 0;
            while (i < N) {
                int curState = seqs[k][i];
                int count = 1;
                int j = i + 1;
                while (j < N && seqs[k][j] == curState) {
                    count++; // count the duration for a given state
                    j++;
                }

                if (!stateDurations.containsKey(curState)) {
                    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                    map.put(count, 1);
                    stateDurations.put(curState, map);
                } else {
                    Map<Integer, Integer> curStateDurations = stateDurations.get(curState);
                    if (!curStateDurations.containsKey(count)) {
                        curStateDurations.put(count, 1);
                    } else {
                        int frequency = curStateDurations.get(count);
                        curStateDurations.put(count, frequency + 1);
                    }
                }

                i = j; // move i
            }
        }

        return stateDurations;
    }

    /**
     * Count state duration of sequences in a map of map
     * @param seqs sequences of data whose index starts with 1
     * @return a map of map of state durations
     */
    public static Map<Integer, Map<Integer, Integer>> countStateDurationForSequences(List<List<Integer>> seqs) {

        /*
            Outer map: Integer - a given state No.
                       Map<Integer, Integer> - state duration for the given state No.

            Inner map: Integer - a given state duration value.
                       Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Map<Integer, Integer>> stateDurations = null;
        if (seqs == null) {
            LOGGER.log(Level.INFO, "The sequence is null!");
            return stateDurations;
        }

        if (seqs.size() == 0) {
            LOGGER.log(Level.INFO, "The sequence is empty!");
            return stateDurations;
        }

        stateDurations = new HashMap<Integer, Map<Integer, Integer>>();
        int ROW = seqs.size();

        for (int k = 0; k < ROW; k++) {
            int N = seqs.get(k).size();
            int i = 0;
            while (i < N) {
                int curState = seqs.get(k).get(i);
                int count = 1;
                int j = i + 1;
                while (j < N && seqs.get(k).get(j) == curState) {
                    count++; // count the duration for a given state
                    j++;
                }

                if (!stateDurations.containsKey(curState)) {
                    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
                    map.put(count, 1);
                    stateDurations.put(curState, map);
                } else {
                    Map<Integer, Integer> curStateDurations = stateDurations.get(curState);
                    if (!curStateDurations.containsKey(count)) {
                        curStateDurations.put(count, 1);
                    } else {
                        int frequency = curStateDurations.get(count);
                        curStateDurations.put(count, frequency + 1);
                    }
                }

                i = j; // move i
            }
        }

        return stateDurations;
    }

    /**
     * Count state duration of a sequence for a given state
     * @param seqs a sequence of data whose index starts with 1
     * @param state a given state
     * @return a map of state durations for a given state
     */
    public static Map<Integer, Integer> countStateDurationForSequences(int[][] seqs, int state) {

        /*
            Map: Integer - a given state duration value.
                 Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Integer> stateDurations = null;

        // compute all durations for all states
        Map<Integer, Map<Integer, Integer>> map = countStateDurationForSequences(seqs);

        // get durations for a given state
        stateDurations = map.get(state);

        return stateDurations;
    }

    /**
     * Count state duration of a sequence for a given state
     * @param seqs a sequence of data whose index starts with 1
     * @param state a given state
     * @return a map of state durations for a given state
     */
    public static Map<Integer, Integer> countStateDurationForSequences(List<List<Integer>> seqs, int state) {

        /*
            Map: Integer - a given state duration value.
                 Integer - frequency of the given state duration value.
         */

        // a sequence of data whose index starts with 1
        Map<Integer, Integer> stateDurations = null;

        // compute all durations for all states
        Map<Integer, Map<Integer, Integer>> map = countStateDurationForSequences(seqs);

        // get durations for a given state
        stateDurations = map.get(state);

        return stateDurations;
    }



    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

        //------------------------------- test count state transition -------------------------------//
        int[] seq = {1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3};
        Utilities.printMatrix(Models.countStateTransitionForOneSequence(seq));

        List<Integer> seql = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3));
        Utilities.printMatrix(Models.countStateTransitionForOneSequence(seql));

        Map<Integer, Map<Integer, Integer>> data = Models.countStateDurationForOneSequence(seq);

        for (Integer key : data.keySet()) {
            Map<Integer, Integer> map = data.get(key);
            System.out.println(map);
        }
        System.out.println(data.get(1));

        System.out.println();

        Map<Integer, Map<Integer, Integer>> datal = Models.countStateDurationForOneSequence(seql);

        for (Integer key : datal.keySet()) {
            Map<Integer, Integer> map = datal.get(key);
            System.out.println(map);
        }

        System.out.println(datal.get(1));

        System.out.println();

        //------------------------------- test count state duration -------------------------------//
        int[][] seqs = {{1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3},
                        {1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3}};
        Map<Integer, Map<Integer, Integer>> datamap = Models.countStateDurationForSequences(seqs);

        for (Integer key : datamap.keySet()) {
            Map<Integer, Integer> map = datamap.get(key);
            System.out.println(map);
        }
        System.out.println(datamap.get(1));

        System.out.println();


        List<Integer> seq1 = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3));
        List<Integer> seq2 = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 1, 1, 2, 3));
        List<List<Integer>> seqls = new ArrayList<List<Integer>>(Arrays.asList(seq1, seq2));
        Map<Integer, Map<Integer, Integer>> datamaps = Models.countStateDurationForSequences(seqls);

        for (Integer key : datamaps.keySet()) {
            Map<Integer, Integer> map = datamaps.get(key);
            System.out.println(map);
        }
        System.out.println(datamaps.get(1));

        System.out.println();
    }
}
