package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import apiContract.ErrorResponse;

import com.google.gson.Gson;

public abstract class BaseHandler implements HttpHandler {
    protected HttpExchange exchange;
    protected boolean success = false;

    protected void writeObject(Object T) throws IOException {
        var gson = new Gson();
        var out = gson.toJson(T);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
        var respBody = exchange.getResponseBody();
        var sw = new OutputStreamWriter(respBody);
        sw.write(out);
        sw.flush();
        respBody.close();
        success = true;
    }

    protected Object getRequestBody(Object T) throws IOException {
        InputStream reqBody = exchange.getRequestBody();
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(reqBody);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        String jsonString = sb.toString();

        var gson = new Gson();
        var obj = gson.fromJson(jsonString, T.getClass());
        return obj;
    }

    protected void throwError(int code, String message) throws IOException {
        if (code == 400) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        } else if (code == 401) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
        } else if (code == 403) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_FORBIDDEN, 0);
        } else if (code == 405) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD, 0);
        } else if (code == 404) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
        } else if (code == 500) {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        } else {
            this.exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
        }

        var outObj = new ErrorResponse(false, message);
        var gson = new Gson();
        var outString = gson.toJson(outObj);
        var respBody = exchange.getResponseBody();
        var sw = new OutputStreamWriter(respBody);
        sw.write(outString);
        sw.flush();
        respBody.close();
        success = false;
    }
}
