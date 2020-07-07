package com.knight.f_interesting.mvp.search;

import android.app.Activity;

import java.util.List;

public interface SearchContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<String> strings);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener);
    }

    interface View{
        void showProgress();
        void hideProgress();
        Activity activity();
        void setDataToView(List<String> strings);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter{
        void onDestroy();
        void resultProducts(String key);
        void requestData();
    }
}
