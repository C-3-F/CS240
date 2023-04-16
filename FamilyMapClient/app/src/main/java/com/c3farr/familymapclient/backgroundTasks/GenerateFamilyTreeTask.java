package com.c3farr.familymapclient.backgroundTasks;

import com.c3farr.familymapclient.DataCache;
import com.c3farr.familymapclient.http.HttpClient;
import com.c3farr.familymapclient.uiModels.FamilyTreeNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Handler;

import models.Event;
import models.Person;

public class GenerateFamilyTreeTask implements Runnable{

    private final Handler handler;
    private DataCache instance;
    private HttpClient httpClient;
    private String rootPersonId;
    private HashMap<String,Person> allPersons;
    private HashMap<String,ArrayList<Event>> allEvents;

    public GenerateFamilyTreeTask(Handler handler, String rootPersonId){
        this.handler = handler;
        this.rootPersonId = rootPersonId;
        instance = DataCache.getInstance();
        httpClient = instance.httpClient;

    }

    @Override
    public void run() {

    }

    private void GetInitDataFromServer()
    {
        ArrayList<Person> personsArray = httpClient.getAllPersons().data;
        for (Person person : personsArray) {
            allPersons.put(person.personID,person);
        }

        ArrayList<Event> eventsArray = httpClient.getAllEvents().data;
        for (Event event : eventsArray) {
            allEvents.put(event.personID,)
        }

    }

    private FamilyTreeNode createPersonNode(String personId) {
        Person thisPerson = allPersons.
    }
}
