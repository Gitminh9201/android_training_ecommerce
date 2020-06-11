package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Product;

import java.util.List;

public interface CartContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products, List<Cart> carts);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, List<Cart> carts);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void refresh(List<Product> products, List<Cart> carts);
        void setDataToView(List<Product> products, List<Cart> carts);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void refresh(List<Product> products, List<Cart> carts);
        void onDestroy();
        void requestData();
    }
}
