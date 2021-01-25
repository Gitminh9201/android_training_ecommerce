package com.knight.clothes.mvp.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.clothes.R;
import com.knight.clothes.adapters.ProductItemAdapter;
import com.knight.clothes.models.Product;
import com.knight.clothes.utils.AppUtils;

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
    private int columnView = 2;

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private ProductsContract.Presenter presenter;

    public ProductsFragment() {
        this.keyword = "";
    }

    public ProductsFragment(int categoryId) {
        this.keyword = "";
        this.categoryId = categoryId;
    }

    public ProductsFragment(String keyword, int brandId, int columnView) {
        this.keyword = keyword;
        if (brandId != 0) this.brandId = brandId;
        this.columnView = columnView;
    }

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
        llLoading = view.findViewById(R.id.ll_load_products);
        rvProducts = view.findViewById(R.id.rv_products);

        products = new ArrayList<>();

        productAdapter = new ProductItemAdapter(products, view.getContext());
        rvProducts.setLayoutManager(new GridLayoutManager(view.getContext(), columnView));
        rvProducts.setAdapter(productAdapter);
        presenter = new ProductsPresenter(this);
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    public void refresh(int categoryId) {
        this.categoryId = categoryId;
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    public void refresh(String keyword) {
        this.keyword = keyword;
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    public void filter(int categoryId, int brandId, int sort) {
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.sort = sort;
        presenter.requestData(keyword, categoryId, brandId, groupId, offset, limit, sort);
    }

    public void filter(int brandId, int sort) {
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
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("ERR", throwable.getMessage());
        AppUtils.showToast(throwable.getMessage(), getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}