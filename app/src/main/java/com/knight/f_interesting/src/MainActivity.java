package com.knight.f_interesting.src;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.knight.f_interesting.R;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listens();
    }

    private void listens() {

    }

    private void init() {
        tab = findViewById(R.id.tabMain);
        pager = findViewById(R.id.pvMain);
    }
}