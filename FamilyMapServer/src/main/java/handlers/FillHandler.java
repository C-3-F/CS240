package handlers;

import java.io.IOException;

import com.sun.net.httpserver.*;

public class FillHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exg) throws IOException {
        exchange = exg;

    }
}
