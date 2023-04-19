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
    public HashMap<String,Person> allPersons = new HashMap<>();
    public HashMap<String,TreeSet<Event>> allEvents;
    public TreeSet<Event> currentEvents;
    public HashMap<String, Float> eventTypeColors = new HashMap<String, Float>();
    public boolean displayMapFragment = false;



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
                if(person.gender.equals("m")) {
                    outEvents.addAll(allEvents.get(person.personID));
                }
            }
        } else {
            for (Person person : allPersons.values())
            {
                if (person.gender.equals("f")) {
                    outEvents.addAll(allEvents.get(person.personID));
                }
            }
        }
        return outEvents;
    }

    public TreeSet<Event> getEventsBySide(String side) {
        TreeSet<Event> outEvents = new TreeSet<Event>(new EventComparator());
        outEvents.addAll(allEvents.get(rootPerson.personID));
        outEvents.addAll(allEvents.get(rootPerson.spouseID));

        if (side == "mother") {
            getEventsBySideHelper(outEvents, rootPerson.motherID, false);
        } else {
            getEventsBySideHelper(outEvents, rootPerson.fatherID, false);
        }
        return outEvents;
    }

    private void getEventsBySideHelper(TreeSet<Event> events, String personId, boolean isSpouse) {
        events.addAll(allEvents.get(personId));
        Person thisPerson = allPersons.get(personId);
        if (thisPerson.fatherID != null) {
            getEventsBySideHelper(events,thisPerson.fatherID, false);
        }
        if (thisPerson.motherID != null) {
            getEventsBySideHelper(events,thisPerson.motherID, false);
        }
        if (thisPerson.spouseID != null && !isSpouse && !thisPerson.personID.equals(rootPerson.fatherID) && !thisPerson.personID.equals(rootPerson.motherID)) {
            getEventsBySideHelper(events, thisPerson.spouseID, true);
        }
    }


    public Float getColor(String eventType)
    {
        Float out = eventTypeColors.get(eventType);
        if (out == null)
        {
            Float max = (float) -50.0;
            for (Float num : eventTypeColors.values())
            {
                if (num > max) { max = num; }
            }
            out = max + 50;
            if (out > 360f) {
                out = 0 + (out - 360f);
            }
            eventTypeColors.put(eventType,out);
        }
        return out;
    }


    public TreeSet<Event> filterEvents()
    {
        TreeSet<Event> genderEvents = new TreeSet<Event>(new EventComparator());
        TreeSet<Event> sideEvents = new TreeSet<Event>(new EventComparator());
        boolean usingSideFilter = false;
        boolean usingGenderFilter = false;
        if (settings.FathersSide) {
            sideEvents.addAll(instance.getEventsBySide("father"));
            usingSideFilter = true;
        }
        if (settings.MothersSide) {
            sideEvents.addAll(instance.getEventsBySide("mother"));
            usingSideFilter = true;
        }
        if (settings.MaleEvents) {
            genderEvents.addAll(instance.getEventsByGender("m"));
            usingGenderFilter = true;
        }
        if (settings.FemaleEvents) {
            genderEvents.addAll(instance.getEventsByGender("f"));
            usingGenderFilter = true;
        }

        TreeSet<Event> eventsToRender;
        if (usingSideFilter && usingGenderFilter) {
            sideEvents.retainAll(genderEvents);
            eventsToRender = sideEvents;
        } else if (usingSideFilter) {
            eventsToRender = sideEvents;
        } else if (usingGenderFilter) {
            eventsToRender = genderEvents;
        } else {
            eventsToRender = new TreeSet<>();
        }

        currentEvents = eventsToRender;
        return currentEvents;
    }

    public void Clear()
    {
        instance = null;
    }

    private DataCache(){ }
}
