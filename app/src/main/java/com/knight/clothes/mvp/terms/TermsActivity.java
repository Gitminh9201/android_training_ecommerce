package com.knight.clothes.mvp.terms;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.clothes.R;
import com.knight.clothes.base.BaseView;

public class TermsActivity extends AppCompatActivity implements BaseView.BaseActivity {

    private ImageButton ibBack;
    private TextView txtAppbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        init();
        listener(this);
    }

    @Override
    public void init() {
        ibBack = findViewById(R.id.ib_back);
        txtAppbar = findViewById(R.id.txt_title_toolbar);
        txtAppbar.setText(R.string.page_terms);
    }

    @Override
    public void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}