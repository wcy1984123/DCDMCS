package initializer.dtws;

import com.dtw.DTW;
import com.dtw.TimeWarpInfo;
import com.timeseries.TimeSeries;
import com.util.DistanceFunction;
import com.util.DistanceFunctionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.dtws
 * Date: 26/Mar/2015
 * Time: 20:52
 * System Time: 8:52 PM
 */

/**
 * Original Dynamic Time Warping
 */
public class OriginalDTW implements IDTW {
    private final static Logger LOGGER = Logger.getLogger(ItakuraParallelogramDTW.class.getName());
    private DistanceFunction mDistFn;
    private TimeWarpInfo mInfo;

    public OriginalDTW(String distanceName){
        this.mDistFn = DistanceFunctionFactory.getDistFnByName(distanceName);
    }

    /**
     * Compute the DTW-related distance between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the distance between two time series
     */
    @Override
    public double computeDistance(double[] timeseries1, double[] timeseries2) {

        // timeseries1 and timeseries2 should be null
        TimeSeries t1 = new TimeSeries(timeseries1, ',');
        TimeSeries t2 = new TimeSeries(timeseries2, ',');

        //******************* Original DTW *********************//
        this.mInfo = DTW.getWarpInfoBetween(t1, t2, this.mDistFn);

        return mInfo.getDistance();
    }


    /**
     * Compute the optimal warping path between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the optimal warping path between two time series
     */
    @Override
    public List<List<Integer>> computePath(double[] timeseries1, double[] timeseries2) {
        List<List<Integer>> optimalPath = null;

        if (this.mInfo == null) {
            LOGGER.info("The time warping information is null!");
            return optimalPath;
        }

        return this.mInfo.getPath().getOptimalPath();
    }

    /**
     * Compute the DTW-related distance between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the distance between two time series
     */
    @Override
    public double computeDistance(List<Double> timeseries1, List<Double> timeseries2) {

        // timeseries1 and timeseries2 should be null
        TimeSeries t1 = new TimeSeries(timeseries1, ',');
        TimeSeries t2 = new TimeSeries(timeseries2, ',');

        //******************* Original DTW *********************//
        this.mInfo = DTW.getWarpInfoBetween(t1, t2, this.mDistFn);

        return mInfo.getDistance();
    }


    /**
     * Compute the optimal warping path between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the optimal warping path between two time series
     */
    @Override
    public List<List<Integer>> computePath(List<Double> timeseries1, List<Double> timeseries2) {
        List<List<Integer>> optimalPath = null;

        if (this.mInfo == null) {
            LOGGER.info("The time warping information is null!");
            return optimalPath;
        }

        return this.mInfo.getPath().getOptimalPath();
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {

        double[] t1 = new double[275];
        double[] t2 = new double[275];
        OriginalDTW test = new OriginalDTW("BinaryDistance");
        try {

            BufferedReader br = new BufferedReader(new FileReader("/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/Utilities/FastDynamicTimeWarping/trace0.csv"));

            String line = null;
            int count = 0;
            while((line = br.readLine()) != null) {
                t1[count++] = Double.parseDouble(line);
            }



            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("/Users/chiyingwang/Documents/IntelliJIdeaSpace/DCDMCS/Utilities/FastDynamicTimeWarping/trace1.csv"));
            String line = null;
            int count = 0;
            while((line = br.readLine()) != null) {
                t2[count++] = Double.parseDouble(line);
            }
            br.close();
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println(test.computeDistance(t1, t2));
        List<List<Integer>> path = test.computePath(t1, t2);

        for (List<Integer> point : path) {
            System.out.println("x = " + point.get(0) + " y = " + point.get(1));
        }
    }

}
