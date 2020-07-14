package com.knight.f_interesting.mvp.coupons;

import com.knight.f_interesting.models.Coupon;

import java.util.List;

public interface CouponsContract {
    interface Model{
        interface OnFinishedListener{
            void onFinished(List<Coupon> coupons);
            void onFailure(Throwable throwable);
        }
        void getData(OnFinishedListener onFinishedListener, boolean reload);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void setDataToView(List<Coupon> coupons);
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void requestData(boolean reload);
    }
}
