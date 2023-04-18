package com.c3farr.familymapclient;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.c3farr.familymapclient.ui.login.LoginFragment;
import com.c3farr.familymapclient.ui.map.MapsFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity implements LoginFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Iconify.with(new FontAwesomeModule());

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentFrameLayout);

        DataCache instance = DataCache.getInstance();
        Log.d("MainActivity", "inOnCreate");
        Log.d("MainActivity", "Display Map: " + instance.displayMapFragment);


        if (fragment == null)
        {
            if (instance.displayMapFragment)
            {
                fragment = new MapsFragment();
            } else {
                instance.Clear();
                fragment = createLoginFragment();
            }

            fragmentManager.beginTransaction()
                    .add(R.id.fragmentFrameLayout,fragment)
                    .commit();
        } else {
            if (fragment instanceof LoginFragment) {
                ((LoginFragment) fragment).registerListener(this);
            } else {
                notifySwitch();
            }
        }

    }


    private Fragment createLoginFragment() {
        LoginFragment fragment = new LoginFragment();
        fragment.registerListener(this);
        return fragment;
    }

    @Override
    public void notifySwitch() {
        Log.d("Main Activity", "Notify Switch");
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        Fragment fragment = new MapsFragment();

        fragmentManager.beginTransaction()
                .replace(R.id.fragmentFrameLayout, fragment)
                .commit();
    }
}