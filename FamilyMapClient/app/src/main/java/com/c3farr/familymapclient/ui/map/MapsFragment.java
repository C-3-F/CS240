package com.c3farr.familymapclient.ui.map;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.c3farr.familymapclient.DataCache;
import com.c3farr.familymapclient.MainActivity;
import com.c3farr.familymapclient.PersonActivity;
import com.c3farr.familymapclient.R;
import com.c3farr.familymapclient.SearchActivity;
import com.c3farr.familymapclient.SettingsActivity;
import com.c3farr.familymapclient.uiModels.EventComparator;
import com.c3farr.familymapclient.uiModels.Settings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import models.Event;
import models.Person;

public class MapsFragment extends Fragment {

    private GoogleMap map;
    private ArrayList<Polyline> lines = new ArrayList<>();
    private HashMap<String,Marker> markers = new HashMap<>();
    private TreeSet<Event> currentVisibleEvents;
    private DataCache instance;
    private Event selectedEvent;
    private String presetEventId;
    private boolean showOptionsMenu = true;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            renderMarkers(googleMap);
            googleMap.setOnMarkerClickListener(onMarkerClick);
            if (presetEventId != null)
            {
                Marker marker = markers.get(presetEventId);
                Event markerEvent = (Event) marker.getTag();
                onMarkerClick.onMarkerClick(marker);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }
            Log.d("MapsFragment","PresetEventId: " + presetEventId);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MapsFragment", "On Create!");
        super.onCreate(savedInstanceState);

        Bundle args = this.getArguments();
        if (args != null)
        {
            showOptionsMenu = args.getBoolean("showOptionsMenu");
            presetEventId = args.getString("selectedEventId");
        }

        setHasOptionsMenu(showOptionsMenu);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d("MapsFragment", "Trying to Inflate View");
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("MapsFragment", "View Created!");
        view.findViewById(R.id.eventInformation).setVisibility(View.GONE);
        view.findViewById(R.id.eventInformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), PersonActivity.class);
                i.putExtra("selectedPersonId",selectedEvent.personID);
                startActivity(i);
            }
        });
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_mapmenu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_icon:
//                Toast.makeText(this.getContext(), R.string.searchIconSelected, Toast.LENGTH_LONG).show();
                Intent searchIntent = new Intent(this.getContext(), SearchActivity.class);
                startActivity(searchIntent);
                return true;
            case R.id.settings_icon:
