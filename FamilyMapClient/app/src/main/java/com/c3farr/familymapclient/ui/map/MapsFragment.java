package com.c3farr.familymapclient.ui.map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.c3farr.familymapclient.R;
import com.c3farr.familymapclient.databinding.FragmentMapsBinding;
import com.c3farr.familymapclient.uiModels.EventComparator;
import com.c3farr.familymapclient.uiModels.Settings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.TreeSet;

import models.Event;
import models.Person;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
//            LatLng sydney = new LatLng(-34, 151);
//            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            renderMarkers(googleMap);

            googleMap.setOnMarkerClickListener(onMarkerClick);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("MapsFragment", "On Create!");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_mapmenu,menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.search_icon:
                Toast.makeText(this.getContext(),R.string.searchIconSelected,Toast.LENGTH_LONG).show();
                return true;
            case R.id.settings_icon:
                Toast.makeText(this.getContext(),R.string.settingsIconSelected,Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public GoogleMap.OnMarkerClickListener onMarkerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(@NonNull Marker marker) {
            DataCache instance = DataCache.getInstance();
            Event thisEvent = (Event) marker.getTag();
            Person thisPerson = instance.allPersons.get(thisEvent.personID);
//            Log.d("MarkerClick","personID: " +thisPerson.personID);
//            Log.d("MarkerClick","personGender: "+ thisPerson.gender);

            LinearLayout eventInformation = (LinearLayout) getView().findViewById(R.id.eventInformation);
            eventInformation.setVisibility(View.VISIBLE);

            String firstName = thisPerson.firstName;
            String lastName = thisPerson.lastName;
            TextView eventInfoText = (TextView) getView().findViewById(R.id.eventInformationText);
            String outText = firstName + " " + lastName + " | " + thisEvent.eventType + " | " + thisEvent.city + ", " + thisEvent.country + " | " + thisEvent.year;
            eventInfoText.setText(outText);

            ImageView eventInfoIcon = (ImageView) getView().findViewById(R.id.eventInformationIcon);
            if (thisPerson.gender == "m") {
                Drawable maleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.male_icon).sizeDp(30);
                eventInfoIcon.setImageDrawable(maleIcon);
            } else {
                Drawable femaleIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.female_icon).sizeDp(30);
                eventInfoIcon.setImageDrawable(femaleIcon);
            }
            return false;
        }
    };


    private void renderMarkers(GoogleMap googleMap) {
        Log.d("RenderMarkers","Rendering Markers");
        DataCache instance = DataCache.getInstance();
        Settings settings = instance.settings;
        TreeSet<Event> eventsToRender = new TreeSet<Event>(new EventComparator());
        eventsToRender = instance.getAllSortedEvents();
//        if (settings.FathersSide) {
//            eventsToRender.addAll(instance.getEventsBySide("father"));
//        }
//        if (settings.MothersSide) {
//            eventsToRender.addAll(instance.getEventsBySide("mother"));
//        }
//        if (settings.MaleEvents) {
//            eventsToRender.addAll(instance.getEventsByGender("m"));
//        }
//        if (settings.FemaleEvents) {
//            eventsToRender.addAll(instance.getEventsByGender("f"));
//        }
        for (Event event : eventsToRender)
        {

            Marker marker = googleMap.addMarker(
                    new MarkerOptions()
                            .position(new LatLng(event.latitude,event.longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(instance.getColor(event.eventType))));
            marker.setTag(event);
        }
    }



}