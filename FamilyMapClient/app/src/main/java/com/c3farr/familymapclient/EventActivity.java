package com.c3farr.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.c3farr.familymapclient.ui.map.MapsFragment;
import com.google.android.gms.maps.MapFragment;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Log.d("EventActivity", "Trying to Create View");
        String eventId = getIntent().getExtras().getString("selectedEventId");
        Bundle bundle = new Bundle();
        bundle.putString("selectedEventId", eventId);
        bundle.putBoolean("showOptionsMenu", false);
        Fragment fragment = new MapsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.mapFragmentContainer,fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DataCache.getInstance().displayMapFragment = true;
        if (item.getItemId() == android.R.id.home){
            Intent i = new Intent(EventActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}