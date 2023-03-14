package handlers;

import java.io.*;
import com.sun.net.httpserver.*;

import apiContract.LoginRequest;
import exceptions.EntityNotFoundException;
import exceptions.InvalidPasswordException;
import services.LoginService;

public class LoginHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exg) throws IOException {
        this.exchange = exg;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                var request = (LoginRequest) getRequestBody(LoginRequest.class);

                var loginServce = new LoginService();
                var response = loginServce.login(request);
                if (response.success) {
                    writeObject(response);
                } else {
                    throwError(400, "There was an error logging in");
                }
            } else {
                throwError(405, "Method not allowed");
            }
        } catch (InvalidPasswordException ex) {
            throwError(400, "Error: " + ex.getMessage());
        } catch (EntityNotFoundException ex) {
            throwError(400, "Error: " +ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            throwError(500, "An Internal Error occured");
        }
    }
}
