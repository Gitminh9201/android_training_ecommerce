package com.knight.clothes.mvp.collection;

import com.knight.clothes.models.Product;

import java.util.List;

public interface CollectionContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products);
            void onGetMoreFinished(List<Product> products);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener);
        void getData(OnFinishedListener onFinishedListener, int offset);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void showLoadMore();
        void hideLoadMore();
        void setMoreDataToView(List<Product> products);
        void setDataToView(List<Product> products);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData();
        void requestData(int offset);
    }
}
