package com.c3farr.familymapclient.uiModels;

import models.Person;

public class PersonInfo extends Person {
    public String relationshipToPerson;


    public PersonInfo(Person person, String relationshipToPerson)
    {
        super(person.personID,person.associatedUsername,person.firstName,person.lastName,person.gender,person.fatherID,person.motherID,person.spouseID);
        this.relationshipToPerson = relationshipToPerson;
    }
}
