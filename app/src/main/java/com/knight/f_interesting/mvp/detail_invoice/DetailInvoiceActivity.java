package com.knight.f_interesting.mvp.detail_invoice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductsInInvoiceAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.models.OrderDetail;
import com.knight.f_interesting.utils.AppContracts;
import com.knight.f_interesting.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailInvoiceActivity extends AppCompatActivity implements BaseView.BaseActivity, DetailInvoiceContract.View {
    private int id;
    private Intent intent;

    private LinearLayout llLoading;
    private RecyclerView rvProducts;
    private TextView txtAddress;
    private TextView txtPayment;
    private TextView txtTotal;
    private TextView txtDiscount;
    private TextView txtResultTotal;
    private TextView txtTitle;
    private ImageButton ibBack;
    private Button btnChange;
    private TextView txtPhone;
    private TextView txtStatus;
    private LinearLayout llIconStatus;

    private List<OrderDetail> details;
    private ProductsInInvoiceAdapter adapter;
    private DetailInvoiceContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_invoice);
        init();
        listener(this);
    }

    @Override
    public void init() {
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        llLoading = findViewById(R.id.ll_load_invoice);
        rvProducts = findViewById(R.id.rv_invoice);
        txtAddress = findViewById(R.id.txt_addres_invoice);
        txtDiscount = findViewById(R.id.txt_discount_invoice);
        txtPayment = findViewById(R.id.txt_method_payment_invoice);
        txtTotal = findViewById(R.id.txt_total_invoice);
        txtResultTotal = findViewById(R.id.txt_total_result_invoice);
        txtTitle = findViewById(R.id.txt_title_toolbar);
        txtTitle.setText(R.string.page_invoice);
        ibBack = findViewById(R.id.ib_back);
        btnChange = findViewById(R.id.btn_change_detail_invoice);
        txtPhone = findViewById(R.id.txt_phone_invoice);
        txtStatus = findViewById(R.id.txt_status_invoice);
        llIconStatus = findViewById(R.id.ll_icon_status_invoice);

        details  =new ArrayList<>();
        adapter = new ProductsInInvoiceAdapter(details);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvProducts.setLayoutManager(linearLayoutManager);
        rvProducts.setAdapter(adapter);

        presenter = new DetailInvoicePresenter(this);
        presenter.requestData(id);
    }

    @Override
    public void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("CLICK", "TRUE");
                AppUtils.showDialogConfirm(R.string.aks_cancel_order, activity, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppUtils.hideDialog(getSupportFragmentManager(), getResources().getString(R.string.dialog_confirm));
                        presenter.cancelInvoice(id);
                    }
                }, getSupportFragmentManager());
            }
        });
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
    public void setDataToView(Order order) {
        adapter.changeData(order.getDetail());
        txtAddress.setText(order.getAddress());
        txtPayment.setText(order.getPayment().getTitle());
        if(!String.valueOf(order.getDiscount()).equals("null"))txtDiscount.setText(String.valueOf(AppUtils.currencyVN(order.getDiscount())));
        else txtDiscount.setText("0");
        txtPhone.setText(order.getPhone());
        txtStatus.setText(AppContracts.orderStatus(order.getStatus()));
        llIconStatus.setBackgroundResource(AppContracts.orderStatusBackground(order.getStatus()));
        txtTotal.setText(String.valueOf(AppUtils.currencyVN(order.getTotal())));
        txtResultTotal.setText("= " + String.valueOf(AppUtils.currencyVN(order.getTotal() - order.getDiscount())));

       if( order.getStatus() < 0 || order.getStatus() > 2){
           btnChange.setEnabled(false);
           btnChange.setBackgroundResource(R.drawable.bg_button_dispose);
       }
    }

    @Override
    public void cancelFinish() {
        AppUtils.showToast(getResources().getString(R.string.canceled_invoice), getApplicationContext());
        finish();
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}