package com.knight.f_interesting.mvp.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.BrandFilterAdapter;
import com.knight.f_interesting.adapters.CategoryStoreAdapter;
import com.knight.f_interesting.customs.RecyclerItemClickListener;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Category;
import com.knight.f_interesting.mvp.products.ProductsFragment;

import java.util.ArrayList;
import java.util.List;

public class StoreFragment extends Fragment implements StoreContract.View {

    private int sort = 0;
    private int brandId;
    private int categoryId;

    private LinearLayout llLoading;
    private RecyclerView rvCategories;
    private RecyclerView rvBrandFilter;
//    private ImageButton ivFilter;
//    private ImageButton ibCart;
    private DrawerLayout drawerFilter;
    private Chip chipInc;
    private Chip chipAbt;
    private Button btnResetFilter;
    private Button btnFilter;

    private StoreContract.Presenter presenter;

    private CategoryStoreAdapter categoryAdapter;
    private BrandFilterAdapter brandAdapter;
    private List<Category> categories;
    private List<Brand> brands;
    private View view;
    private ProductsFragment fProducts;

    FragmentManager fm;
    FragmentTransaction ft;

    private void init(View view) {
//        ivFilter = view.findViewById(R.id.ib_filter);
//        ibCart = view.findViewById(R.id.ib_cart);
        this.view = view;
        llLoading = view.findViewById(R.id.ll_load_store);
        rvCategories = view.findViewById(R.id.rv_categories_store);
        rvBrandFilter = view.findViewById(R.id.rv_brand_filter);
        drawerFilter = view.findViewById(R.id.drawer_store);
        chipAbt = view.findViewById(R.id.chip_abatement);
        chipInc = view.findViewById(R.id.chip_increment);
        btnFilter = view.findViewById(R.id.btn_filter);
        btnResetFilter = view.findViewById(R.id.btn_reset_filter);

        fm = getFragmentManager();
        ft = fm.beginTransaction();

        brands = new ArrayList<>();
        categories = new ArrayList<>();
        brandAdapter = new BrandFilterAdapter(view.getContext(), brands);
        rvBrandFilter.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBrandFilter.setAdapter(brandAdapter);
        categoryAdapter = new CategoryStoreAdapter(categories, view.getContext());
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(categoryAdapter);
        presenter = new StorePresenter(this);
        presenter.requestData();

        fProducts = new ProductsFragment( "", 0,
                0, 0, 0, 0, 0);
        ft.add(R.id.rl_products_store, fProducts);
        ft.commit();
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
            public void onLongItemClick(View view, int position) {

            }
        }));
        rvBrandFilter.addOnItemTouchListener(new RecyclerItemClickListener(view.getContext(),
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
                fProducts.filter(categoryId, brandId, sort);
            }
        });
//        ivFilter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {drawerFilter.openDrawer(GravityCompat.END);
//            }
//        });
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
    public void setDataToView(List<Category> categories, List<Brand> brands) {
        if(!categories.isEmpty() && !brands.isEmpty()){
            this.categories = categories;
            this.brands = brands;
            brandAdapter.changeData(this.brands);
            categoryAdapter.changeData(this.categories);
            brandAdapter.notifyDataSetChanged();
            categoryAdapter.notifyDataSetChanged();

            fProducts.refresh(this.categories.get(0).getId());
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