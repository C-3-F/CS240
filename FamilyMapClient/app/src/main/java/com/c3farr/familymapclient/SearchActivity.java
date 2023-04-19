package com.c3farr.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.c3farr.familymapclient.uiModels.Searcher;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;

import models.Event;
import models.Person;

public class SearchActivity extends AppCompatActivity {

    private final int PERSON_LIST_GROUP = 0;
    private final int EVENT_LIST_GROUP = 1;

    private ArrayList<Event> searchEvents = new ArrayList<>();
    private ArrayList<Person> searchPersons = new ArrayList<>();
    private DataCache instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        instance = DataCache.getInstance();

//        searchEvents = new ArrayList<>(instance.getEventsByGender("m"));
//        searchPersons = new ArrayList<>(instance.allPersons.values());

        Log.d("SearchActivity", "At OnCreate");
        Log.d("SearchActivity", "Events Size: " + searchEvents.size());
        Log.d("SearchActivity", "Persons Size: " + searchPersons.size());


        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                Log.d("SearchActivity", "Search Clicked! Searching...");
                searchEvents = Searcher.searchEvents(text);
                searchPersons = Searcher.searchPersons(text);

                Log.d("SearchActivity", String.format("Found %d events", searchEvents.size()));
                Log.d("SearchActivity", String.format("Found %d persons", searchPersons.size()));

                RecyclerView recyclerView = findViewById(R.id.searchRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(searchEvents, searchPersons);
                recyclerView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DataCache.getInstance().displayMapFragment = true;
        if (item.getItemId() == android.R.id.home) {
            Intent i = new Intent(SearchActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {

        private final ArrayList<Person> persons;
        private final ArrayList<Event> events;

        public RecyclerViewAdapter(ArrayList<Event> events, ArrayList<Person> persons) {
            this.persons = persons;
            this.events = events;
        }

        @Override
        public int getItemViewType(int position) {
            return position < persons.size() ? PERSON_LIST_GROUP : EVENT_LIST_GROUP;
        }

        @NonNull
        @Override
        public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.search_result_item, parent, false);
            return new SearchItemViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
            if (position < persons.size()) {
                holder.bind(persons.get(position));
            } else {
                holder.bind(events.get(position - persons.size()));
            }
        }


        @Override
        public int getItemCount() {
            return persons.size() + events.size();
        }

    }

    private class SearchItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView info1;
        private TextView info2;
        private ImageView icon;

        private final int viewType;
        private Event event;
        private Person person;


        public SearchItemViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            info1 = view.findViewById(R.id.searchResultInformationText1);
            info2 = view.findViewById(R.id.searchResultInformationText2);
            icon = view.findViewById(R.id.searchResultIcon);
        }

        public void bind(Event event) {
            Log.d("SearchActivity", "Binding Event!");
            this.event = event;
            String eventInfoText = event.eventType + " | " + event.city + ", " + event.country + " | " + event.year;
            info1.setText(eventInfoText);
            Person thisEventPerson = instance.allPersons.get(event.personID);
            String fullName = thisEventPerson.firstName + " " + thisEventPerson.lastName;
            info2.setText(fullName);

            Drawable markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_map_marker).colorRes(R.color.dark_gray).sizeDp(25);
            icon.setImageDrawable(markerIcon);

        }

        public void bind(Person person) {
            Log.d("SearchActivity", "Binding Person!");

            this.person = person;

            String fullName = person.firstName + " " + person.lastName;

            info1.setText(fullName);

            if (person.gender.equals("m")) {
                info2.setText("Male");
            } else {
                info2.setText("Female");
            }

            Drawable markerIcon;

            if (person.gender.equals("m")) {
                markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(15);
            } else {
                markerIcon = new IconDrawable(itemView.getContext(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(15);
            }
            icon.setImageDrawable(markerIcon);
        }

        @Override
        public void onClick(View view) {
            if (viewType == PERSON_LIST_GROUP) {
                Intent i = new Intent(view.getContext(), PersonActivity.class);
                i.putExtra("selectedPersonId", person.personID);
                startActivity(i);
            } else {
                Intent i = new Intent(view.getContext(), EventActivity.class);
                i.putExtra("selectedEventId", event.eventID);
                startActivity(i);
            }
        }
    }
}
