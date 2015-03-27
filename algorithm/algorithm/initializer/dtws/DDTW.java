package initializer.dtws;

import java.util.List;

/**
 * Project: DCDMC
 * Package: initializer.dtws
 * Date: 26/Mar/2015
 * Time: 15:38
 * System Time: 3:38 PM
 */

/**
 * Deviated Dynamic Time Warping
 */
public class DDTW implements IDTW {

    /**
     * Compute the DTW-related distance between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the distance between two time series
     */
    public double computeDistance(double[] timeseries1, double[] timeseries2) {
        return 0.0;
    }

    /**
     * Compute the optimal warping path between two time series
     * @param timeseries1 the first time series
     * @param timeseries2 the second time series
     * @return the optimal warping path between two time series
     */
    public List<List<Integer>> computePath(double[] timeseries1, double[] timeseries2) {
        return null;
    }
}
