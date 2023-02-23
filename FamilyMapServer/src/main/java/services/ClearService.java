package services;

import apiContract.ClearResponse;


/**
 * This service is responsible for clearing the database
 */
public class ClearService {

    /**
     * Clears the data in all of the database tables (User, Person, Event,
     * AuthToken)
     * 
     * @return Returns a ClearResponse object that indicates the result of the clear
     */
    public ClearResponse clear() {
        return new ClearResponse();
    }
}
