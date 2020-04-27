package com.smoothswitch;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smoothswitch.service.MainService;
import com.smoothswitch.fragments.PageViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainService.updateActivity(this);

        setContentView(R.layout.activity_main);
        PageViewModel.SectionsPagerAdapter sectionsPagerAdapter =
                new PageViewModel.SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Demarrage du service
        startService(new Intent(this, MainService.class));
    }
}
