package com.c3farr.familymapclient.uiModels;

import java.util.ArrayList;

import models.Event;
import models.Person;

public class FamilyTreeNode {

    private Person person;
    private ArrayList<Event> events;
    private FamilyTreeNode spouse;
    private FamilyTreeNode father;
    private FamilyTreeNode mother;

    public FamilyTreeNode(Person person, ArrayList<Event> events, FamilyTreeNode spouse, FamilyTreeNode father, FamilyTreeNode mother) {
        this.person = person;
        this.events = events;
        this.spouse = spouse;
        this.father = father;
        this.mother = mother;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }

    public FamilyTreeNode getSpouse() {
        return spouse;
    }

    public void setSpouse(FamilyTreeNode spouse) {
        this.spouse = spouse;
    }

    public FamilyTreeNode getFather() {
        return father;
    }

    public void setFather(FamilyTreeNode father) {
        this.father = father;
    }

    public FamilyTreeNode getMother() {
        return mother;
    }

    public void setMother(FamilyTreeNode mother) {
        this.mother = mother;
    }
}
