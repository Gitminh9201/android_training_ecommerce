package com.knight.clothes.mvp.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.knight.clothes.R;
import com.knight.clothes.adapters.CategoryStoreAdapter;
import com.knight.clothes.customs.RecyclerItemClickListener;
import com.knight.clothes.models.Category;
import com.knight.clothes.mvp.products.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment implements StoreContract.View {

    private int categoryId;

    private LinearLayout llLoading;
    private RecyclerView rvCategories;

    private StoreContract.Presenter presenter;

    private CategoryStoreAdapter categoryAdapter;
    private List<Category> categories;
    private View view;
    private ProductsFragment fProducts;

    FragmentManager fm;
    FragmentTransaction ft;

    private void init(View view) {
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_store);
        rvCategories = view.findViewById(R.id.rv_categories_store);

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        categories = new ArrayList<>();
        categoryAdapter = new CategoryStoreAdapter(categories, view.getContext());
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);
        presenter = new StorePresenter(this);
        presenter.requestData();
    }

    private void listener(final View view) {
        rvCategories.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
                rvCategories, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                categoryAdapter.changeIndex(position);
                categoryId = categories.get(position).getId();
                fProducts.refresh(categories.get(position).getId());
            }
            @Override
            public void onLongItemClick(View view, int position) {}
        }));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);

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
    public void setDataToView(List<Category> categories) {
        if(!categories.isEmpty()){
            this.categories = categories;
            categoryAdapter.changeData(this.categories);
            fProducts = new ProductsFragment(this.categories.get(0).getId());
            ft.add(R.id.rl_products_store, fProducts);
            ft.commit();
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(this.view.findViewById(R.id.fragment_store), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}