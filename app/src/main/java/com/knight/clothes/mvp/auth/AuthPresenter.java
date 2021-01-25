package com.knight.clothes.mvp.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.knight.clothes.R;
import com.knight.clothes.buses.UserBus;
import com.knight.clothes.models.User;
import com.knight.clothes.utils.AppShared;
import com.knight.clothes.utils.AppUtils;

public class AuthPresenter implements AuthContract.Presenter, AuthContract.Model.OnFinishedListener {

    private AuthContract.View view;
    private AuthContract.Model model;
    private Context context;

    public AuthPresenter(AuthContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.model = new AuthModel();
    }

    @Override
    public void onFinished(User user) {
        if (view != null)
            view.hideProgress();
        if (user != null) {
            UserBus.publish(user);
            AppShared.setAccessToken(context, user.getToken());
            Log.e("USER", user.toString());
            view.pop();
        } else {
            AppUtils.showToast(context.getResources()
                    .getString(R.string.login_failure), context);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (view != null) {
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void loginGoogle(String id, String email, String name, String picture, String accessToken) {
        if (view != null) {
            view.showProgress();
            model.loginWithGoogle(this, id, email, name, picture, accessToken);
        }
    }
}
