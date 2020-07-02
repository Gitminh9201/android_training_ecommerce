package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.models.Product;

import java.util.List;

public interface CartContract {
    interface Model{
        interface OnFinishedListener{
            void onFinishedGetData(List<Product> products, List<Cart> carts);
            void onFinishedCreateOrder(Order order);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, List<Cart> carts);
        void createOrder(OnFinishedListener onFinishedListener, Order order);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void refresh(List<Product> products, List<Cart> carts);
        void setDataToView(List<Product> products, List<Cart> carts);
        void onOrderSuccess(Order order);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void refresh(List<Product> products, List<Cart> carts);
        void onDestroy();
        void createOrder(Order order);
        void requestData();
    }
}
