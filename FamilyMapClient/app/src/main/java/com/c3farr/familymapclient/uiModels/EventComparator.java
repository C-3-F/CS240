package com.c3farr.familymapclient.uiModels;

import java.util.Comparator;

import models.Event;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event event, Event t1) {
        if (event.equals(t1)) {
            return 0;
        } else if (event.year == t1.year) {
//            return event.eventType.compareTo(t1.eventType);
            return -1;
        } else if (event.year > t1.year) {
            return 1;
        } else {
            return -1;
        }
    }
}
