package initializer.initializers;

/**
 * Project: DCDMC
 * Package: initializer
 * Date: 23/Mar/2015
 * Time: 20:41
 * System Time: 8:41 PM
 */

/*
    1. OriginalDTW
        * Original Dynamic Time Warping
    2. SakoeChibaDTW
        * Sakoe-Chiba Band Dynamic Time Warping
    3. ItakuraParallelogramDTW
        * Itakura-Parallelogram Band Dynamic Time Warping
    4. FastOptimalDTW
        * Fast Dynamic Time Warping
    5. MatlabOriginalDTW
        * Original Dynamic Time Warping In Matlab
    6. DDTW
        * Deviated Dynamic Time Warping
 */
public enum INITIALIZERTYPE {
    ORIGINALDTW,
    SAKOECHIBADTW,
    ITAKURAPARALLELOGRAMDTW,
    FASTOPTIMALDTW,
    MATLABORIGINALDTW,
    DEVIATEDDTW;
}
