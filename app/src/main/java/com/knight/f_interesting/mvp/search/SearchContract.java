package com.knight.f_interesting.mvp.search;

import android.app.Activity;

import com.knight.f_interesting.models.ProductSuggest;

import java.util.List;

public interface SearchContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<ProductSuggest> suggests);
            void onFailure(Throwable throwable);
        }
        void getSuggest(OnFinishedListener onFinishedListener, String key);
        void getLocal(OnFinishedListener onFinishedListener);
    }

    interface View{
        Activity activity();
        void setDataToView(List<ProductSuggest> suggests);
        void onResponseFailure(Throwable throwable);
    }

    interface Presenter{
        void onDestroy();
        void resultProducts(String key);
        void requestSuggest(String key);
    }
}
