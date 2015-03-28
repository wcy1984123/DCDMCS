package model;

import java.util.logging.Logger;

/**
 * Project: DCDMC
 * Package: model
 * Date: 28/Mar/2015
 * Time: 18:27
 * System Time: 6:27 PM
 */

/**
 * Dynamic models factory
 */
public class ModelsFactory {
    private static final Logger LOGGER = Logger.getLogger(ModelsFactory.class.getName());
    private static final ModelsFactory instance = new ModelsFactory();

    /**
     * Singleton pattern
     */
    private ModelsFactory() {

    }

    /**
     * Get single instance
     * @return a single instance
     */
    public static ModelsFactory getInstance() {
        return instance;
    }

    /**
     * Build dynamic model
     * @param mt dynamic model type
     * @return a dynamic model given the model type
     */
    public IModels createModels(MODELSTYPE mt) {
        IModels iModels =  null;
        switch(mt) {
            case STATEBASEDDYNAMICMODELS:    // Markov Chain Model
                iModels = new StateBasedDynamicModels();
                break;
            default:
                LOGGER.info("No matching data type of dynamic models!");
        }

        return iModels;
    }
}
