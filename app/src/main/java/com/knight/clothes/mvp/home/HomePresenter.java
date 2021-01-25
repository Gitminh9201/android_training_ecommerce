package com.knight.clothes.mvp.home;

import com.knight.clothes.models.Banner;
import com.knight.clothes.models.Brand;
import com.knight.clothes.models.Coupon;
import com.knight.clothes.models.Group;

import java.util.List;

public class HomePresenter implements HomeContract.Presenter, HomeContract.Model.OnFinishedListener {

    private HomeContract.View homeView;
    private HomeContract.Model homeModel;

    public HomePresenter(HomeContract.View homeView) {
        this.homeView = homeView;
        this.homeModel = new HomeModel();
    }

    @Override
    public void onDestroy() {
        homeView = null;
    }

    @Override
    public void requestData(boolean reload) {
        if(homeView != null){
            homeView.showProgress();
            homeModel.getData(this, reload);
        }
    }

    @Override
    public void onFinished(List<Banner> banners, List<Brand> brands, List<Group> groups, List<Coupon> coupons) {
        if(homeView != null){
            homeView.hideProgress();
            homeView.setDataToViews(banners, brands, groups, coupons);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if(homeView != null){
            homeView.hideProgress();
            homeView.onResponseFailure(t);
        }
    }
}
