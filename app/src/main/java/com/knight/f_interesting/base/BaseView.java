package com.knight.f_interesting.base;

import android.app.Activity;
import android.view.View;

public interface BaseView {
    interface BaseActivity{
        void init();
        void listener(Activity activity);
    }
    interface BaseFragment{
        void init(View view);
        void listener(Activity activity);
    }
}
