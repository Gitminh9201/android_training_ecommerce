package com.knight.f_interesting.mvp.result_products;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductItemAdapter;
import com.knight.f_interesting.base.BaseView;
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

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private ResultProductsContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_products);
        init();
        listener(this);
    }

    @Override
    public void init() {
        llLoading = findViewById(R.id.ll_load_products);
        rvProducts = findViewById(R.id.rv_products);
        ibSearch = findViewById(R.id.ib_search_toolbar);
        editSearch = findViewById(R.id.edit_search);

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
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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