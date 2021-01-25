package com.knight.clothes.mvp.coupons;

import com.knight.clothes.models.Coupon;

import java.util.List;

public class CouponsPresenter implements CouponsContract.Model.OnFinishedListener, CouponsContract.Presenter {

    private CouponsContract.View view;
    private CouponsContract.Model model;

    public CouponsPresenter(CouponsContract.View view){
        this.view = view;
        this.model = new CouponsModel();
    }

    @Override
    public void onFinished(List<Coupon> coupons) {
        if(view != null){
            view.hideProgress();
            view.setDataToView(coupons);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null){
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void onDestroy() {
        if(view != null){
            view = null;
        }
    }

    @Override
    public void requestData(boolean reload) {
        if(view != null){
            view.showProgress();
            model.getData(this, reload);
        }
    }
}
