package com.knight.f_interesting.mvp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.knight.f_interesting.R;
import com.knight.f_interesting.utils.AppUtils;

public class AuthActivity extends AppCompatActivity implements View.OnClickListener, AuthContract.View {

    private final int RC_SIGN_IN = 2011;
    private GoogleSignInClient mGoogleSignInClient;
    private LoginButton mBtnLoginFacebook;
    private CallbackManager mCallbackManager;
    private LinearLayout llLoading;
    private AuthPresenter presenter;

    private void init() {
        llLoading = findViewById(R.id.ll_loading_auth);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mCallbackManager = CallbackManager.Factory.create();
        mBtnLoginFacebook = (LoginButton) findViewById(R.id.btn_login_facebook);
        presenter = new AuthPresenter(this, getApplicationContext());
    }

    private void listener() {
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("TAGGG", "User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.e("CAL", "Login canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.e("FAIL", "Login failed.");
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
        listener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInGoogleResult(task);
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInGoogleResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w("TAG", "signInResult:success email=" + account.getEmail());
            presenter.loginGoogle(account.getId(), account.getEmail(), account.getDisplayName(),
                    account.getPhotoUrl().toString(), account.getIdToken());
        } catch (ApiException e) {
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signInGoogle();
                break;
            // ...
        }
    }

    private void signInGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void pop() {
        Toast.makeText(getApplicationContext(), getResources()
                .getString(R.string.login_success), Toast.LENGTH_LONG)
                .show();
        finish();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        AppUtils.snackbarError(findViewById(android.R.id.content).getRootView(), R.id.layout_auth);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}