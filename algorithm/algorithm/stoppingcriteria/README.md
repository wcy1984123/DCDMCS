## Stopping Criteria

1. Rand index (RI) 
    * It is a measure of agreement between two partitions of the same set, and therefore could be used to measure the similarity of two clusterings on the same dataset. A drawback of this index is that it will produce a nonzero value for the comparison of two randomly constructed partitions. 
2. Adjusted Rand Index (ARI)
    * It is based on the Rand index but corrects for clustering agreements due to chance. 
3. Normalized mutual information (NMI) 
    * It is a distinct way of evaluating clusters by the tradeoff between the number of clusters and qualities.

In the present progress, these three metrics are compared as the basis for the stopping criterion in CDMC, using the resulting CDMC convergence time (number of modeling-clustering iterations) and classification accuracy over labeled synthetic data for evaluation.

### Class Diagram
![Stopping Criteria](/classdiagrams/stoppingcriteriaclassdiagram.png)

See Link: http://nlp.stanford.edu/IR-book/html/htmledition/evaluation-of-clustering-1.html
