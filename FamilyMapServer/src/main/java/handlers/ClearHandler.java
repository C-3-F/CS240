package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

import services.ClearService;

public class ClearHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException{
        this.exchange =exg;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post"))
            {

                var clearService = new ClearService();
                var response = clearService.clear();
                if (response.success)
                {
                    writeObject(response);
                } else {
                    throwError(400, "Error: " + response.message);
                }
            } else {
                throwError(405, "Method not allowed");
            }
        } catch (Exception ex) {
            throwError(500, ex.getMessage());
        }

    }
}
