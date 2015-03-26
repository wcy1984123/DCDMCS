package dao;

import Utilities.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: dao
 * Date: 22/Mar/2015
 * Time: 18:02
 * System Time: 6:02 PM
 */

/*
    Hypnogram Format Encoding Rules:
    %~~~~~~~~~~~~~~~~~~~~~~~~ Encoding Rule (Format) ~~~~~~~~~~~~~~~~~~~~~~~~~%
    % Format = 2 - WS Model
    % Format = 3 - WNR Model
    % Format = 4 - WDL Model
    % Format = 5 - W123R Model
    %~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%
    %~~~~~~~~~~~~~~~~~~~~~~~~~~ Encoding Rule (WS) ~~~~~~~~~~~~~~~~~~~~~~~~~~~%
    % Wake Stage - 0
    % Sleep Stage - 6(Combine Stage 1 2 3 5 into one)
    %~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~%

    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%Encoding Rule (WNR)%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Wake Stage - 0
    % NREM Stage - 6(Combine Stage 1 2 3 into one)
    % REM Stage - 5
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%Encoding Rule (WDL)%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Wake Stage - 0
    % Deep Stage - 3
    % Light Stage - 6(Combine Stage 1 2 5 into one)
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

 */

public class HypnogramDao extends AbstractDaoInput {

    private static final Logger LOGGER = Logger.getLogger(HypnogramDao.class.getName());
    /**
     * Generate two dimensional array including data
     * @param path source file path
     * @param args hypnogram data type
     * @return two dimensional array including data
     */
    @Override
    public int[][] getDataSource(String path, String args) {

        int[][] data = readFile(path);

        int[][] formatData = formatHypnogrmas(data, Integer.parseInt(args));

        int[][] res = Utilities.convertToTwoDimensionArray(getHypnogramData(formatData));

        return res;
    }

    /**
     * Load file data into memory
     * @param path external file path
     * @return two dimensional array
     */
    private int[][] readFile(String path) {

        List<List<Integer>> cache = new ArrayList<List<Integer>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;
            while((line = br.readLine()) != null){
                List<Integer> curline = new ArrayList<Integer>();
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    curline.add(Integer.parseInt(values[i]));
                }
                cache.add(curline);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // convert list structures into primitive data
        int[][] res = readFile(cache);
        return res;
    }

    /**
     * Convert list structures into primitive data
     * @param datasource list of list data source
     * @return two dimensional array
     */
    private int[][] readFile(List<List<Integer>> datasource) {
        int[][] res = null;
        if (datasource == null) {
            LOGGER.log(Level.INFO, "data source is null!");
            return res;
        }

        if (datasource.size() == 0) {
            LOGGER.log(Level.INFO, "data source is empty!");
            return res;
        }

        List<Integer> data = datasource.get(0);
        int ROW = datasource.size();
        int COLUMN = data.size();
        res = new int[ROW][COLUMN];
        for (int i = 0; i < ROW; i++) {
           data = datasource.get(i);
            if (data == null || data.size() == 0) {
                LOGGER.log(Level.INFO, "data is null or empty!");
                break;
            }

            for (int j = 0; j < COLUMN; j++) {
                res[i][j] = data.get(j);
            }
        }

        return res;
    }

    /**
     * Format data in terms of format type
     * @param data hypnograms
     * @param format 3 types of formats
     * @return formatted data
     */
    private int[][] formatHypnogrmas(int[][] data, int format) {
        if (data == null) {
            LOGGER.log(Level.INFO, "data is null!");
            return data;
        }

        if (data.length == 0 || data[0].length == 0) {
            LOGGER.log(Level.INFO, "data is empty!");
            return data;
        }

        int ROW = data.length;
        int COLUMN = data[0].length;
        int[][] res = new int[ROW][COLUMN];

        // copy data
        // Delete '4','6' from hypnogram before translating 5 states into 3 states
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (data[i][j] == 4) {
                    res[i][j] = 44;
                } else if (data[i][j] == 6) {
                    res[i][j] = 66;
                } else {
                    res[i][j] = data[i][j];
                }
            }
        }

        // format data
        if (format == 2) {
            // WS format
            // Change 5 states hypnogram into 2 states hypnogram combining stage 1, 2, 3, and 5 into stage 6
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    if (res[i][j] != 0 && res[i][j] <= 6) {
                        res[i][j] = 6;
                    }
                }
            }
        } else if (format == 3) {
            // WNR format
            // Change 5 states hypnogram into 3 states hypnogram combining stage 1, 2, and 3  into stage 6
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    if (res[i][j] != 0 && res[i][j] != 5 && res[i][j] <= 5) {
                        res[i][j] = 6;
                    }
                }
            }

        } else if (format == 4) {
            // WDL format
            // Change 5 states hypnogram into 3 states hypnogram combining stage 1, 2, and 5  into stage 6
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    if (res[i][j] != 0 && res[i][j] != 3 && res[i][j] <= 5) {
                        res[i][j] = 6;
                    }
                }
            }
        } else if (format == 5) {
            // W123R format
            // keep the original dataset

        } else {
            // keep the original dataset
        }

        return res;
    }

    /**
     * Reformat data using numbers in ascending sort "1 2 3"
     * @param data processed hypnogram by function "formatHypngrams"
     * @return removed states dataset
     */
    private List<List<Integer>> getHypnogramData(int[][] data) {
        int ROW = data.length;
        int COLUMN = data[0].length;
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (data[i][j] == 0) {
                    data[i][j] = 1;
                } else if (data[i][j] == 6) {
                    data[i][j] = 2;
                } else if (data[i][j] == 5) {
                    data[i][j] = 3;
                } else {
                    // keep the same
                }
            }
        }

        // delete the unused values bigger than 3
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> level = null;
        for (int i = 0; i < ROW; i++) {
            level = new ArrayList<Integer>();
            for (int j = 0; j < COLUMN; j++) {
                if (data[i][j] <= 3) {
                    level.add(data[i][j]);
                }
            }
            res.add(level);
        }

        return res;
    }

    /**
     * Remove state duration info
     * @param data processed hypnogram by function "getHypnogramData"
     * @return removed data with no state duration info
     */
    private List<List<Integer>> removeHypnogramBoutDuration(int[][] data) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> level = null;

        int ROW = data.length;
        int COLUMN = data[0].length;
        for (int i = 0; i < ROW; i++) {
            int currentState = data[i][0];
            level = new ArrayList<Integer>();
            level.add(currentState);
            for (int j = 1; j < COLUMN; j++) {
                int nextState = data[i][j];
                if (currentState != nextState) {
                    level.add(nextState);
                    currentState = nextState;
                }
            }
            res.add(level);
        }

        return res;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        HypnogramDao test = new HypnogramDao();
    }
}
