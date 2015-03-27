package model;

import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: model
 * Date: 27/Mar/2015
 * Time: 16:05
 * System Time: 4:05 PM
 */

/*
    Dynamic model factory
 */

public class ModelFactory {

    private static final Logger LOGGER = Logger.getLogger(ModelFactory.class.getName());
    private static final ModelFactory instance = new ModelFactory();

    /**
     * Singleton pattern
     */
    private ModelFactory() {

    }

    /**
     * Get single instance
     * @return a single instance
     */
    public static ModelFactory getInstance() {
        return instance;
    }

    /**
     * Build dynamic model
     * @param mt dynamic model type
     * @return a dynamic model given the model type
     */
    public IModel createModel(MODELTYPE mt) {
        IModel iModel =  null;
        switch(mt) {
            case MARKOVCHAINMODEL:    // Markov Chain Model
                iModel = new MarkovChainModel();
                break;
            case SEMIMARKOVCHAINMODEL: // semi-Markov Chain Model
                iModel = new SemiMarkovChainModel();
                break;
            case HIDDENMARKOVMODEL:    // Hidden Markov Model
                iModel = new HiddenMarkovModel();
                break;
            default:
                LOGGER.info("No matching data type of dynamic model!");
        }

        return iModel;
    }

}
