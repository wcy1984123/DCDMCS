package stoppingcriteria;

/**
 * Project: DCDMC
 * Package: stoppingcriteria
 * Date: 22/Mar/2015
 * Time: 09:49
 * System Time: 9:49 AM
 */

/**
 * It provides the instance of different stopping criteria according to the string input in the config file.
 * Design pattern: singleton + factory pattern
 */
public class StoppingCriteriaFactory {
    private static volatile StoppingCriteriaFactory instanceFactory = null;
    private StoppingCriteriaFactory() {}

    /**
     * Class constructor
     * Instantiate an unique instance of StoppingCriteriaFactory
     * @return an instance of StoppingCriteriaFactory
      */
    public static StoppingCriteriaFactory getInstance() {
        synchronized (StoppingCriteriaFactory.class) {
            if (instanceFactory == null) {
                instanceFactory = new StoppingCriteriaFactory();
            }
        }

        return instanceFactory;
    }

    /**
     * Create an stopping criteria according to string input in the config file
     * @param sc stopping criteria type
     * @return stopping criteria
     */
    public IStoppingCriteria createStoppingCriteria(STOPPINGCRITERIA sc){
        IStoppingCriteria isp = null;
        switch (sc) {
            case RANDINDEX:
                isp = new RandIndex();
                break;
            case ADJUSTEDRANDINDEX:
                isp = new AdjustedRandIndex();
                break;
            case NORMALIZEDMUTUALINFORMATION:
                isp = new NormalizedMutualInformation();
                break;
            case PURITY:
                isp = new Purity();
                break;
            default:
                System.out.println("No Matching Instance To Created!");
        }

        return isp;
    }

    /**
     * test
     * @param args user input
     */
    public static void main(String[] args) {
        StoppingCriteriaFactory instance = StoppingCriteriaFactory.getInstance();
        instance.createStoppingCriteria(STOPPINGCRITERIA.RANDINDEX);
        System.out.println(1);
    }
}
