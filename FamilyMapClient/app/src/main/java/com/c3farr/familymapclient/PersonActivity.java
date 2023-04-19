package com.c3farr.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.c3farr.familymapclient.uiModels.PersonInfo;
import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TreeSet;

import models.Event;
import models.Person;

public class PersonActivity extends AppCompatActivity {

    private DataCache instance;
    private TreeSet<Event> thisPersonsEvents;
    private ArrayList<PersonInfo> thisPersonsFamily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PersonActivity", "Passed Person: " + getIntent().getExtras().get("selectedPersonId"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        instance = DataCache.getInstance();
        String personId = (String) getIntent().getExtras().get("selectedPersonId");
        Person thisPerson = instance.allPersons.get(personId);

        TextView personsFirstNameText = findViewById(R.id.personFirstName);
        TextView personsLastNameText = findViewById(R.id.personLastName);
        TextView personsGenderText = findViewById(R.id.personGender);

        personsFirstNameText.setText(thisPerson.firstName);
        personsLastNameText.setText(thisPerson.lastName);

        if (thisPerson.gender.equals("m"))
        {
            personsGenderText.setText(R.string.male);
        } else {
            personsGenderText.setText(R.string.female);
        }


        ExpandableListView expandableListView = findViewById(R.id.personExpandableListView);
        thisPersonsEvents = instance.allEvents.get(personId);
        thisPersonsEvents.retainAll(instance.currentEvents);

        thisPersonsFamily = PersonInfo.generatePersonsFamily(personId);

        expandableListView.setAdapter(new ExpandableListAdapter(thisPersonsEvents, thisPersonsFamily,thisPerson));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DataCache.getInstance().displayMapFragment = true;
        if (item.getItemId() == android.R.id.home){
            Intent i = new Intent(PersonActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final int EVENT_GROUP_POSITION = 0;
        private final int PERSON_GROUP_POSITION = 1;

        private ArrayList<Event> events;
        private ArrayList<PersonInfo> personInfos;
        private Person thisPerson;

        public ExpandableListAdapter(TreeSet<Event> events, ArrayList<PersonInfo> persons, Person thisPerson) {
            this.events = new ArrayList<>(events);
            this.personInfos = persons;
            this.thisPerson = thisPerson;
        }


        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.size();
                case PERSON_GROUP_POSITION:
                    return personInfos.size();
                default:
                    throw new IllegalArgumentException("Invalid Group Position");
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return getString(R.string.eventsExpandalbleListTitle);
                case PERSON_GROUP_POSITION:
                    return getString(R.string.personExpandalbleListTitle);
                default:
                    throw new IllegalArgumentException("Invalid Group Position");
            }
        }

        @Override
        public Object getChild(int groupPosition, int i) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.get(i);
                case PERSON_GROUP_POSITION:
                    return personInfos.get(i);
                default:
                    throw new IllegalArgumentException("Invalid Group Position");
            }
        }

        @Override
        public long getGroupId(int i) {
            return 0;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.list_item_title);

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.eventsExpandalbleListTitle);
                    break;
                case PERSON_GROUP_POSITION:
                    titleView.setText(R.string.personExpandalbleListTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Group Position");
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int i, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_view_item, parent, false);
                    initializeEventView(itemView,i);
                    break;
                case PERSON_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_view_item, parent, false);
                    initializePersonView(itemView,i);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid Group Position");
            }
            return itemView;
        }

        private void initializePersonView(View itemView, final int childPosition) {
            TextView personName = itemView.findViewById(R.id.personExpandableListInformationText1);
            String fullName = personInfos.get(childPosition).firstName + " " +personInfos.get(childPosition).lastName;
            personName.setText(fullName);

            TextView personRelation = itemView.findViewById(R.id.personExpandableListInformationText2);
            Log.d("ExpandableListView","PersonId: " + personInfos.get(childPosition).personID + " RelationshipToPerson: " + personInfos.get(childPosition).relationshipToPerson);
            personRelation.setText(personInfos.get(childPosition).relationshipToPerson);

            ImageView icon = itemView.findViewById(R.id.personExpandableListIcon);
            Drawable markerIcon;

            if (personInfos.get(childPosition).gender.equals("m"))
            {
                markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(15);
            } else {
                markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(15);
            }
            icon.setImageDrawable(markerIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(),PersonActivity.class);
                    i.putExtra("selectedPersonId",personInfos.get(childPosition).personID);
                    startActivity(i);
                }
            });
        }

        private void initializeEventView(View itemView, final int childPosition) {
            TextView eventInfo = itemView.findViewById(R.id.personExpandableListInformationText1);
            Event event = events.get(childPosition);
            String eventInfoText = event.eventType + " | " + event.city + ", " + event.country + " | " + event.year;
            eventInfo.setText(eventInfoText);

            TextView personName = itemView.findViewById(R.id.personExpandableListInformationText2);
            String fullName = thisPerson.firstName + " " + thisPerson.lastName;
            personName.setText(fullName);

            ImageView icon = itemView.findViewById(R.id.personExpandableListIcon);
            Drawable markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.dark_gray).sizeDp(25);
            icon.setImageDrawable(markerIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(),EventActivity.class);
                    i.putExtra("selectedEventId",events.get(childPosition).eventID);
                    startActivity(i);
                }
            });
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }
}