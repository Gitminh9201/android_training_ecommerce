package com.knight.f_interesting.mvp.terms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.R;
import com.knight.f_interesting.base.BaseView;

public class TermsActivity extends AppCompatActivity implements BaseView.BaseActivity {

    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
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
                finish();
            }
        });
    }
}