package com.knight.f_interesting.mvp.search;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.R;
import com.knight.f_interesting.base.BaseView;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements BaseView.BaseActivity, SearchContract.View {

    private LinearLayout llLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void init() {

    }

    @Override
    public void listener(Activity activity) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public Activity activity() {
        return this;
    }

    @Override
    public void setDataToView(List<String> strings) {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}