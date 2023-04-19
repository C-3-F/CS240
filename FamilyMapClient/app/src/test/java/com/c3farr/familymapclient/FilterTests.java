package com.c3farr.familymapclient;

import com.c3farr.familymapclient.uiModels.EventComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import models.Event;
import models.Person;
import models.User;

public class FilterTests {
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
            instance.rootPerson = instance.allPersons.get("Sheila_Parker");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Test
    public void GetAllUnfilteredEvents() {
        beforeEach();
        TreeSet<Event> allEvents = instance.getAllSortedEvents();
        Assert.assertEquals(16, allEvents.size());
    }

    @Test
    public void GetAllMaleEvents()
    {
        beforeEach();
        instance.settings.FemaleEvents = false;
        TreeSet<Event> maleEvents = instance.filterEvents();
        for (Event event : maleEvents)
        {
            Person thisEventPerson = instance.allPersons.get(event.personID);
            Assert.assertEquals("m",thisEventPerson.gender);
        }
    }

    @Test
    public void GetAllFemaleEvents()
    {
        beforeEach();
        instance.settings.MaleEvents = false;
        TreeSet<Event> femaleEvents = instance.filterEvents();
        for (Event event : femaleEvents)
        {
            Person thisEventPerson = instance.allPersons.get(event.personID);
            Assert.assertEquals("f",thisEventPerson.gender);
        }
    }

    @Test
    public void GetAllFathersSideEvents()
    {
        beforeEach();
        instance.settings.MothersSide = false;
        TreeSet<Event> fathersSideEvents = instance.filterEvents();
        ArrayList<String> fathersSidePersonIds = new ArrayList<>();
        fathersSidePersonIds.add("Sheila_Parker");
        fathersSidePersonIds.add("Blaine_McGary");
        fathersSidePersonIds.add("Ken_Rodham");
        fathersSidePersonIds.add("Mrs_Rodham");
        fathersSidePersonIds.add("Davis_Hyer");
        for (Event event : fathersSideEvents)
        {
            Person thisEventPerson = instance.allPersons.get(event.personID);
            if (!fathersSidePersonIds.contains(thisEventPerson.personID)) {
                Assert.fail();
            }
        }
    }

    @Test
    public void GetAllMothersSideEvents()
    {
        beforeEach();
        instance.settings.FathersSide = false;
        TreeSet<Event> mothersSideEvents = instance.filterEvents();
        ArrayList<String> mothersSidePersonIds = new ArrayList<>();
        mothersSidePersonIds.add("Sheila_Parker");
        mothersSidePersonIds.add("Davis_Hyer");
        mothersSidePersonIds.add("Betty_White");
        mothersSidePersonIds.add("Mrs_Jones");
        mothersSidePersonIds.add("Frank_Jones");
        for (Event event : mothersSideEvents)
        {
            Person thisEventPerson = instance.allPersons.get(event.personID);
            if (!mothersSidePersonIds.contains(thisEventPerson.personID)) {
                Assert.fail();
            }
        }
    }

    @Test
    public void AllFiltersOffEmptyList() {
        beforeEach();
        instance.settings.FathersSide = false;
        instance.settings.MothersSide = false;
        instance.settings.MaleEvents = false;
        instance.settings.FemaleEvents = false;

        TreeSet<Event> allEvents = instance.filterEvents();
        Assert.assertTrue(allEvents.isEmpty());
    }

}
