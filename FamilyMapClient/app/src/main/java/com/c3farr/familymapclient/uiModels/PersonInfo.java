package com.c3farr.familymapclient.uiModels;

import models.Person;

public class PersonInfo extends Person {
    public String relationshipToPerson;

    public PersonInfo(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID, String relationshipToPerson) {
        super(personID,associatedUsername,firstName,lastName,gender,fatherID,motherID,spouseID);
        this.relationshipToPerson = relationshipToPerson;
    }
}
