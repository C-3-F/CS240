package serviceTests;

import apiContract.PersonRequest;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.PersonDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.UnauthorizedException;
import models.AuthToken;
import models.Person;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ClearService;
import services.PersonService;

import static org.junit.jupiter.api.Assertions.*;
public class PersonServiceTest {

    private PersonService service;
    private PersonDAO personDAO;
    private AuthTokenDAO authTokenDAO;
    private Database db;
    private AuthToken token;
    private Person person1;
    private Person person2;

    @BeforeEach
    public void setup() throws DataAccessException
    {
        service = new PersonService();
        db = new Database();
        personDAO = new PersonDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
        token = new AuthToken("12-34-56","farrnacle");
        authTokenDAO.create(token);

        person1 = new Person("12345","farrnacle","cannon","farr","m","982348","3408234","23409823");
        person2 = new Person("45678","farrnacle","julia","farr","f",null,null,null);
        personDAO.createPerson(person1);
        personDAO.createPerson(person2);
        db.closeConnection(true);
    }

    @Test
    public void RetrievePersonByIDSuccess()
    {
        try {
            var request = new PersonRequest(token.authToken, "12345");
            var response = service.getPersonDetails(request);
            var newPerson = new Person(response.personID,response.associatedUsername,response.firstName,response.lastName,response.gender,response.fatherID,response.motherID, response.spouseID);
            assertEquals(person1,newPerson);
        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void RetreiveAllPersonsSuccess()
    {
        try {
            var request = new PersonRequest(token.authToken,"");
            var response = service.getAllPersons(request);
            assertEquals(2,response.data.size());
            var newPerson1 = response.data.get(0);
            var newPerson2 = response.data.get(1);
            var newPerson1Obj = new Person(newPerson1.personID,newPerson1.associatedUsername,newPerson1.firstName,newPerson1.lastName,newPerson1.gender,newPerson1.fatherID,newPerson1.motherID, newPerson1.spouseID);
            var newPerson2Obj = new Person(newPerson2.personID,newPerson2.associatedUsername,newPerson2.firstName,newPerson2.lastName,newPerson2.gender,newPerson2.fatherID,newPerson2.motherID, newPerson2.spouseID);
            assertEquals(person1,newPerson1Obj);
            assertEquals(person2,newPerson2Obj);

        } catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void getAllPersonsFail()
    {
        var request = new PersonRequest("bogusauthtoken","");
        assertThrows(UnauthorizedException.class, () -> service.getAllPersons(request));
    }

    @Test
    public void getPersonFail()
    {
        var request = new PersonRequest("bogusauthtoken","12345");
        assertThrows(UnauthorizedException.class, () -> service.getPersonDetails(request));
    }

    @Test
    public void getPersonDoesNotExist()
    {
        var request = new PersonRequest(token.authToken, "000001");
        assertThrows(EntityNotFoundException.class, () -> service.getPersonDetails(request));
    }

    @AfterEach
    public void clear() throws DataAccessException
    {
        var clearService = new ClearService();
        clearService.clear();
    }
}
