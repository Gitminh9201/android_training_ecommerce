package com.knight.clothes.mvp.store;

import com.knight.clothes.models.Category;

import java.util.List;

public interface StoreContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Category> categories);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener);
    }

    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(List<Category> categories);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter{
        void onDestroy();
        void requestData();
    }
}
