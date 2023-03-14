package services;

import apiContract.AllPersonResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.PersonDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.UnauthorizedException;

/**
 * This service is responsible for interacting with people data and retrieving
 * person information from the database.
 */
public class PersonService {
    private Database db;
    private PersonDAO personDAO;
    private AuthTokenDAO authTokenDAO;

    public PersonService() throws DataAccessException {
        db = new Database();
        personDAO = new PersonDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
    }

    /**
     * Gets the details for a single person from a given personID
     * 
     * @param personID The personID to get details for
     * @return a response object containing the details for the given personID
     */
    public PersonDetailsResponse getPersonDetails(PersonRequest request)
            throws DataAccessException, EntityNotFoundException, UnauthorizedException {
        try {


            var authTokenObj = authTokenDAO.getAuthToken(request.authToken);
            var person = personDAO.getPersonById(request.personID);
            if (person == null) {
                throw new EntityNotFoundException("The personID " + request.personID + " does not exist in the database");
            }
            if (authTokenObj == null || !person.associatedUsername.equals(authTokenObj.username)) {
                throw new UnauthorizedException("AuthToken does not exist");
            }
            return new PersonDetailsResponse(person.personID, person.associatedUsername, person.firstName, person.lastName,
                    person.gender, person.fatherID, person.motherID, person.spouseID, true);
        }finally {
            db.closeConnection(true);

        }
    }

    /**
     * Returns a list of all the persons corresponding to a person's family
     * 
     * @param personID the personID of the user to get all family members
     * @return a response object containing a list of the person's family members
     */
    public AllPersonResponse getAllPersons(PersonRequest request) throws DataAccessException, UnauthorizedException {
        try {

            var authTokenObj = authTokenDAO.getAuthToken(request.authToken);
            if (authTokenObj == null) {
                throw new UnauthorizedException("AuthToken does not exist");
            }
            var persons = personDAO.getPersonsByUsername(authTokenObj.username);

            return new AllPersonResponse(persons, true);
        }finally {
            db.closeConnection(true);
        }
    }
}
