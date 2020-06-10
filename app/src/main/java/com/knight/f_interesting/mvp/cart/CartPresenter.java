package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Product;

import java.util.Arrays;
import java.util.List;

public class CartPresenter implements CartContract.Presenter, CartContract.Model.OnFinishedListener {

    private CartContract.View view;
    private CartContract.Model model;

    public CartPresenter(CartContract.View view){
        this.view = view;
        model = new CartModel();
    }

    @Override
    public void onFinished(List<Product> products) {
        if(view != null)
            view.hideProgress();
        view.setDataToView(products);
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
    public void requestData(Arrays ids) {
        if(view != null)
            view.showProgress();
        model.getData(this, ids);
    }
}
