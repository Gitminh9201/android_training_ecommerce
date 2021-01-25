package com.knight.clothes.mvp.result_products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.knight.clothes.R;
import com.knight.clothes.adapters.BrandFilterAdapter;
import com.knight.clothes.base.BaseView;
import com.knight.clothes.customs.RecyclerItemClickListener;
import com.knight.clothes.models.Brand;
import com.knight.clothes.mvp.products.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class ResultProductsActivity extends AppCompatActivity implements BaseView.BaseActivity {

    private String keyword;

    private ImageButton ibSearch;
    private EditText editSearch;
    private ImageButton ivFilter;
    private ImageButton ibBack;
    private TextView txtTitleFilter;

    private int sort = 0;
    private int brandId;
    private RecyclerView rvBrandFilter;
    private DrawerLayout drawerFilter;
    private Chip chipInc;
    private Chip chipAbt;
    private Button btnResetFilter;
    private Button btnFilter;
    private BrandFilterAdapter brandAdapter;
    private List<Brand> brands;

    private ProductsFragment fProducts;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_products);
        init();
        listener(this);
    }

    @Override
    public void init() {
        txtTitleFilter = findViewById(R.id.txt_title_toolbar);
        txtTitleFilter.setText(R.string.page_filter);
        ibBack = findViewById(R.id.ib_back);
        ivFilter = findViewById(R.id.iv_float_filter);
        rvBrandFilter = findViewById(R.id.rv_brand_filter);
        drawerFilter = findViewById(R.id.drawer_store);
        chipAbt = findViewById(R.id.chip_abatement);
        chipInc = findViewById(R.id.chip_increment);
        btnFilter = findViewById(R.id.btn_filter);
        btnResetFilter = findViewById(R.id.btn_reset_filter);

        brands = new ArrayList<>();
//        brands = ContentBus.brands();

        brandAdapter = new BrandFilterAdapter(getApplicationContext(), brands);
        rvBrandFilter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvBrandFilter.setAdapter(brandAdapter);

        ibSearch = findViewById(R.id.ib_search_toolbar);
        editSearch = findViewById(R.id.edit_search);
        editSearch.clearFocus();
        ivFilter = findViewById(R.id.iv_float_filter);
        drawerFilter = findViewById(R.id.drawer_store);

        intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        if(keyword != null) editSearch.setText(keyword);
        if(intent.getStringExtra("brandID") != null)brandId = (int) Integer.parseInt(intent.getStringExtra("brandID"));

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fProducts = new ProductsFragment(keyword, brandId, 3);
        ft.add(R.id.rl_products_result, fProducts);
        ft.commit();
    }

    @Override
    public void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.clearFocus();
//                editSearch.setFocusableInTouchMode(false);
//                editSearch.setFocusable(false);
//                editSearch.setFocusableInTouchMode(true);
//                editSearch.setFocusable(true);
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                fProducts.refresh(editSearch.getText().toString());
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                editSearch.clearFocus();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    fProducts.refresh(editSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {drawerFilter.openDrawer(GravityCompat.END);
            }
        });
        rvBrandFilter.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                rvBrandFilter, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                brandAdapter.changeIndex(position);
                brandId = brands.get(position).getId();
            }

            @Override
            public void onLongItemClick(View view, int position) {}
        }));
        chipInc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && chipAbt.isChecked())
                    chipAbt.setChecked(false);
                if(isChecked)
                    sort = 1;
                else if(!chipAbt.isChecked())
                    sort = 0;
            }
        });
        chipAbt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && chipInc.isChecked())
                    chipInc.setChecked(false);
                if(isChecked)
                    sort = 2;
                else if(!chipInc.isChecked())
                    sort = 0;
            }
        });
        btnResetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chipAbt.isChecked())
                    chipAbt.setChecked(false);
                if(chipInc.isChecked())
                    chipInc.setChecked(false);
                brandAdapter.changeIndex(-1);
                sort = 0;
                brandId = 0;
                drawerFilter.closeDrawer(GravityCompat.END);
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.clearFocus();
                drawerFilter.closeDrawer(GravityCompat.END);
                fProducts.filter(brandId, sort);
            }
        });
    }
}