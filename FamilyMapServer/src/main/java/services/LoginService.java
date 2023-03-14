package services;

import java.util.UUID;

import apiContract.LoginRequest;
import apiContract.LoginResponse;
import dataAccess.AuthTokenDAO;
import dataAccess.UserDAO;
import exceptions.DataAccessException;
import exceptions.EntityNotFoundException;
import exceptions.InvalidPasswordException;
import models.AuthToken;
import dataAccess.Database;

/**
 * This service is responsible for authenticating the user and returning new
 * auth tokens on successful auth requests
 */
public class LoginService {

    private final UserDAO userDao;
    private final AuthTokenDAO authTokenDAO;
    public final Database db;
    private boolean success = true;

    public LoginService() throws DataAccessException {
        db = new Database();
        userDao = new UserDAO(db.getConnection());
        authTokenDAO = new AuthTokenDAO(db.getConnection());
    }

    /**
     * Logs a user in and generates a new auth token from the given request object
     * 
     * @param request The request object containing login information for a user
     * @return a response object that contains a new auth token for the logged in
     *         user
     */
    public LoginResponse login(LoginRequest request)
            throws DataAccessException, InvalidPasswordException, EntityNotFoundException {

        try {

            var user = userDao.getUserById(request.username);
            if (user == null) {
                throw new EntityNotFoundException("User does not exist");

            } else if (!request.password.equals(user.password)) {
                throw new InvalidPasswordException("INVALID PASSWORD");

            } else {
                var authToken = new AuthToken(UUID.randomUUID().toString(), user.username);
                authTokenDAO.create(authToken);
                return new LoginResponse(authToken.authToken, user.username, user.personID, true);
            }
        } catch (Exception ex) {
            success = false;
            throw ex;
        } finally {
            db.closeConnection(success);
        }
    }
}
