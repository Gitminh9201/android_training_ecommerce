package com.knight.clothes.mvp.detail_invoice;

import com.knight.clothes.models.Invoice;

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
