package initializer.clusterings;

import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: initializer.clusterings
 * Date: 09/Apr/2015
 * Time: 14:18
 * System Time: 2:18 PM
 */
public class InitialClusteringFactory {

private static final Logger LOGGER = Logger.getLogger(InitialClusteringFactory.class.getName());
private static InitialClusteringFactory ourInstance = new InitialClusteringFactory();

        /**
         *
         * @return instance of the class
         */
        public static InitialClusteringFactory getInstance() {
            return ourInstance;
        }

        /**
         * Class constructor
         */
        private InitialClusteringFactory() {
        }

        /**
         * Generate data source
         * @param dt data type
         * @return data interface
         */
        public IClusteringAlgorithm createInitialClusters(INITIALCLUSTERINGTYPE dt) {
            IClusteringAlgorithm iInitializer = null;

            switch (dt) {
                case HIERARCHICALCLUSTERING:
                    iInitializer = new HierarchicalClusterAdapter();
                    break;
                case KMEANSCLUSTERING:
                    iInitializer = new KMeansClusterAdapter();
                    break;
                default:
                    LOGGER.info("No Matching Instance To Created!");
            }

            return iInitializer;
        }

        /**
         * test
         * @param args user input
         */
        public static void main(String[] args) {
            InitialClusteringFactory test = new InitialClusteringFactory();
        }
}
