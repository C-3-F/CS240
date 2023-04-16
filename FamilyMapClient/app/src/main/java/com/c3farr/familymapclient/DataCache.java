package com.c3farr.familymapclient;

import com.c3farr.familymapclient.http.HttpClient;
import com.c3farr.familymapclient.uiModels.Settings;

import models.Person;

public class DataCache {

    private static DataCache instance;
    public Person rootPerson;
    public String authToken;
    public Settings settings;
    public HttpClient httpClient;



    public static DataCache getInstance() {
        if (instance == null)
        {
            instance = new DataCache();
        }
        return instance;
    }





    private DataCache(){ }
}
