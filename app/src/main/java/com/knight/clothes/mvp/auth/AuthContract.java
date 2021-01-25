package com.knight.clothes.mvp.auth;

import com.knight.clothes.models.User;

public interface AuthContract {

    interface Model{
        interface OnFinishedListener{
            void onFinished(User user);
            void onFailure(Throwable throwable);
        }
        void loginWithNumberPhone(OnFinishedListener onFinishedListener, String username, String password);
        void loginWithGoogle(OnFinishedListener onFinishedListener, String id, String email, String name, String picture, String accessToken);
        void loginWithFaceBook(OnFinishedListener onFinishedListener, String id, String accessToken);
    }
    interface View{
        void showProgress();
        void hideProgress();
        void pop();
        void onResponseFailure(Throwable throwable);
    }
    interface Presenter{
        void onDestroy();
        void loginGoogle(String id, String email, String name, String picture, String accessToken);
    }
}
