package com.knight.f_interesting.mvp.person;

import android.content.Context;
import android.net.Uri;

import com.knight.f_interesting.models.User;

public interface PersonContract {
    interface View{
        void showProgress();
        void hideProgress();
        void changeButtonAuth(boolean login);
        void setData(User user);
        void onResponseFailure(Throwable throwable);
    }
    interface Model{
        interface OnFinishedListener{
            void onFinish(User user);
            void onFailure(Throwable throwable);
        }
        void getUser(OnFinishedListener onFinishedListener, Context context);
        void uploadAvatar(OnFinishedListener onFinishedListener, Uri uri);
    }
    interface Presenter{
        void requestData();
        void uploadAvatar(Uri uri);
        void logout();
        void onDestroy();
    }
}
