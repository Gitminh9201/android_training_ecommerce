package com.knight.f_interesting.mvp.cart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductCartAdapter;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.mvp.address.AddressActivity;
import com.knight.f_interesting.mvp.payment_method.PaymentMethodActivity;
import com.knight.f_interesting.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private static final int REQUEST_CODE_ADDRESS = 0x9000;
    private static final int REQUEST_CODE_PAYMENT = 0x9001;

    private RecyclerView rvCart;
    private LinearLayout llLoading;
    private List<Product> products;
    private List<Cart> carts;
    private TextView txtEmpty;
    private Button btnContinue;
    private Button btnPayment;
    private Button btnAddress;
    private ProductCartAdapter productAdapter;

    private CartPresenter presenter;

    private void init() {
        rvCart = findViewById(R.id.rv_cart);
        txtEmpty = findViewById(R.id.txt_empty_cart);
        llLoading = findViewById(R.id.ll_load_cart);
        btnAddress = findViewById(R.id.btn_choose_address);
        btnPayment = findViewById(R.id.btn_choose_payment);
        btnContinue = findViewById(R.id.btn_continue_cart);

        products = new ArrayList<>();
        carts = new ArrayList<>();

        presenter = new CartPresenter(this);
        productAdapter = new ProductCartAdapter(getApplicationContext(), products, carts, presenter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(productAdapter);
        presenter.requestData();
    }

    private void listener(){
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Dialog dialog = new Dialog(CartActivity.this);
////                dialog.setTitle("Continue");
////                dialog.show();
            }
        });
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(
                        CartActivity.this, AddressActivity.class), REQUEST_CODE_ADDRESS);
            }
        });
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(
                        CartActivity.this, PaymentMethodActivity.class), REQUEST_CODE_PAYMENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADDRESS) {
            if(resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra(AddressActivity.EXTRA_DATA);
                Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
            }
        }
        if(requestCode == REQUEST_CODE_PAYMENT){
            if(resultCode == Activity.RESULT_OK) {
                final String result = data.getStringExtra(PaymentMethodActivity.EXTRA_DATA);
                Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        listener();
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
    public void refresh(List<Product> products, List<Cart> carts) {
        if(products.isEmpty()){
            txtEmpty.setVisibility(View.VISIBLE);
        }
        int total = 0;
        for(int i = 0; i < products.size(); i++){
            total += products.get(i).getPrice() * carts.get(i).getQuantity();
        }
        if(total == 0){
            btnContinue.setText(getResources().getText(R.string.next));
            btnContinue.setEnabled(false);
            btnContinue.setBackgroundColor(getResources().getColor(R.color.colorGrey));
        }
        else {
            btnContinue.setText(getResources().getText(R.string.next) + "("  + AppUtils.currencyVN(total) +")");
            btnContinue.setEnabled(true);
            btnContinue.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
        }
    }

    @Override
    public void setDataToView(List<Product> products, List<Cart> carts) {

        this.products = products;
        this.carts = carts;

        if(products.isEmpty()){
            txtEmpty.setVisibility(View.VISIBLE);
        }
        else productAdapter.changeData(this.products, this.carts);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        AppUtils.snackbar(findViewById(android.R.id.content).getRootView(), R.id.layout_cart);
    }
}