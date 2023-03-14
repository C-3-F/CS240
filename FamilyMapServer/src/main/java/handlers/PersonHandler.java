package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import apiContract.PersonRequest;
import exceptions.UnauthorizedException;
import services.PersonService;

public class PersonHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException {
        exchange = exg;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                var headers = exchange.getRequestHeaders();
                if (headers.containsKey("Authorization")) {
                    String authToken = headers.getFirst("Authorization");
                    if (!authToken.isEmpty()) {
                        PersonRequest request;
                        var params = exchange.getRequestURI().toString().split("/");
                        var personService = new PersonService();
                        if (params.length == 3) {
                            // ONE PERSON
                            request = new PersonRequest(authToken, params[2]);
                            var person = personService.getPersonDetails(request);
                            writeObject(person);

                        } else {
                            // ALL PERSONS
                            request = new PersonRequest(authToken, "");
                            var persons = personService.getAllPersons(request);
                            writeObject(persons);
                        }
                    } else {
                        throwError(400, "Error: Unauthorized");
                    }
                }
            } else {
                throwError(405, "Method not allowed");
            }
        } catch (UnauthorizedException ex) {
            throwError(400, "Error: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            throwError(500, ex.getMessage());
        }
    }
}
