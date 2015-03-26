##Collectiv Dynamical Modeling & Clustering Framework
The collective dynamical modeling-clustering (CDMC) algorithm addresses the problem of scarcity of dynamical events by pooling data across multiple time series, simultaneously partitioning the collection of time series by dynamical similarity. CDMC reduces the model variation by selectively aggregating instances through clustering. This reduction in variation is accomplished in CDMC without the loss of detail that would result by simply aggregating data without regard for dynamical similarity. The result of CDMC is a collection of groups of instances such that instances within a given group are dynamically similar, while instances in different groups are not.

###Configuration
1. **Dataset**
    * HYPNOGRAM (Hypnogram Dataset)
    * MSNBC (User Navigation Behavior Dataset)

2. **Initializer**
    * DTW (Dynamic Time Warping)
    * CDTW (Constrained Dynamic Time Warping)
    * DDTW (Deviated Dynamic Time Warping)

3. **Stopping Criteria**
    * RANDINDEX (Rand Index)
    * ADJUSTEDRANDINDEX (Adjusted Rand Index)
    * NORMALIZEDMUTUALINFORMATION (Normalized Mutual Information)
    * PURITY (Purity)