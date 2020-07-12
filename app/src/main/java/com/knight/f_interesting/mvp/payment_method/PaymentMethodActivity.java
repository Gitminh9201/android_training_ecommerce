package com.knight.f_interesting.mvp.payment_method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.knight.f_interesting.R;
import com.knight.f_interesting.models.MethodPayment;

public class PaymentMethodActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "DATA-PAYMENT";

    private Button btnSave;
    private RelativeLayout rlItemPayment;
    private ImageView ivChecked;
    private ImageButton ibBack;
    private TextView txtAppbar;

    private void init() {
        ibBack = findViewById(R.id.ib_back);
        txtAppbar = findViewById(R.id.txt_title_toolbar);
        txtAppbar.setText(R.string.page_choose_method_payment);
        btnSave = findViewById(R.id.btn_save_payment_method);
        rlItemPayment = findViewById(R.id.rl_payment_method);
        ivChecked = findViewById(R.id.iv_choose_payment);
    }

    private void listener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA, new MethodPayment(1, "Thanh toán khi nhận hàng"));
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
        rlItemPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ivChecked.getVisibility() == View.VISIBLE){
                    ivChecked.setVisibility(View.GONE);
                    btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_dispose));
                    btnSave.setEnabled(false);
                }
                else{
                    ivChecked.setVisibility(View.VISIBLE);
                    btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                    btnSave.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        init();
        listener();
    }
}