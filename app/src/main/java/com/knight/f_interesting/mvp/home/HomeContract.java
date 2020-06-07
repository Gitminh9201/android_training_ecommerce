package com.knight.f_interesting.mvp.home;

public interface HomeContract {
    interface Model {

        interface OnFinishedListener {
            void onFinished();

            void onFailure(Throwable t);
        }

        void getData(OnFinishedListener onFinishedListener);
    }

    interface View {

        void showProgress();

        void hideProgress();

        void setDataToViews();

        void onResponseFailure(Throwable throwable);
    }

    interface Presenter {
        void onDestroy();

        void requestData();
    }
}
