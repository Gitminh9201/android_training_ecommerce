package com.knight.f_interesting.mvp.cart;

import com.knight.f_interesting.models.Product;

import java.util.Arrays;
import java.util.List;

public interface CartContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, Arrays ids);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(List<Product> products);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData(Arrays ids);
    }
}
