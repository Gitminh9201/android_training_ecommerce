package com.knight.clothes.mvp.products;

import com.knight.clothes.models.Product;

import java.util.List;

public interface ProductsContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Product> products);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(List<Product> products);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData(String keyword, int categoryId, int brandId, int groupId, int offset, int limit, int sort);
    }
}
