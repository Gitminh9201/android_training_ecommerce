package com.knight.f_interesting.mvp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.TabPagerMainAdapter;
import com.knight.f_interesting.mvp.collection.CollectionFragment;
import com.knight.f_interesting.mvp.home.HomeFragment;
import com.knight.f_interesting.mvp.person.PersonFragment;
import com.knight.f_interesting.mvp.store.StoreFragment;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabPagerMainAdapter adapter;
    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_shop,
            R.drawable.ic_collection,
            R.drawable.ic_person
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        listens();
    }

    private void listens() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("Sel", String.valueOf(tab.getPosition()));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.e("Un", String.valueOf(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.e("ReSel", String.valueOf(tab.getPosition()));
            }
        });
    }

    private void init() {
        tabLayout = findViewById(R.id.tab_main);
        viewPager = findViewById(R.id.vp_main);
        adapter = new TabPagerMainAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new StoreFragment());
        adapter.addFragment(new CollectionFragment());
        adapter.addFragment(new PersonFragment());

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < tabIcons.length; i++){
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }
}