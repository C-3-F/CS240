package com.c3farr.familymapclient;

import com.c3farr.familymapclient.uiModels.EventComparator;
import com.c3farr.familymapclient.uiModels.Searcher;
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

public class SearchTests {
    DataCache instance;

    public void beforeEach() {
        instance = DataCache.getInstance();
        instance.Clear();
        instance = DataCache.getInstance();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();

        try {

            String filePath = new File("").getAbsolutePath();
            FileReader reader = new FileReader(filePath + "/src/test/java/com/c3farr/familymapclient/testData.json");
            InputDataFormat data = (InputDataFormat) gson.fromJson(reader, InputDataFormat.class);

            for (Person person : data.persons) {
                if (person.associatedUsername.equals("sheila")) {
                    instance.allPersons.put(person.personID, person);
                }
            }

            HashMap<String, TreeSet<Event>> allEvents = new HashMap<>();
            for (Event event : data.events) {
                TreeSet<Event> thisPersonsEvents = allEvents.get(event.personID);
                if (thisPersonsEvents == null) {
                    thisPersonsEvents = new TreeSet<Event>(new EventComparator());
                }
                if (event.associatedUsername.equals("sheila")) {
                    thisPersonsEvents.add(event);
                }
                allEvents.put(event.personID, thisPersonsEvents);
            }

            instance.allEvents = allEvents;
            instance.currentEvents = instance.getAllSortedEvents();
            instance.rootPerson = instance.allPersons.get("Sheila_Parker");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void SearchForPerson()
    {
        beforeEach();

        String searchQuery = "Sheila";

        ArrayList<Person> personResults = Searcher.searchPersons(searchQuery);
        ArrayList<Event> eventResults = Searcher.searchEvents(searchQuery);

        Assert.assertEquals(1,personResults.size());
        Assert.assertEquals(0,eventResults.size());
    }

    @Test
    public void ShortSearchQuery() {
        beforeEach();

        String searchQuery = "a";

        ArrayList<Person> personResults = Searcher.searchPersons(searchQuery);
        ArrayList<Event> eventResults = Searcher.searchEvents(searchQuery);

        Assert.assertEquals(6,personResults.size());
        Assert.assertEquals(16,eventResults.size());
    }

    @Test
    public void SearchQueryNoResults() {
        beforeEach();

        String searchQuery = "asdf";

        ArrayList<Person> personResults = Searcher.searchPersons(searchQuery);
        ArrayList<Event> eventResults = Searcher.searchEvents(searchQuery);

        Assert.assertEquals(0,personResults.size());
        Assert.assertEquals(0,eventResults.size());
    }
}
