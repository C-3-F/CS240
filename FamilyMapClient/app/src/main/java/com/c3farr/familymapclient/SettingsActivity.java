package com.c3farr.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.Toolbar;

import com.c3farr.familymapclient.R;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchMaterial lifeStory = findViewById(R.id.switchLifeStory);
        SwitchMaterial familyTree = findViewById(R.id.switchFamilyTree);
        SwitchMaterial spouse = findViewById(R.id.switchSpouse);
        SwitchMaterial fathersSide = findViewById(R.id.switchFathersSide);
        SwitchMaterial mothersSide = findViewById(R.id.switchMothersSide);
        SwitchMaterial male = findViewById(R.id.switchMaleEvents);
        SwitchMaterial female = findViewById(R.id.switchFemaleEvents);

        DataCache instance = DataCache.getInstance();

        lifeStory.setChecked(instance.settings.LifeStoryLines);
        familyTree.setChecked(instance.settings.FamilyTreeLines);
        spouse.setChecked(instance.settings.SpouseLines);
        fathersSide.setChecked(instance.settings.FathersSide);
        mothersSide.setChecked(instance.settings.MothersSide);
        male.setChecked(instance.settings.MaleEvents);
        female.setChecked(instance.settings.FemaleEvents);


        TableRow logOutArea = findViewById(R.id.logoutTableRow);

        lifeStory.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.LifeStoryLines = isChecked;
        }));

        familyTree.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.FamilyTreeLines = isChecked;
        }));

        spouse.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.SpouseLines = isChecked;
        }));

        fathersSide.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.FathersSide = isChecked;
        }));

        mothersSide.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.MothersSide = isChecked;
        }));

        male.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.MaleEvents = isChecked;
        }));

        female.setOnCheckedChangeListener(((compoundButton, isChecked) -> {instance.settings.FemaleEvents = isChecked;
        }));


        logOutArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCache.getInstance().displayMapFragment = false;
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        DataCache.getInstance().displayMapFragment = true;
        if (item.getItemId() == android.R.id.home){
            Intent i = new Intent(SettingsActivity.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}