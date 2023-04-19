package com.c3farr.familymapclient;

import android.app.usage.UsageEvents;

import com.c3farr.familymapclient.http.HttpClient;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import apiContract.AllEventsResponse;
import apiContract.AllPersonResponse;
import apiContract.LoginRequest;
import apiContract.LoginResponse;
import apiContract.RegisterRequest;
import apiContract.RegisterResponse;
import models.Event;
import models.Person;

public class HttpClientTests {

    HttpClient httpClient;
    String username;
    String password;

    public void beforeEach() {
        httpClient = new HttpClient("http://localhost:8080");
        username = "TestUserBase";
        password = "Password";
        RegisterRequest request = new RegisterRequest(username,password,"test@test.com","Test", "User","m");
        RegisterResponse response = httpClient.register(request);
    }

    @Test
    public void RegisterNewUserSuccess() {
        beforeEach();

        String uniqueUsername = "TestUser+" + System.currentTimeMillis();
        RegisterRequest request = new RegisterRequest(uniqueUsername,"Password","test@test.com","Test", "User","m");
        RegisterResponse response = httpClient.register(request);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.authtoken);
        Assert.assertNotNull(response.personID);
        Assert.assertEquals(uniqueUsername,response.username);
        Assert.assertTrue(response.success);
    }

    @Test
    public void RegisterInvalidUser()
    {
        beforeEach();
        RegisterRequest request = new RegisterRequest("","","","","","");
        RegisterResponse response = httpClient.register(request);
        Assert.assertNull(response);
    }


    @Test
    public void LoginSuccess()
    {
        beforeEach();

        LoginRequest loginRequest = new LoginRequest(username,"Password");
        LoginResponse loginResponse = httpClient.login(loginRequest);
        Assert.assertNotNull(loginResponse);
        Assert.assertNotNull(loginResponse.authtoken);
        Assert.assertNotNull(loginResponse.personID);
        Assert.assertEquals(username,loginResponse.username);
        Assert.assertTrue(loginResponse.success);
    }

    @Test
    public void LoginInvalidPassword()
    {
        beforeEach();

        LoginRequest loginRequest = new LoginRequest(username,"NOTSAMEPASSWORD");
        LoginResponse loginResponse = httpClient.login(loginRequest);
        Assert.assertNull(loginResponse);
    }


    @Test
    public void GetEventsSucess()
    {
        beforeEach();
        LoginRequest loginRequest = new LoginRequest(username,password);
        LoginResponse loginResponse = httpClient.login(loginRequest);
        System.out.println("AuthToken: " + loginResponse.authtoken);

        httpClient.setAuthToken(loginResponse.authtoken);
        ArrayList<Event> events = httpClient.getAllEvents().data;

        Assert.assertNotNull(events);
        Assert.assertTrue(events.size() > 0);
    }

    @Test
    public void GetEventsUnauthorized()
    {
        beforeEach();
        AllEventsResponse response = httpClient.getAllEvents();

        Assert.assertNull(response);

    }

    public void GetPersonsSuccess()
    {
        beforeEach();
        LoginRequest loginRequest = new LoginRequest(username,password);
        LoginResponse loginResponse = httpClient.login(loginRequest);
        System.out.println("AuthToken: " + loginResponse.authtoken);

        httpClient.setAuthToken(loginResponse.authtoken);
        ArrayList<Person> persons = httpClient.getAllPersons().data;

        Assert.assertNotNull(persons);
        Assert.assertTrue(persons.size() > 0);
    }

    @Test
    public void GetPersonsUnauthorized()
    {
        beforeEach();
        AllPersonResponse response = httpClient.getAllPersons();

        Assert.assertNull(response);

    }
}
