package com.knight.clothes.mvp.invoices;

import com.knight.clothes.models.Invoice;

import java.util.List;

public interface InvoicesContract {
    interface Model{
        interface OnFinishedListener{
            void onLoadMoreFinished(List<Invoice> invoices);
            void onGetDataFinished(List<Invoice> invoices);
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
        void setMoreData(List<Invoice> invoices);
        void setDataOriginal(List<Invoice> invoices);
        void onResponseFailure(Throwable throwable);
    }
}
