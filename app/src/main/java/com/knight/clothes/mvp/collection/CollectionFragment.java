package com.knight.clothes.mvp.collection;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.knight.clothes.R;
import com.knight.clothes.adapters.ProductItemAdapter;
import com.knight.clothes.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CollectionFragment extends Fragment implements CollectionContract.View {

    private int THRESHOLD = 6;

    private LinearLayout llLoading;
    private RecyclerView rvProducts;

    private ProductItemAdapter productAdapter;

    private List<Product> products;
    private CollectionContract.Presenter presenter;

    private View view;

    private LinearLayout llLoadMore;
    private boolean isLoading;

    private void init(View view) {
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_product_collection);
        rvProducts = view.findViewById(R.id.rv_product_collection);
        llLoadMore = view.findViewById(R.id.ll_load_more_grid);
        isLoading = false;

        products = new ArrayList<>();

        productAdapter = new ProductItemAdapter(products, view.getContext());
        rvProducts.setLayoutManager(new GridLayoutManager(view.getContext(), 2));
        rvProducts.setAdapter(productAdapter);
        presenter = new CollectionPresenter(this);
        presenter.requestData();
    }

    private void listener(View view) {
        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            GridLayoutManager layoutManager = (GridLayoutManager) rvProducts.getLayoutManager();

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && llLoading.getVisibility() == View.GONE && layoutManager.getItemCount()
                        <= (layoutManager.findLastVisibleItemPosition() + THRESHOLD)){
                    presenter.requestData(layoutManager.getItemCount());
                    isLoading = true;
                }
            }
        });
    }

    private void setLoaded(){
        this.isLoading = false;
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
    public void showLoadMore() {
        llLoadMore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadMore() {
        llLoadMore.setVisibility(View.GONE);
    }

    @Override
    public void setMoreDataToView(final List<Product> rs) {
        if(!rs.isEmpty()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideLoadMore();
                    setLoaded();
                    products.addAll(rs);
                    productAdapter.notifyItemInserted(products.size());
                }
            }, 500);
        }
        else new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadMore();
            }
        }, 1000);
    }

    @Override
    public void setDataToView(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            this.products = products;
            productAdapter.changeData(products);
        }
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