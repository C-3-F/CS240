package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import apiContract.LoadRequest;
import services.LoadService;

public class LoadHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException {
        exchange = exg;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                var loadService = new LoadService();
                var request = (LoadRequest) getRequestBody(LoadRequest.class);
                var response = loadService.load(request);
                if (response.success) {
                    writeObject(response);
                } else {
                    throwError(400, "Something went wrong with loading");
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
