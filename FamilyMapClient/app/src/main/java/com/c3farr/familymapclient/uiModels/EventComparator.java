package com.c3farr.familymapclient.uiModels;

import java.util.Comparator;

import models.Event;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event, Event t1) {
        if (event.year == t1.year) {
            return 0;
        } else if (event.year > t1.year) {
            return 1;
        } else {
            return -1;
        }
    }
}
