package com.knight.f_interesting.mvp.detail;

import com.knight.f_interesting.models.Product;

public interface DetailContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(Product product);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, int id);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(Product product);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData(int id);
    }
}
