package com.c3farr.familymapclient.backgroundTasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.c3farr.familymapclient.DataCache;
import com.c3farr.familymapclient.http.HttpClient;
import com.c3farr.familymapclient.uiModels.EventComparator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import apiContract.PersonDetailsResponse;
import models.Event;
import models.Person;

public class GetPersonDetailsTask implements Runnable{
    private final Handler handler;
    private String request;
    private DataCache instance;
    private HttpClient httpClient;
    private String rootPersonId;
    private HashMap<String,Person> allPersons;
    private HashMap<String,TreeSet<Event>> allEvents;

    public GetPersonDetailsTask(Handler handler, String personId)
    {
        this.handler = handler;
        this.request = personId;
        allPersons = new HashMap<String, Person>();
        allEvents = new HashMap<String, TreeSet<Event>>();
    }

    @Override
    public void run() {
        Log.d("PersonTask","running Person");
        instance = DataCache.getInstance();
        httpClient = instance.httpClient;
        Log.d("PersonTask","httpClientCreated");
        PersonDetailsResponse response = httpClient.getPersonDetails(request);
        rootPersonId = request;
        instance.rootPerson = new Person(response.personID,response.associatedUsername,response.firstName,response.lastName,response.gender,response.fatherID,response.motherID,response.spouseID);
        GetInitDataFromServer();
        sendMessage(response);
    }

    private void sendMessage(PersonDetailsResponse data) {
        Log.d("PersonTask","sending message");
        Message message = Message.obtain();

        Bundle messageBundle = new Bundle();
        if (data == null)
        {
            messageBundle.putBoolean("success",false);
        } else {
            messageBundle.putString("firstName", data.firstName);
            messageBundle.putString("lastName",data.lastName);
            messageBundle.putString("personId",data.personID);
            messageBundle.putString("associatedUsername",data.associatedUsername);
            messageBundle.putString("fatherId",data.fatherID);
            messageBundle.putString("motherId",data.motherID);
            messageBundle.putString("spouseId",data.spouseID);
            messageBundle.putBoolean("success",data.success);
        }
        message.setData(messageBundle);
        handler.sendMessage(message);

    }


    private void GetInitDataFromServer()
    {
        ArrayList<Person> personsArray = httpClient.getAllPersons().data;
        for (Person person : personsArray) {
            allPersons.put(person.personID,person);
        }

        ArrayList<Event> eventsArray = httpClient.getAllEvents().data;
        for (Event event : eventsArray) {
            TreeSet<Event> thisPersonsEvents = allEvents.get(event.personID);
            if (thisPersonsEvents == null)
            {
                thisPersonsEvents = new TreeSet<Event>(new EventComparator());
            }
            thisPersonsEvents.add(event);
            allEvents.put(event.personID,thisPersonsEvents);
        }

        instance.allPersons = allPersons;
        instance.allEvents = allEvents;

    }
}
