package com.knight.f_interesting.mvp.detail;

import android.content.Context;

import com.knight.f_interesting.models.Product;

public class DetailPresenter implements DetailContract.Presenter, DetailContract.Model.OnFinishedListener {

    private DetailContract.View view;
    private DetailContract.Model model;
    private Context context;

    public DetailPresenter(DetailContract.View view, Context context){
        this.view = view;
        this.context = context;
        model = new DetailModel(context);
    }

    @Override
    public void onFinished(Product product) {
        if(view != null)
            view.hideProgress();
        view.setDataToView(product);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null)
            view.hideProgress();
        view.onResponseFailure(throwable);
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
        }
        model.makeBookmark(this, id);
    }

    @Override
    public void requestData(int id) {
        if(view != null)
            view.showProgress();
        model.getData(this, id);
        model.checkBookmark(this, id);
    }
}
