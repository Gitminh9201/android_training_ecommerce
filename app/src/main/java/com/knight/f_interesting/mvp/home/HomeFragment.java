package com.knight.f_interesting.mvp.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.BannerHomeAdapter;
import com.knight.f_interesting.adapters.BrandHomeAdapter;
import com.knight.f_interesting.adapters.GroupHomeAdapter;
import com.knight.f_interesting.customs.ExpandableHeightGridView;
import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Group;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter presenter;

    private ProgressBar pbLoading;
    private LinearLayout llLoading;
    private ViewPager vpBanner;
    private ExpandableHeightGridView gvBrand;
    private RecyclerView rvGroup;

    private List<Group> groups;

    private BannerHomeAdapter bannerAdapter;
    private BrandHomeAdapter brandAdapter;
    private GroupHomeAdapter groupAdapter;

    private View view;

    private void init(View view) {
        this.view = view;
        pbLoading = view.findViewById(R.id.pb_load_home);
        llLoading = view.findViewById(R.id.ll_load_home);
        vpBanner = view.findViewById(R.id.vp_banner);
        gvBrand = view.findViewById(R.id.gv_brand_home);
        rvGroup = view.findViewById(R.id.rv_group_home);

        groups = new ArrayList<>();

        groupAdapter = new GroupHomeAdapter(groups, getContext());
        bannerAdapter = new BannerHomeAdapter(getContext());
        brandAdapter = new BrandHomeAdapter(getContext());
        vpBanner.setAdapter(bannerAdapter);
        gvBrand.setAdapter(brandAdapter);
        rvGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGroup.setAdapter(groupAdapter);
        presenter = new HomePresenter(this);
        presenter.requestData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    @Override
    public void showProgress() {
        pbLoading.setVisibility(View.VISIBLE);
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        pbLoading.setVisibility(View.GONE);
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setDataToViews(List<Banner> banners, List<Brand> brands, List<Group> groups) {
        Log.e("Length", "banner:" + String.valueOf(banners.size()) + "brand:"
                + String.valueOf(brands.size()) + "group:" + String.valueOf(groups.size()));
        this.groups = groups;
        groupAdapter.changeData(this.groups);
        for (int index = 0; index < banners.size(); index++) {
            bannerAdapter.addItem(banners.get(index));
        }
        if(brands.size() > 8)
            for (int index = 0; index < 8; index++) {
                brandAdapter.addItem(brands.get(index));
            }
        else
            for (int index = 0; index < brands.size(); index++) {
                brandAdapter.addItem(brands.get(index));
            }
        groupAdapter.notifyDataSetChanged();
        brandAdapter.notifyDataSetChanged();
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Snackbar.make(this.view.findViewById(R.id.fragment_home), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
}