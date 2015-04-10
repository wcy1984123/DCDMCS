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
    public double[][] getDataSourceAsMatrix(String path, String args) {

        double[][] data = Utilities.convertToTwoDimensionDoubleArray(readFile(path));

        double[][] formatData = formatHypnogrmas(data, Integer.parseInt(args));

        double[][] res = Utilities.convertToTwoDimensionDoubleArray(getHypnogramData(formatData, Integer.parseInt(args)));

        return res;
    }

    /**
     * Generate list of list of doubles including data
     * @param path source file path
     * @args sparing parameters for other functionality
     * @return list of list of doubles including data
     */
    @Override
    public List<List<Double>> getDataSourceAsLists(String path, String args) {
        List<List<Double>> data = readFile(path);

        List<List<Double>> formatData = formatHypnogrmas(data, Integer.parseInt(args));

        List<List<Double>> res = getHypnogramData(formatData, Integer.parseInt(args));

        return res;
    }

    /**
     * Load file data into memory
     * @param path external file path
     * @return two dimensional array
     */
    private List<List<Double>> readFile(String path) {

        List<List<Double>> cache = new ArrayList<List<Double>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = null;
            while((line = br.readLine()) != null){
                List<Double> curline = new ArrayList<Double>();
                String[] values = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    curline.add(Double.parseDouble(values[i]));
                }
                cache.add(curline);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cache;
    }

    /**
     * Format data in terms of format type
     * @param data hypnograms
     * @param format 4 types of formats
     * @return formatted data
     */
    private double[][] formatHypnogrmas(double[][] data, int format) {

        double[][] res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "data is null!");
            return res;
        }

        if (data.length == 0 || data[0].length == 0) {
            LOGGER.log(Level.INFO, "data is empty!");
            return res;
        }

        int ROW = data.length;
        int COLUMN = data[0].length;
        res = new double[ROW][COLUMN];

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
            // keep the original dataset and make 0, 1, 2, 3, 5 to 1, 2, 3, 4, 5
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COLUMN; j++) {
                    if (res[i][j] < 5) {
                        res[i][j]++;
                    }
                }
            }

        } else {
            // keep the original dataset
        }

        return res;
    }

    /**
     * Format data in terms of format type
     * @param data hypnograms
     * @param format 4 types of formats
     * @return formatted data
     */
    private List<List<Double>> formatHypnogrmas(List<List<Double>> data, int format) {

        List<List<Double>> res = null;

        if (data == null) {
            LOGGER.log(Level.INFO, "data is null!");
            return res;
        }

        if (data.size() == 0) {
            LOGGER.log(Level.INFO, "data is empty!");
            return res;
        }

        int ROW = data.size();
        res = new ArrayList<List<Double>>();

        // copy data
        // Delete '4','6' from hypnogram before translating 5 states into 3 states
        for (int i = 0; i < ROW; i++) {
            int COLUMN = data.get(i).size();
            List<Double> eachRow = new ArrayList<Double>();
            for (int j = 0; j < COLUMN; j++) {
                double value = data.get(i).get(j);
                if (value == 4) {
                    eachRow.add(44.0);
                } else if (value == 6) {
                    eachRow.add(66.0);
                } else {
                    eachRow.add(value);
                }
            }
            res.add(eachRow);
        }

        // format data
        if (format == 2) {
            // WS format
            // Change 5 states hypnogram into 2 states hypnogram combining stage 1, 2, 3, and 5 into stage 6
            for (int i = 0; i < ROW; i++) {
                int COLUMN = res.get(i).size();
                for (int j = 0; j < COLUMN; j++) {
                    double value = res.get(i).get(j);
                    if (value != 0 && value <= 6) {
                        res.get(i).set(j, 6.0);
                    }
                }
            }
        } else if (format == 3) {
            // WNR format
            // Change 5 states hypnogram into 3 states hypnogram combining stage 1, 2, and 3  into stage 6
            for (int i = 0; i < ROW; i++) {
                int COLUMN = res.get(i).size();
                for (int j = 0; j < COLUMN; j++) {
                    double value = res.get(i).get(j);
                    if (value != 0 && value != 5 && value <= 5) {
                        res.get(i).set(j, 6.0);
                    }
                }
            }

        } else if (format == 4) {
            // WDL format
            // Change 5 states hypnogram into 3 states hypnogram combining stage 1, 2, and 5  into stage 6
            for (int i = 0; i < ROW; i++) {
                int COLUMN = res.get(i).size();
                for (int j = 0; j < COLUMN; j++) {
                    double value = res.get(i).get(j);
                    if (value != 0 && value != 3 && value <= 5) {
                        res.get(i).set(j, 6.0);
                    }
                }
            }
        } else if (format == 5) {
            // W123R format
            // keep the original dataset and make 0, 1, 2, 3, 5 to 1, 2, 3, 4, 5
            for (int i = 0; i < ROW; i++) {
                int COLUMN = res.get(i).size();
                for (int j = 0; j < COLUMN; j++) {
                    double value = res.get(i).get(j);
                    if (value < 5) {
                        res.get(i).set(j, value + 1);
                    }
                }
            }

        } else {
            // keep the original dataset
        }

        return res;
    }

    /**
     * Reformat data using numbers in ascending sort "1 2 3"
     * @param data processed hypnogram by function "formatHypngrams"
     * @param format data format
     * @return removed states dataset
     */
    private List<List<Double>> getHypnogramData(double[][] data, int format) {
        int ROW = data.length;
        int COLUMN = data[0].length;
        if (format < 5) {
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
        }

        // delete the unused values bigger than 5
        List<List<Double>> res = new ArrayList<List<Double>>();
        List<Double> level = null;
        for (int i = 0; i < ROW; i++) {
            level = new ArrayList<Double>();
            for (int j = 0; j < COLUMN; j++) {
                if (data[i][j] <= 5) {
                    level.add(data[i][j]);
                }
            }
            res.add(level);
        }

        return res;
    }

    /**
     * Reformat data using numbers in ascending sort "1 2 3"
     * @param data processed hypnogram by function "formatHypngrams"
     * @param format data format
     * @return removed states dataset
     */
    private List<List<Double>> getHypnogramData(List<List<Double>> data, int format) {
        int ROW = data.size();
        if (format < 5) {
            for (int i = 0; i < ROW; i++) {
                int COLUMN = data.get(i).size();
                for (int j = 0; j < COLUMN; j++) {
                    double value = data.get(i).get(j);
                    if (value == 0) {
                        data.get(i).set(j, 1.0);
                    } else if (value == 6) {
                        data.get(i).set(j, 2.0);
                    } else if (value == 5) {
                        data.get(i).set(j, 3.0);
                    } else {
                        // keep the same
                    }
                }
            }
        }

        // delete the unused values bigger than 5
        List<List<Double>> res = new ArrayList<List<Double>>();
        List<Double> level = null;
        for (int i = 0; i < ROW; i++) {
            int COLUMN = data.get(i).size();
            level = new ArrayList<Double>();
            for (int j = 0; j < COLUMN; j++) {
                double value = data.get(i).get(j);
                if (value <= 5) {
                    level.add(value);
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
    private List<List<Double>> removeHypnogramBoutDuration(double[][] data) {
        List<List<Double>> res = new ArrayList<List<Double>>();
        List<Double> level = null;

        int ROW = data.length;
        int COLUMN = data[0].length;
        for (int i = 0; i < ROW; i++) {
            double currentState = data[i][0];
            level = new ArrayList<Double>();
            level.add(currentState);
            for (int j = 1; j < COLUMN; j++) {
                double nextState = data[i][j];
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
