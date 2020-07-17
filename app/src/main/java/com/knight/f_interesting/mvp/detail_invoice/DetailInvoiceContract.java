package com.knight.f_interesting.mvp.detail_invoice;

import com.knight.f_interesting.models.Invoice;

public interface DetailInvoiceContract {
    interface Model{
        interface OnFinishedListener{
            void onCancelSuccess();
            void onGetDataFinished(Invoice invoice);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, int id);
        void cancelInvoice(OnFinishedListener onFinishedListener, int id);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(Invoice invoice);
        void cancelFinish();
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void cancelInvoice(int id);
        void requestData(int id);
    }
}
