package com.knight.f_interesting.mvp.result_products;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.BrandFilterAdapter;
import com.knight.f_interesting.adapters.ProductItemAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.customs.RecyclerItemClickListener;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.Router;

import java.util.ArrayList;
import java.util.List;

public class ResultProductsActivity extends AppCompatActivity implements BaseView.BaseActivity, ResultProductsContract.View {

    private String keyword;

    private LinearLayout llLoading;
    private RecyclerView rvProducts;
    private ImageButton ibSearch;
    private EditText editSearch;
    private ImageButton ivFilter;
    private ImageButton ibBack;

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private ResultProductsContract.Presenter presenter;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_products);
        init();
        listener(this);
    }

    @Override
    public void init() {
        ibBack = findViewById(R.id.ib_back);
        ivFilter = findViewById(R.id.iv_float_filter);
        rvBrandFilter = findViewById(R.id.rv_brand_filter);
        drawerFilter = findViewById(R.id.drawer_store);
        chipAbt = findViewById(R.id.chip_abatement);
        chipInc = findViewById(R.id.chip_increment);
        btnFilter = findViewById(R.id.btn_filter);
        btnResetFilter = findViewById(R.id.btn_reset_filter);

        brands = new ArrayList<>();

        brandAdapter = new BrandFilterAdapter(getApplicationContext(), brands);
        rvBrandFilter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvBrandFilter.setAdapter(brandAdapter);

        llLoading = findViewById(R.id.ll_load_products);
        rvProducts = findViewById(R.id.rv_products);
        ibSearch = findViewById(R.id.ib_search_toolbar);
        editSearch = findViewById(R.id.edit_search);
        editSearch.clearFocus();
        ivFilter = findViewById(R.id.iv_float_filter);
        drawerFilter = findViewById(R.id.drawer_store);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        if(keyword != null) editSearch.setText(keyword);
        products = new ArrayList<>();

        productAdapter = new ProductItemAdapter(products, getApplicationContext());
        rvProducts.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        rvProducts.setAdapter(productAdapter);
        presenter = new ResultProductsPresenter(this);
        presenter.requestData(keyword);
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
                Router.navigator(Router.RESULT_PRODUCTS, activity, new String[]{editSearch.getText().toString(), "true"});
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.refresh(editSearch.getText().toString());
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
            public void onLongItemClick(View view, int position) {

            }
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
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerFilter.closeDrawer(GravityCompat.END);
//                fProducts.filter(categoryId, brandId, sort);
            }
        });
        ivFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {drawerFilter.openDrawer(GravityCompat.END);
            }
        });
    }

    @Override
    public Activity activity() {
        return this;
    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToView(List<Product> products) {
        this.products = products;
        productAdapter.changeData(this.products);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(findViewById(R.id.layout_result_products), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}