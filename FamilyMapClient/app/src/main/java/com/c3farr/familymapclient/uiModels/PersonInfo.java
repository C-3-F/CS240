package com.c3farr.familymapclient.uiModels;

import com.c3farr.familymapclient.DataCache;

import java.util.ArrayList;

import models.Person;

public class PersonInfo extends Person {

    public String relationshipToPerson;

    public PersonInfo(Person person, String relationshipToPerson)
    {
        super(person.personID,person.associatedUsername,person.firstName,person.lastName,person.gender,person.fatherID,person.motherID,person.spouseID);
        this.relationshipToPerson = relationshipToPerson;
    }

    public static ArrayList<PersonInfo> generatePersonsFamily(String personId) {
        DataCache instance = DataCache.getInstance();
        ArrayList<PersonInfo> thisPersonsFamily = new ArrayList<>();
        Person thisPerson = instance.allPersons.get(personId);
        //Find Children from all People
        for (Person person : instance.allPersons.values()) {
            if ((person.fatherID != null && person.fatherID.equals(personId)) || (person.motherID != null && person.motherID.equals(personId))) {
                thisPersonsFamily.add(new PersonInfo(person, "Child"));
            }
        }

        Person father = instance.allPersons.get(thisPerson.fatherID);
        Person mother = instance.allPersons.get(thisPerson.motherID);
        Person spouse = instance.allPersons.get(thisPerson.spouseID);
        if (father != null) {
            thisPersonsFamily.add(new PersonInfo(father, "Father"));
        }
        if (mother != null) {
            thisPersonsFamily.add(new PersonInfo(mother, "Mother"));
        }
        if (spouse != null) {
            thisPersonsFamily.add(new PersonInfo(spouse, "Spouse"));
        }
        return thisPersonsFamily;
    }


}
