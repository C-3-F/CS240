package com.c3farr.familymapclient.uiModels;

import com.c3farr.familymapclient.DataCache;

import java.util.ArrayList;

import models.Event;
import models.Person;

public class Searcher {

    public static ArrayList<Event> searchEvents(String text) {
        ArrayList<Event> outEvents = new ArrayList<>();
        DataCache instance = DataCache.getInstance();
        for (Event event : instance.getAllSortedEvents()) {
            if ((event.eventType.toLowerCase().contains(text) || event.city.toLowerCase().contains(text) || event.country.toLowerCase().contains(text) || ((Integer) event.year).toString().contains(text)) && instance.currentEvents.contains(event)) {
                outEvents.add(event);
            }
        }
        return outEvents;
    }

    public static ArrayList<Person> searchPersons(String text) {
        ArrayList<Person> outPersons = new ArrayList<>();
        DataCache instance = DataCache.getInstance();
        for (Person person : instance.allPersons.values()) {
            if (person.firstName.toLowerCase().contains(text) || person.lastName.toLowerCase().contains(text)) {
                outPersons.add(person);
            }
        }
        return outPersons;
    }
}
