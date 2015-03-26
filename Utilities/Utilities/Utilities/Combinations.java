package Utilities;

/**
 * Project: DCDMC
 * Package: Utilities
 * Date: 22/Mar/2015
 * Time: 12:56
 * System Time: 12:56 PM
 */
public class Combinations {

    /**
     * Calculate the combinations where k out of n
     * @param n
     * @param k
     * @return combinations
     */
    public static long nchoosek(long n, long k) {
        long nCk = 1;
        for (long i = 0; i < k; i++) {
            nCk = nCk * (n-i) / (i+1);
        }

        return nCk;
    }

    public static void main(String[] args) {
        System.out.println(Combinations.nchoosek(6, 3));
    }
}
