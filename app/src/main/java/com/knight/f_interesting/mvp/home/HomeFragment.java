package com.knight.f_interesting.mvp.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.BannerHomeAdapter;
import com.knight.f_interesting.adapters.BrandHomeAdapter;
import com.knight.f_interesting.adapters.CouponHomeAdapter;
import com.knight.f_interesting.adapters.GroupHomeAdapter;
import com.knight.f_interesting.customs.ExpandableHeightGridView;
import com.knight.f_interesting.models.Banner;
import com.knight.f_interesting.models.Brand;
import com.knight.f_interesting.models.Coupon;
import com.knight.f_interesting.models.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment implements HomeContract.View {

    private HomeContract.Presenter presenter;

    private ProgressBar pbLoading;
    private LinearLayout llLoading;
    private ViewPager vpBanner;
    private ExpandableHeightGridView gvBrand;
    private RecyclerView rvGroup;
    private RecyclerView rvCoupon;
    private SwipeRefreshLayout refreshLayout;

    private List<Group> groups;
    private List<Coupon> coupons;

    private BannerHomeAdapter bannerAdapter;
    private BrandHomeAdapter brandAdapter;
    private GroupHomeAdapter groupAdapter;
    private CouponHomeAdapter couponAdapter;

    private View view;
    private Timer timer;

    private void init(View view) {
        this.view = view;

        refreshLayout = view.findViewById(R.id.swiperefresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        pbLoading = view.findViewById(R.id.pb_load_home);
        llLoading = view.findViewById(R.id.ll_load_home);
        vpBanner = view.findViewById(R.id.vp_banner);
        gvBrand = view.findViewById(R.id.gv_brand_home);
        rvGroup = view.findViewById(R.id.rv_group_home);
        rvCoupon = view.findViewById(R.id.rv_coupon_home);

        groups = new ArrayList<>();
        coupons = new ArrayList<>();

        vpBanner.setScrollBarFadeDuration(500);
        groupAdapter = new GroupHomeAdapter(groups, getContext());
        bannerAdapter = new BannerHomeAdapter(getContext());
        brandAdapter = new BrandHomeAdapter(getContext());
        couponAdapter = new CouponHomeAdapter(coupons);
        vpBanner.setAdapter(bannerAdapter);
        gvBrand.setAdapter(brandAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        rvGroup.setAdapter(groupAdapter);
        rvCoupon.setLayoutManager(layoutManager);
        rvCoupon.setAdapter(couponAdapter);
        presenter = new HomePresenter(this);
        presenter.requestData(false);
    }

    private int positionPage(){
        int i = vpBanner.getCurrentItem();
        if((i + 1) >= vpBanner.getChildCount()){
            return 0;
        }
        else{
            return (i + 1);
        }
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
        listener(view);
        return view;
    }

    private void listener(final View view) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                timer.cancel();
                presenter.requestData(true);
            }
        });
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
    public void setDataToViews(List<Banner> banners, List<Brand> brands, List<Group> groups, List<Coupon> coupons) {
        refreshLayout.setRefreshing(false);
        timer = new Timer();
        this.groups = groups;
        this.coupons = coupons;
        groupAdapter.changeData(this.groups);
        couponAdapter.changeData(this.coupons);
        bannerAdapter.changeData(banners);
        brandAdapter.changeData(brands);
        try {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    vpBanner.setCurrentItem(positionPage());
                }
            }, 7000, 5000);
        }catch (Exception e){
            Log.e("ERR", e.getMessage());
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("Err", throwable.getMessage());
        Snackbar.make(this.view.findViewById(R.id.fragment_home), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        presenter.onDestroy();
    }
}