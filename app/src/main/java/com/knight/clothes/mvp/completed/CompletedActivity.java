package com.knight.clothes.mvp.completed;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.clothes.R;
import com.knight.clothes.base.BaseView;
import com.knight.clothes.utils.Router;

public class CompletedActivity extends AppCompatActivity implements BaseView.BaseActivity {

    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        init();
        listener(this);
    }

    @Override
    public void init() {
        btnFinish = findViewById(R.id.btn_completed);
    }

    @Override
    public void listener(final Activity activity) {
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.navigator(Router.MAIN, activity, null);
            }
        });
    }
}