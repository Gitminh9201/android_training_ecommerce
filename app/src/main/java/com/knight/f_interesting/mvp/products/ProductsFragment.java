package com.knight.f_interesting.mvp.products;

import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductItemAdapter;
import com.knight.f_interesting.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment implements ProductsContract.View {

    private String keyword;
    private int categoryId;
    private int brandId;
    private int groupId;
    private int offset;
    private int limit;
    private int sort;

    private LinearLayout llLoading;
    private RecyclerView rvProducts;

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private ProductsContract.Presenter presenter;

    private View view;

    public ProductsFragment(String keyword, int categoryId, int brandId,
                            int groupId, int offset, int limit, int sort) {
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.groupId = groupId;
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    private void init(View view) {
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_products);
        rvProducts = view.findViewById(R.id.rv_products);

        products = new ArrayList<>();

        productAdapter = new ProductItemAdapter(products, view.getContext());
        rvProducts.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        rvProducts.setAdapter(productAdapter);
        presenter = new ProductsPresenter(this);
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    private void listener(View view){

    }

    public void refresh(int categoryId){
        this.categoryId = categoryId;
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    public void filter(int categoryId, int brandId, int sort){
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.sort = sort;
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        init(view);
        listener(view);
        return view;
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
        productAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(this.view.findViewById(R.id.fragment_products), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
}