package com.knight.f_interesting.mvp.collection;

import android.content.Context;

import com.knight.f_interesting.models.Product;

import java.util.List;

public class CollectionPresenter implements CollectionContract.Presenter, CollectionContract.Model.OnFinishedListener {

    private CollectionContract.Model model;
    private CollectionContract.View view;
    private Context context;

    public CollectionPresenter(CollectionContract.View view, Context context) {
        this.view = view;
        this.context = context;
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
            model.getData(this, context);
        }
    }
}
