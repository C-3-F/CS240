package com.c3farr.familymapclient;

import com.c3farr.familymapclient.uiModels.EventComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import models.Event;
import models.Person;

public class SortingTests {

    private DataCache instance;

    public void beforeEach() {
        instance = DataCache.getInstance();
        instance.Clear();
        instance = DataCache.getInstance();
    }


    @Test
    public void testEventsChronologically() {
        beforeEach();
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("event_1", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "birth", 2000));
        events.add(new Event("event_2", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "birth", 1950));
        events.add(new Event("event_3", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "birth", 1900));
        events.add(new Event("event_4", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "birth", 1850));

        addEvents(events);

        TreeSet<Event> sortedEvents = instance.getAllSortedEvents();
        for (Event testEvent : sortedEvents) {
            if (!testEvent.equals(sortedEvents.last())) {
                Assert.assertTrue(testEvent.year < sortedEvents.higher(testEvent).year);
            }
        }
    }

    @Test
    public void testEventsSameYear() {
        beforeEach();
        ArrayList<Event> events = new ArrayList<>();
        events.add(new Event("event_1", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "marrriage", 2000));
        events.add(new Event("event_2", "testUser", "testUser", 100.00f, 100.00f, "United States", "Los Angeles", "birth", 2000));

        addEvents(events);

        TreeSet<Event> sortedEvents = instance.getAllSortedEvents();
        System.out.println("sortedEvents Size: " + sortedEvents.size());
        Assert.assertEquals("event_2",sortedEvents.first().eventID);
    }



    private void addEvents(ArrayList<Event> eventsToAdd) {
        HashMap<String, TreeSet<Event>> allEvents = new HashMap<>();
        for (Event event : eventsToAdd) {
            TreeSet<Event> thisPersonsEvents = allEvents.get(event.personID);
            if (thisPersonsEvents == null) {
                thisPersonsEvents = new TreeSet<Event>(new EventComparator());
            }
            thisPersonsEvents.add(event);
            allEvents.put(event.personID, thisPersonsEvents);
        }

        instance.allEvents = allEvents;
    }
}
