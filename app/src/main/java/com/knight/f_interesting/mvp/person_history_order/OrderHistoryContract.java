package com.knight.f_interesting.mvp.person_history_order;

import com.knight.f_interesting.models.Order;

import java.util.List;

public interface OrderHistoryContract {
    interface Model{
        interface OnFinishedListener{
            void onLoadMoreFinished(List<Order> orders);
            void onGetDataFinished(List<Order> orders);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener);
        void getData(OnFinishedListener onFinishedListener, int offset);
        void getData(OnFinishedListener onFinishedListener, int offset, int limit);
    }

    interface Presenter{
        void requestData();
        void requestData(int offset);
        void requestData(int offset, int limit);
        void onDestroy();
    }

    interface View{
        void showProgress();
        void hideProgress();
        void setMoreData(List<Order> invoices);
        void setDataOriginal(List<Order> orders);
        void onResponseFailure(Throwable throwable);
    }
}
