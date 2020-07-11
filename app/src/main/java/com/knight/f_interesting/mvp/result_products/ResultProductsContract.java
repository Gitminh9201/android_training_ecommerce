package com.knight.f_interesting.mvp.result_products;

import android.app.Activity;

import com.knight.f_interesting.models.Product;

import java.util.List;

public interface ResultProductsContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort);
        void getData(OnFinishedListener onFinishedListener, String keyword);
    }
    interface View{
        Activity activity();
        void showProgress();
        void hideProgress();
        void setDataToView(List<Product> products);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData(String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort);
        void requestData(String keyword);
        void refresh(String keyword);
    }
}
