package com.knight.f_interesting.mvp;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.TabPagerMainAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.mvp.collection.CollectionFragment;
import com.knight.f_interesting.mvp.home.HomeFragment;
import com.knight.f_interesting.mvp.person.PersonFragment;
import com.knight.f_interesting.mvp.store.StoreFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BaseView.BaseActivity {

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
        listener(this);
    }

    @Override
    public void init() {
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
            Objects.requireNonNull(tabLayout.getTabAt(i)).setIcon(tabIcons[i]);
        }
    }

    @Override
    public void listener(Activity activity) {
    }
}