package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import apiContract.EventRequest;
import exceptions.UnauthorizedException;
import services.EventService;

public class EventHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException {
        exchange = exg;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                var headers = exchange.getRequestHeaders();
                if (headers.containsKey("Authorization")) {
                    String authToken = headers.getFirst("Authorization");
                    if (!authToken.isEmpty()) {
                        EventRequest request;
                        var params = exchange.getRequestURI().toString().split("/");
                        var eventService = new EventService();
                        if (params.length == 3) {
                            // ONE Event
                            request = new EventRequest(authToken, params[2]);
                            var event = eventService.getEventDetails(request);
                            writeObject(event);

                        } else {
                            // ALL EVENTS
                            request = new EventRequest(authToken, "");
                            var events = eventService.getAllEvents(request);
                            writeObject(events);
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
