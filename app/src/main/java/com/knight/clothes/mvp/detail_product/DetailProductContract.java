package com.knight.clothes.mvp.detail_product;

import com.knight.clothes.models.Product;

public interface DetailProductContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(Product product);
            void onFailure(Throwable throwable);
            void onStatusBookmark(boolean status);
        }
        void getData(OnFinishedListener onFinishedListener, int id);
        void checkBookmark(OnFinishedListener onFinishedListener, int id);
        void makeBookmark(OnFinishedListener onFinishedListener, int id);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setButtonBookmark(boolean status);
        void setDataToView(Product product);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void changeBookmark(int id);
        void requestData(int id);
    }
}
