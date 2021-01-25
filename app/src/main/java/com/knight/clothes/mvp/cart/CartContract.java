package com.knight.clothes.mvp.cart;

import com.knight.clothes.models.Cart;
import com.knight.clothes.models.Invoice;
import com.knight.clothes.models.Product;

import java.util.List;

public interface CartContract {
    interface Model{
        interface OnFinishedListener{
            void onFinishedGetData(List<Product> products, List<Cart> carts);
            void onFinishedCreateOrder(Invoice invoice);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, List<Cart> carts);
        void createOrder(OnFinishedListener onFinishedListener, Invoice invoice, String code);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void refresh(List<Product> products, List<Cart> carts);
        void setDataToView(List<Product> products, List<Cart> carts);
        void onOrderSuccess(Invoice invoice);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void refresh(List<Product> products, List<Cart> carts);
        void onDestroy();
        void createOrder(Invoice invoice, String code);
        void requestData();
    }
}
