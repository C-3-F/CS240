package services;

import apiContract.AllPersonResponse;
import apiContract.PersonDetailsResponse;

/**
 * This service is responsible for interacting with people data and retrieving
 * person information from the database.
 */
public class PersonService {
    /**
     * Gets the details for a single person from a given personID
     * 
     * @param personID The personID to get details for
     * @return a response object containing the details for the given personID
     */
    public PersonDetailsResponse getPersonDetails(String personID) {
        return new PersonDetailsResponse();
    }

    /**
     * Returns a list of all the persons corresponding to a person's family
     * 
     * @param personID the personID of the user to get all family members
     * @return a response object containing a list of the person's family members
     */
    public AllPersonResponse getAllPersons(String personID) {
        return new AllPersonResponse();
    }
}
