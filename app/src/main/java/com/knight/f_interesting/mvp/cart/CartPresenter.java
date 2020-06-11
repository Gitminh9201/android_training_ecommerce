package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

public class CartPresenter implements CartContract.Presenter, CartContract.Model.OnFinishedListener {

    private CartContract.View view;
    private CartContract.Model model;

    public CartPresenter(CartContract.View view){
        this.view = view;
        model = new CartModel();
    }

    @Override
    public void onFinished(List<Product> products, List<Cart> carts) {
        if(view != null)
            view.hideProgress();
        view.setDataToView(products, carts);
        view.refresh(products, carts);
    }

    @Override
    public void onFailure(Throwable throwable) {
        if(view != null)
            view.hideProgress();
        view.onResponseFailure(throwable);
    }

    @Override
    public void refresh(List<Product> products, List<Cart> carts) {
        view.refresh(products, carts);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestData() {
        if(view != null)
            view.showProgress();
        List<Cart> carts = AppUtils.db.getCart();
        model.getData(this, carts);
    }
}
