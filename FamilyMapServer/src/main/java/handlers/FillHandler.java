package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import apiContract.FillRequest;
import services.FillService;

public class FillHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException {
        exchange = exg;
        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                var uri = exchange.getRequestURI().toString();
                var params = uri.split("/");
                FillRequest request;
                if (params.length == 4) {
                    request = new FillRequest(params[2], Integer.parseInt(params[3]));
                } else {
                    request = new FillRequest(params[2], 4);
                }

                var fillService = new FillService();
                var response = fillService.fill(request);
                if (response.success) {
                    writeObject(response);
                } else {
                    throwError(400, "Error: " + response.message);
                }
            } else {
                throwError(405, "Method not allowed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throwError(500, "Error: " + ex.getMessage());
        }

    }
}
