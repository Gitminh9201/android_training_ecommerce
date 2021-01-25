package com.knight.clothes.mvp.collection;

import com.knight.clothes.models.Product;

import java.util.List;

public class CollectionPresenter implements CollectionContract.Presenter, CollectionContract.Model.OnFinishedListener {

    private CollectionContract.Model model;
    private CollectionContract.View view;

    public CollectionPresenter(CollectionContract.View view) {
        this.view = view;
        this.model = new CollectionModel();
    }

    @Override
    public void onFinished(List<Product> products) {
        if (view != null) {
            view.hideProgress();
            view.setDataToView(products);
        }
    }

    @Override
    public void onGetMoreFinished(List<Product> products) {
        if(view != null){
            view.setMoreDataToView(products);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (view != null) {
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestData() {
        if (view != null) {
            view.showProgress();
            model.getData(this);
        }
    }

    @Override
    public void requestData(int offset) {
        if (view != null) {
            view.showLoadMore();
            model.getData(this, offset);
        }
    }
}
