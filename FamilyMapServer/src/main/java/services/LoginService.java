package services;

import apiContract.LoginRequest;
import apiContract.LoginResponse;

/**
 * This service is responsible for authenticating the user and returning new
 * auth tokens on successful auth requests
 */
public class LoginService {
    /**
     * Logs a user in and generates a new auth token from the given request object
     * 
     * @param request The request object containing login information for a user
     * @return a response object that contains a new auth token for the logged in
     *         user
     */
    public LoginResponse login(LoginRequest request) {
        return new LoginResponse();
    }
}
