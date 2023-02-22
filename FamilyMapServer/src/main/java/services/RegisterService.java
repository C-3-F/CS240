package services;

import apiContract.*;

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
    public RegisterResponse register(RegisterRequest request) {
        return new RegisterResponse();
    }
}
