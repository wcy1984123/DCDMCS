package stoppingcriteria;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 09:45
 * System Time: 9:45 AM
 */

import java.util.List;

/**
 * It defines an interface of stopping criteria
 */
public interface IStoppingCriteria {

    /**
     * Determine the degree of clustering agreement
     * @param cluster1 one clustering results
     * @param cluster2 the other clustering results
     * @return the degree of clustering agreement; 1 refers to be the same clustering results; 0 refers to be the totally different clustering results
     */
    public double computeSimilarity(int[] cluster1, int[] cluster2);

}
