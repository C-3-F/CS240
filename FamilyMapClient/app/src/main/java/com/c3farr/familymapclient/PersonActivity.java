package com.c3farr.familymapclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.TreeSet;

import models.Event;
import models.Person;

public class PersonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PersonActivity","Passed Person: " + getIntent().getExtras().get("selectedPersonId"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ExpandableListView expandableListView = findViewById(R.id.personExpandableListView);
    }

//    private class ExpandableListAdapter extends BaseExpandableListAdapter {
//        private TreeSet<Event> events;
//
//        public ExpandableListAdapter(TreeSet<Event> events, ArrayList<Person> persons)
//        {
//
//        }
//    }
}