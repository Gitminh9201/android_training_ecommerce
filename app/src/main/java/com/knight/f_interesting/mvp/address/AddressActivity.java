package com.knight.f_interesting.mvp.address;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.knight.f_interesting.R;

public class AddressActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "DATA - ADDRESS";

    private Button btnSave;

    private void init(){
        btnSave = findViewById(R.id.btn_save_address);
    }

    private void listener(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, "Some interesting data from address!");
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        init();
        listener();
    }
}