package com.c3farr.familymapclient.http;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.JsonReader;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import apiContract.LoginRequest;
import apiContract.LoginResponse;
import apiContract.PersonDetailsResponse;
import apiContract.PersonRequest;
import apiContract.RegisterRequest;
import apiContract.RegisterResponse;


public class HttpClient{
    private String baseUrl;
    private final Gson gson;
    private final String logTag = "httpClient";

    public HttpClient(String baseUrl)
    {
        this.baseUrl = baseUrl;
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
    }


    public LoginResponse login(LoginRequest request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + "/user/login").openConnection();
            connection.setRequestMethod("POST");
            try {
                postContent(connection,request);
                LoginResponse response = (LoginResponse) parseResponse(connection, LoginResponse.class);
                Log.d("login","gotResponse");
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            Log.e(logTag,"The URL is not formatted correctly");
        } catch (Exception ex) {
            Log.e(logTag,"There was an error logging in");
            Log.e(logTag,ex.getMessage());
        }
        return null;
    }

    public RegisterResponse register(RegisterRequest request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + "/user/register").openConnection();
            connection.setRequestMethod("POST");
            try {
                postContent(connection, request);
                RegisterResponse response = (RegisterResponse) parseResponse(connection, RegisterResponse.class);
                Log.d("registr","gotResponse");
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                connection.disconnect();
            }
        } catch (MalformedURLException ex){
                Log.e(logTag,"The Url Was formatted incorrectly");
        } catch (Exception ex){
               Log.e(logTag,ex.getMessage());
        }
        return null;
    }

    public PersonDetailsResponse getPersonDetails(PersonRequest request) {
        try {
            Log.d(logTag,"Here");
            HttpURLConnection connection = (HttpURLConnection) new URL(baseUrl + "/person/"+request.personID).openConnection();
            connection.setRequestProperty("Authorization",request.authToken);
            connection.setRequestMethod("GET");
            try {
                PersonDetailsResponse response = (PersonDetailsResponse) parseResponse(connection,PersonDetailsResponse.class);
                return response;
            } catch (Exception ex) {
                Log.e(logTag,ex.getMessage());
            } finally {
                connection.disconnect();
            }
        } catch (MalformedURLException ex) {
            Log.e(logTag,"The Url was formatted incorrectly");
        } catch (Exception ex) {
            Log.e(logTag,ex.getMessage());
        }
        return null;
    }

    private void postContent(HttpURLConnection connection, Object T)  throws IOException {
            connection.setDoOutput(true);
            connection.setChunkedStreamingMode(0);

            String json = gson.toJson(T);
            OutputStream out = new BufferedOutputStream(connection.getOutputStream());
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(json);
            writer.flush();
            writer.close();
            out.close();
            Log.d("postContent","finished postContent");
    }

    private Object parseResponse(HttpURLConnection connection, Object T) throws IOException {
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
        {
            return null;
        }
        Log.d(logTag,"parsingResponse");
        InputStream in = new BufferedInputStream(connection.getInputStream());
        Log.d(logTag,"created streams");
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(in);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        String jsonString = sb.toString();
        Log.d(logTag,"Json: " +jsonString);


        Object obj = gson.fromJson(jsonString, (Type) T);
        in.close();
        sr.close();
        return obj;
    }

}
