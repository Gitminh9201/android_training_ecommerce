package com.knight.clothes.mvp.address;

import android.content.Context;

import com.knight.clothes.models.Address;

public interface AddressContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(Address data);
            void onFailure(Throwable th);
        }
        void getData(OnFinishedListener onFinishedListener, Context context);
    }
    interface Presenter{
        void onDestroy();
        void requestData();
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setData(Address data);
        void onResponseFailure(Throwable throwable);
    }
}
