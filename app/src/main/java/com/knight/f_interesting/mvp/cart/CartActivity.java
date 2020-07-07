package com.knight.f_interesting.mvp.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.ProductCartAdapter;
import com.knight.f_interesting.buses.CartBus;
import com.knight.f_interesting.models.Address;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.MethodPayment;
import com.knight.f_interesting.models.Order;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.mvp.address.AddressActivity;
import com.knight.f_interesting.mvp.payment_method.PaymentMethodActivity;
import com.knight.f_interesting.utils.AppUtils;
import com.knight.f_interesting.utils.Router;

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

    private Address address;
    private MethodPayment payment;

    private Activity activity;

    private void init(Activity activity) {
        this.activity = activity;
        rvCart = findViewById(R.id.rv_cart);
        txtEmpty = findViewById(R.id.txt_empty_cart);
        llLoading = findViewById(R.id.ll_load_cart);
        btnAddress = findViewById(R.id.btn_choose_address);
        btnPayment = findViewById(R.id.btn_choose_payment);
        btnContinue = findViewById(R.id.btn_continue_cart);

        products = new ArrayList<>();
        carts = new ArrayList<>();

        presenter = new CartPresenter(this, getApplicationContext());
        productAdapter = new ProductCartAdapter(getApplicationContext(), products, carts, presenter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCart.setLayoutManager(linearLayoutManager);
        rvCart.setAdapter(productAdapter);
        presenter.requestData();
    }

    private void listener(final Activity activity) {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtils.logged()) {
                    AppUtils.showDialogConfirm(getResources().getString(R.string.confirm_order), activity, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppUtils.hideDialog(getSupportFragmentManager(), getResources().getString(R.string.dialog_confirm));
                            String inputAddress = address.getProvince() + " - " + address.getDistrict()
                                    + " - " + address.getWard() + " - " + address.getAddress();
                            presenter.createOrder(new Order(address.getPhone(), 1, payment.getId(), inputAddress, "Nothings"));
                        }
                    }, getSupportFragmentManager());
                } else {
                    AppUtils.showDialogAuth(getResources()
                                    .getString(R.string.request_login_cart),
                            activity, getSupportFragmentManager());
                }
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
        if (requestCode == REQUEST_CODE_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                address = (Address) data.getSerializableExtra(AddressActivity.EXTRA_DATA);
                btnAddress.setText(address.getPhone() + " - " + address.getProvince());
                btnAddress.setTextColor(getResources().getColor(R.color.colorWhite));
                btnAddress.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                presenter.refresh(products, carts);
            }
        }
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                payment = (MethodPayment) data.getSerializableExtra(PaymentMethodActivity.EXTRA_DATA);
                btnPayment.setText(payment.getTitle());
                btnPayment.setTextColor(getResources().getColor(R.color.colorWhite));
                btnPayment.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                presenter.refresh(products, carts);
            }
        }
    }

    private void updateButton() {
        if (payment != null && address != null) {
            btnContinue.setEnabled(true);
            btnContinue.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
        } else {
            btnContinue.setEnabled(false);
            btnContinue.setBackground(getResources().getDrawable(R.drawable.bg_button_dispose));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init(this);
        listener(this);
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
        updateButton();
        if (products.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        }
        int total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * carts.get(i).getQuantity();
        }
        if (total == 0) {
            btnContinue.setText(getResources().getText(R.string.next));
            btnContinue.setEnabled(false);
            btnContinue.setBackground(getResources().getDrawable(R.drawable.bg_button_dispose));
        } else {
            btnContinue.setText(getResources().getText(R.string.next) + "(" + AppUtils.currencyVN(total) + ")");
        }
    }

    @Override
    public void setDataToView(List<Product> products, List<Cart> carts) {

        this.products = products;
        this.carts = carts;

        if (products.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
        } else productAdapter.changeData(this.products, this.carts);
    }

    @Override
    public void onOrderSuccess(Order order) {
        if (order != null && order.getId() > 0) {
            AppUtils.db.removeCart();
            CartBus.refresh();
            Router.navigator(Router.COMPLETED, activity, null);
        } else
            AppUtils.snackbarError(findViewById(android.R.id.content).getRootView(), R.id.layout_cart);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        AppUtils.snackbarError(findViewById(android.R.id.content).getRootView(), R.id.layout_cart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}