package com.knight.f_interesting.mvp.detail;

import com.knight.f_interesting.models.Product;

public class DetailPresenter implements DetailContract.Presenter, DetailContract.Model.OnFinishedListener {

    private DetailContract.View view;
    private DetailContract.Model model;

    public DetailPresenter(DetailContract.View view){
        this.view = view;
        model = new DetailModel();
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
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestData(int id) {
        if(view != null)
            view.showProgress();
        model.getData(this, id);
    }
}
