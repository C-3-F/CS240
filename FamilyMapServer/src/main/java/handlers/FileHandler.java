package handlers;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

import com.sun.net.httpserver.*;

public class FileHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            String urlPath = exchange.getRequestURI().toString();
            if (urlPath.equals("/") || urlPath == null) {
                urlPath = "/index.html";
            }

            String filePath = "web" + urlPath;
            var file = new File(filePath);
            if (file.exists()) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0);
                OutputStream response = exchange.getResponseBody();
                Files.copy(file.toPath(), response);
                response.close();
            } else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            }

        } else {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
        }
    }
}
