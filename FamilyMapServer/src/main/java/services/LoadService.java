package services;

import apiContract.LoadRequest;
import apiContract.LoadResponse;

/**
 * This service is responsible for loading a set of preset data into the
 * database
 */
public class LoadService {
    /**
     * Clears the current state of the database and then loads in new User, Event,
     * and Person data from the given request
     * 
     * @param request an object containing lists of the data to populate
     * @return A response object with the status of the call
     */
    public LoadResponse load(LoadRequest request) {
        return new LoadResponse();
    }
}
