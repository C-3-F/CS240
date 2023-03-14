package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import apiContract.RegisterRequest;
import services.RegisterService;

public class RegisterHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exg) throws IOException {
        this.exchange = exg;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                var request = (RegisterRequest) getRequestBody(RegisterRequest.class);
                var registerService = new RegisterService();

                var response = registerService.register(request);
                if (response.success) {
                    writeObject(response);
                } else {
                    throwError(400, "There was an error registering");
                }

            } else {
                throwError(405, "Method not allowed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throwError(500, ex.getMessage());
        }
    }
}
