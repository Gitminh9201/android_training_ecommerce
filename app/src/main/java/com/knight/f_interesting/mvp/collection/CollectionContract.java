package com.knight.f_interesting.mvp.collection;

import android.content.Context;

import com.knight.f_interesting.models.Product;

import java.util.List;

public interface CollectionContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, Context context);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(List<Product> products);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData();
    }
}
