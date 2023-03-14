package services;

import java.io.IOException;
import java.util.UUID;

import apiContract.*;
import dataAccess.AuthTokenDAO;
import dataAccess.Database;
import dataAccess.UserDAO;
import exceptions.DataAccessException;
import models.AuthToken;
import models.User;

/**
 * This service is responsible for registering new users into the system.
 * Similar to the login service, it also handles basic auth and returns new auth
 * tokens on successful registrations
 */
public class RegisterService {

    /**
     * Register a new user in the system. Creates a new user in the database and
     * logs them in
     * 
     * @param request the details of a user to create including name, email, and
     *                username
     * @return a response object containing basic user information and a new auth
     *         token for the newly created user.
     */
    public RegisterResponse register(RegisterRequest request) throws DataAccessException, IOException {
        var fillService = new FillService();
        var db = new Database();
        var userDAO = new UserDAO(db.getConnection());
        var authTokenDAO = new AuthTokenDAO(db.getConnection());

        var newUser = new User(request.username, request.password, request.email, request.firstName, request.lastName,
                request.gender, null);

        userDAO.createUser(newUser);

        fillService.fill(new FillRequest(newUser.username, 4));

        newUser = userDAO.getUserById(newUser.username);

        var authToken = new AuthToken(UUID.randomUUID().toString(), request.username);
        authTokenDAO.create(authToken);

        return new RegisterResponse(authToken.authToken, request.username, newUser.personID, true);
    }
}
