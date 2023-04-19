package com.c3farr.familymapclient;

import com.c3farr.familymapclient.http.HttpClient;
import com.c3farr.familymapclient.uiModels.PersonInfo;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

import models.Person;

public class GenerateFamilyTests {

    ArrayList<Person> persons;
    DataCache instance;

    public void beforeEach() {
        instance = DataCache.getInstance();
        instance.Clear();
        instance = DataCache.getInstance();

        persons = new ArrayList<>();
        persons.add(new Person("person_me", "testUser", "Wilbur", "Wright", "m", "person_father", "person_mother", "person_spouse"));
        persons.add(new Person("person_spouse", "testUser", "Rosa", "Parks", "f", null, null, "person_me"));
        persons.add(new Person("person_father", "testUser", "George", "Washington", "m", null, null, "person_mother"));
        persons.add(new Person("person_mother", "testUser", "Betsy", "Ross", "f", null, null, "person_father"));
        persons.add(new Person("person_child", "testUser", "Henry", "Ford", "m", "person_me", "person_spouse", null));
        persons.add(new Person("person_child2", "testUser", "Melinda", "Gates", "f", "person_me", "person_spouse", null));

        for (Person person : persons) {
            instance.allPersons.put(person.personID, person);

        }
    }

    @Test
    public void GettingFamilyRelationshipsForPersonMe() {
        beforeEach();
        ArrayList<PersonInfo> family = PersonInfo.generatePersonsFamily("person_me");

        Assert.assertNotNull(family);
        for (PersonInfo person : family) {
            if (person.personID.contains("child")) {
                Assert.assertEquals("Child", person.relationshipToPerson);
            }
            if (person.personID.contains("spouse")) {
                Assert.assertEquals("Spouse", person.relationshipToPerson);
            }
            if (person.personID.contains("father")) {
                Assert.assertEquals("Father", person.relationshipToPerson);
            }
            if (person.personID.contains("mother")) {
                Assert.assertEquals("Mother", person.relationshipToPerson);
            }
        }
    }

    @Test
    public void GetFamilyRelationshipsForNonExistantPerson()
    {
         Assert.assertThrows(NullPointerException.class, () -> PersonInfo.generatePersonsFamily("Bogus_person"));
    }



}
