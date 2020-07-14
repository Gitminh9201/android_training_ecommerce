package com.knight.f_interesting.mvp.collection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductItemAdapter;
import com.knight.f_interesting.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment implements CollectionContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvProducts;

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private CollectionContract.Presenter presenter;

    private View view;

    private void init(View view) {
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_product_collection);
        rvProducts = view.findViewById(R.id.rv_product_collection);

        products = new ArrayList<>();

        productAdapter = new ProductItemAdapter(products, view.getContext());
        rvProducts.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        rvProducts.setAdapter(productAdapter);
        presenter = new CollectionPresenter(this, view.getContext());
        presenter.requestData();
    }

    private void listener(View view){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
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
        productAdapter.changeData(products);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(this.view.findViewById(R.id.fragment_collection), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}