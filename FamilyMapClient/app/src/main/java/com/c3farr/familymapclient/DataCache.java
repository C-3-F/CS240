package com.c3farr.familymapclient;

import com.c3farr.familymapclient.http.HttpClient;
import com.c3farr.familymapclient.uiModels.EventComparator;
import com.c3farr.familymapclient.uiModels.Settings;

import java.util.TreeSet;
import java.util.HashMap;

import models.Event;
import models.Person;

public class DataCache {

    private static DataCache instance;
    public Person rootPerson;
    public String authToken;
    public Settings settings = new Settings();
    public HttpClient httpClient;
    public HashMap<String,Person> allPersons;
    public HashMap<String,TreeSet<Event>> allEvents;
    public HashMap<String, Float> eventTypeColors = new HashMap<String, Float>();



    public static DataCache getInstance() {
        if (instance == null)
        {
            instance = new DataCache();
        }
        return instance;
    }

    public TreeSet<Event> getAllSortedEvents() {
        TreeSet<Event> events = new TreeSet<Event>(new EventComparator());
        for (TreeSet<Event> eventsValue : allEvents.values()) {
            events.addAll(eventsValue);
        }
        return events;
    }

    public TreeSet<Event> getEventsByGender(String gender) {
        TreeSet<Event> outEvents = new TreeSet<Event>(new EventComparator());
        if (gender == "m") {
            for (Person person : allPersons.values()) {
                if(person.gender == "m") {
                    outEvents.addAll(allEvents.get(person.personID));
                }
            }
        } else {
            for (Person person : allPersons.values())
            {
                if (person.gender == "f") {
                    outEvents.addAll(allEvents.get(person.personID));
                }
            }
        }
        return outEvents;
    }

    public TreeSet<Event> getEventsBySide(String side) {
        TreeSet<Event> outEvents = new TreeSet<Event>(new EventComparator());
        outEvents.addAll(allEvents.get(rootPerson.personID));
        if (side == "mother") {
            getEventsBySideHelper(outEvents, rootPerson.motherID);
        } else {
            getEventsBySideHelper(outEvents, rootPerson.fatherID);
        }
        return outEvents;
    }

    private void getEventsBySideHelper(TreeSet<Event> events, String personId) {
        events.addAll(allEvents.get(personId));
        Person thisPerson = allPersons.get(personId);
        if (thisPerson.fatherID != null) {
            getEventsBySideHelper(events,thisPerson.fatherID);
        }
        if (thisPerson.motherID != null) {
            getEventsBySideHelper(events,thisPerson.motherID);
        }
        if (thisPerson.spouseID != null) {
            getEventsBySideHelper(events, thisPerson.spouseID);
        }
    }


    public Float getColor(String eventType)
    {
        Float out = eventTypeColors.get(eventType);
        if (out == null)
        {
            Float max = (float) 0.0;
            for (Float num : eventTypeColors.values())
            {
                if (num > max) { max = num; }
            }
            out = max + 30;
            eventTypeColors.put(eventType,out);
        }
        return out;
    }

    private DataCache(){ }
}
