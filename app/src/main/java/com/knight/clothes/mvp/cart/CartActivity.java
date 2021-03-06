package com.knight.clothes.mvp.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.clothes.R;
import com.knight.clothes.adapters.ProductCartAdapter;
import com.knight.clothes.buses.CartBus;
import com.knight.clothes.models.Address;
import com.knight.clothes.models.Cart;
import com.knight.clothes.models.MethodPayment;
import com.knight.clothes.models.Invoice;
import com.knight.clothes.models.Product;
import com.knight.clothes.mvp.address.AddressActivity;
import com.knight.clothes.mvp.coupons.CouponsActivity;
import com.knight.clothes.mvp.payment_method.PaymentMethodActivity;
import com.knight.clothes.utils.AppUtils;
import com.knight.clothes.utils.Router;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartContract.View {

    private static final int REQUEST_CODE_ADDRESS = 0x9000;
    private static final int REQUEST_CODE_PAYMENT = 0x9001;
    private static final int REQUEST_CODE_COUPON = 0x9002;

    private RecyclerView rvCart;
    private LinearLayout llLoading;
    private List<Product> products;
    private List<Cart> carts;
    private TextView txtEmpty;
    private Button btnContinue;
    private Button btnPayment;
    private Button btnCoupon;
    private Button btnAddress;
    private ImageButton ibBack;
    private TextView txtAppbar;
    private ProductCartAdapter productAdapter;

    private CartPresenter presenter;

    private Address address;
    private MethodPayment payment;
    private String coupon;

    private Activity activity;

    private void init(Activity activity) {
        this.activity = activity;
        ibBack = findViewById(R.id.ib_back);
        txtAppbar = findViewById(R.id.txt_title_toolbar);
        txtAppbar.setText(R.string.page_cart);
        rvCart = findViewById(R.id.rv_cart);
        txtEmpty = findViewById(R.id.txt_empty_cart);
        llLoading = findViewById(R.id.ll_load_cart);
        btnAddress = findViewById(R.id.btn_choose_address);
        btnPayment = findViewById(R.id.btn_choose_payment);
        btnContinue = findViewById(R.id.btn_continue_cart);
        btnCoupon = findViewById(R.id.btn_choose_coupon);

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
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                            presenter.createOrder(new Invoice(address.getPhone(),
                                    1, payment.getId(), inputAddress, "Nothings"), coupon.toUpperCase());
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
        btnCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartActivity.this, CouponsActivity.class);
                intent.putExtra("forResult", true);
                startActivityForResult(intent, REQUEST_CODE_COUPON);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_ADDRESS) {
                address = (Address) data.getSerializableExtra(AddressActivity.EXTRA_DATA);
                btnAddress.setText(address.getPhone() + " - " + address.getProvince());
                btnAddress.setTextColor(getResources().getColor(R.color.colorWhite));
                btnAddress.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                presenter.refresh(products, carts);
            } else if (requestCode == REQUEST_CODE_PAYMENT) {
                payment = (MethodPayment) data.getSerializableExtra(PaymentMethodActivity.EXTRA_DATA);
                btnPayment.setText(payment.getTitle());
                btnPayment.setTextColor(getResources().getColor(R.color.colorWhite));
                btnPayment.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                presenter.refresh(products, carts);
            } else if (requestCode == REQUEST_CODE_COUPON) {
                coupon = data.getStringExtra(CouponsActivity.EXTRA_DATA);
                if (coupon != null && coupon.trim().length() != 0) {
                    btnCoupon.setText(coupon);
                    btnCoupon.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnCoupon.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
                }
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
    public void onOrderSuccess(Invoice invoice) {
        if (invoice != null && invoice.getId() > 0) {
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