//                Toast.makeText(this.getContext(),R.string.settingsIconSelected,Toast.LENGTH_LONG).show();
                Intent i = new Intent(this.getContext(), SettingsActivity.class);
                startActivity(i);
                return true;
            case android.R.id.home:
                Intent home = new Intent(this.getContext(), MainActivity.class);
                home.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public GoogleMap.OnMarkerClickListener onMarkerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(@NonNull Marker marker) {
            instance = DataCache.getInstance();
            Event thisEvent = (Event) marker.getTag();
            selectedEvent = thisEvent;
            Person thisPerson = instance.allPersons.get(thisEvent.personID);
            Log.d("MarkerClick", "personID: " + thisPerson.personID);
            Log.d("MarkerClick", "personGender: " + thisPerson.gender);

            LinearLayout eventInformation = (LinearLayout) getView().findViewById(R.id.eventInformation);
            eventInformation.setVisibility(View.VISIBLE);

            //Set Text
            String firstName = thisPerson.firstName;
            String lastName = thisPerson.lastName;
            TextView eventInfoText = (TextView) getView().findViewById(R.id.eventInformationText);
            String outText = firstName + " " + lastName + " | " + thisEvent.eventType + " | " + thisEvent.city + ", " + thisEvent.country + " | " + thisEvent.year;
            eventInfoText.setText(outText);

            //Set Icon Image
            ImageView eventInfoIcon = (ImageView) getView().findViewById(R.id.eventInformationIcon);
            if (thisPerson.gender.equals("m")) {
                Drawable maleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(30);
                eventInfoIcon.setImageDrawable(maleIcon);
            } else {
                Drawable femaleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(30);
                eventInfoIcon.setImageDrawable(femaleIcon);
            }

            //Draw Lines
            clearLines();
            if (instance.settings.SpouseLines) {
                drawSpouseLines(thisEvent);
            }
            if (instance.settings.LifeStoryLines) {
                drawLifeStoryLines(thisEvent);
            }
            if (instance.settings.FamilyTreeLines) {
                drawFamilyTreeLines(thisEvent, 20);
            }

            return false;
        }
    };


    private void renderMarkers(GoogleMap googleMap) {
        Log.d("RenderMarkers", "Rendering Markers");
        DataCache instance = DataCache.getInstance();
        Settings settings = instance.settings;
        TreeSet<Event> genderEvents = new TreeSet<Event>(new EventComparator());
        TreeSet<Event> sideEvents = new TreeSet<Event>(new EventComparator());
        boolean usingSideFilter = false;
        boolean usingGenderFilter = false;
        if (settings.FathersSide) {
            sideEvents.addAll(instance.getEventsBySide("father"));
            usingSideFilter = true;
        }
        if (settings.MothersSide) {
            sideEvents.addAll(instance.getEventsBySide("mother"));
            usingSideFilter = true;
        }
        if (settings.MaleEvents) {
            genderEvents.addAll(instance.getEventsByGender("m"));
            usingGenderFilter = true;
        }
        if (settings.FemaleEvents) {
            genderEvents.addAll(instance.getEventsByGender("f"));
            usingGenderFilter = true;
        }

        TreeSet<Event> eventsToRender;
        if (usingSideFilter && usingGenderFilter) {
            sideEvents.retainAll(genderEvents);
            eventsToRender = sideEvents;
        } else if (usingSideFilter) {
            eventsToRender = sideEvents;
        } else if (usingGenderFilter) {
            eventsToRender = genderEvents;
        } else {
            eventsToRender = new TreeSet<>();
        }

        currentVisibleEvents = eventsToRender;
        instance.currentEvents = currentVisibleEvents;

        Log.d("MapsFragment", "Events Num: " + eventsToRender.size());

        for (Event event : eventsToRender) {

            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(event.latitude, event.longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(instance.getColor(event.eventType))));
            markers.put(event.eventID,marker);
            marker.setTag(event);
        }
    }


    private void drawSpouseLines(Event selectedEvent) {
        Log.d("MapsFragment", "Drawing Spouse Lines");
        Person thisPerson = instance.allPersons.get(selectedEvent.personID);
        if (thisPerson.spouseID == null) {
            return;
        }
        Event spouseEarliestEvent = getPersonsEarliestEvent(thisPerson.spouseID);
        if (currentVisibleEvents.contains(spouseEarliestEvent)) {
            drawAndRemoveLine(selectedEvent, spouseEarliestEvent, 0xffffea00, 15);
        }
    }

    private void drawLifeStoryLines(Event selectedEvent) {
        Log.d("MapsFragment", "Drawing Life Story Lines");
        TreeSet<Event> thisPersonsEvents = instance.allEvents.get(selectedEvent.personID);
        for (Event event : thisPersonsEvents) {
            Log.d("MapsFragment", "Contains?: " + currentVisibleEvents.contains(event));
            if (currentVisibleEvents.contains(event) && !event.equals(thisPersonsEvents.last())) {
                drawAndRemoveLine(event, thisPersonsEvents.higher(event), 0xff3DDC84, 15);
            }
        }

    }


    private void drawFamilyTreeLines(Event selectedEvent, float width) {
        Person thisPerson = instance.allPersons.get(selectedEvent.personID);
        Person father = instance.allPersons.get(thisPerson.fatherID);
        Person mother = instance.allPersons.get(thisPerson.motherID);

        if (father != null) {
            Event fatherEarliestEvent = getPersonsEarliestEvent(father.personID);
            if (fatherEarliestEvent != null && currentVisibleEvents.contains(fatherEarliestEvent)) {
                drawAndRemoveLine(selectedEvent, fatherEarliestEvent, 0xff0080FE, width);
                drawFamilyTreeLines(fatherEarliestEvent, width - 5);
            }
        }

        if (mother != null) {
            Event motherEarliestEvent = getPersonsEarliestEvent(mother.personID);
            if (motherEarliestEvent != null && currentVisibleEvents.contains(motherEarliestEvent)) {
                drawAndRemoveLine(selectedEvent, motherEarliestEvent, 0xff0080FE, width);
                drawFamilyTreeLines(motherEarliestEvent, width - 5);
            }
        }
    }


    private Event getPersonsEarliestEvent(String personId) {
        TreeSet<Event> personsEvents = instance.allEvents.get(personId);
        if (personsEvents.isEmpty()) {
            return null;
        }
        return personsEvents.first();
    }

    private void drawAndRemoveLine(Event startEvent, Event endEvent, float googleColor, float width) {
//         Create start and end points for the line
        LatLng startPoint = new LatLng(startEvent.latitude, startEvent.longitude);
        LatLng endPoint = new LatLng(endEvent.latitude, endEvent.longitude);
        // Add line to map by specifying its endpoints, color, and width
        PolylineOptions options = new PolylineOptions().add(startPoint).add(endPoint).color((int) googleColor).width(width);
        Polyline line = map.addPolyline(options);
        lines.add(line);
    }

    private void clearLines() {
        for (Polyline line : lines)
            line.remove();
    }

}