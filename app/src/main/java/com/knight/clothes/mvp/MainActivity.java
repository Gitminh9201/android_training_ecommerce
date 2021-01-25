package com.knight.clothes.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.knight.clothes.R;
import com.knight.clothes.adapters.TabPagerMainAdapter;
import com.knight.clothes.base.BaseView;
import com.knight.clothes.buses.CartBus;
import com.knight.clothes.buses.ContextBus;
import com.knight.clothes.models.Cart;
import com.knight.clothes.mvp.collection.CollectionFragment;
import com.knight.clothes.mvp.home.HomeFragment;
import com.knight.clothes.mvp.person.PersonFragment;
import com.knight.clothes.mvp.store.StoreFragment;
import com.knight.clothes.utils.Router;

import java.util.List;
import java.util.Objects;

import io.reactivex.rxjava3.functions.Consumer;

public class MainActivity extends AppCompatActivity implements BaseView.BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabPagerMainAdapter adapter;
    private ImageButton ibCart;
    private TextView txtBadge;
    private EditText editSearch;
    private int[] tabIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        refreshCart();
        setSupportActionBar(toolbar);
        listener(this);
    }

    @Override
    public void init() {
        ContextBus.publish(getApplicationContext());
        tabIcons = new int[]{
                R.drawable.ic_home,
                R.drawable.ic_shop,
                R.drawable.ic_collection,
                R.drawable.ic_person
        };

        editSearch = findViewById(R.id.edit_search);
        txtBadge = findViewById(R.id.txt_badge_cart_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        ibCart = findViewById(R.id.ib_cart_toolbar);
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

    public void refreshCart(){
        CartBus.subscribe(new Consumer<List<Cart>>() {
            @Override
            public void accept(List<Cart> carts) throws Throwable {
                if(carts.size() != 0){
                    txtBadge.setVisibility(View.VISIBLE);
                    txtBadge.setText(String.valueOf(carts.size()));
                }else
                    txtBadge.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void listener(final Activity activity) {
        ibCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.CART, activity, null);
            }
        });
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.SEARCH, activity, null);
            }
        });
    }
}