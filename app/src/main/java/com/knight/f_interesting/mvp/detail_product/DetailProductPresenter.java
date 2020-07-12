package com.knight.f_interesting.mvp.detail_product;

import android.content.Context;

import com.knight.f_interesting.models.Product;

public class DetailProductPresenter implements DetailProductContract.Presenter, DetailProductContract.Model.OnFinishedListener {

    private DetailProductContract.View view;
    private DetailProductContract.Model model;
    private Context context;

    public DetailProductPresenter(DetailProductContract.View view, Context context){
        this.view = view;
        this.context = context;
        model = new DetailProductModel(context);
    }

    @Override
    public void onFinished(Product product) {
        if(view != null){
            view.hideProgress();
            view.setDataToView(product);
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
    public void onStatusBookmark(boolean status) {
        if(view != null){
            view.hideProgress();
            view.setButtonBookmark(status);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void changeBookmark(int id) {
        if(view != null){
            view.showProgress();
            model.makeBookmark(this, id);
        }
    }

    @Override
    public void requestData(int id) {
        if(view != null){
            view.showProgress();
            model.getData(this, id);
            model.checkBookmark(this, id);
        }
    }
}
