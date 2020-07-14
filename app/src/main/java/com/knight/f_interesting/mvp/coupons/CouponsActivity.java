package com.knight.f_interesting.mvp.coupons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.CouponAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.customs.RecyclerItemClickListener;
import com.knight.f_interesting.models.Coupon;

import java.util.ArrayList;
import java.util.List;

public class CouponsActivity extends AppCompatActivity implements BaseView.BaseActivity, CouponsContract.View {

    public static final String EXTRA_DATA = "DATA-COUPONS";

    private LinearLayout llLoading;
    private RecyclerView rvCoupons;
    private ImageButton ibBack;
    private TextView txtTitle;
    private Button btnSave;
    private SwipeRefreshLayout refreshLayout;
    private EditText editInputCode;

    private CouponAdapter adapter;
    private List<Coupon> coupons;

    private CouponsContract.Presenter presenter;
    private Intent intent;
    private boolean forResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupons);
        init();
        listener(this);
    }

    @Override
    public void init() {
        intent = getIntent();
        forResult = intent.getBooleanExtra("forResult", false);

        llLoading = findViewById(R.id.ll_load_coupons);
        rvCoupons = findViewById(R.id.rv_coupons);
        ibBack = findViewById(R.id.ib_back);
        txtTitle = findViewById(R.id.txt_title_toolbar);
        txtTitle.setText(R.string.page_coupons);
        btnSave = findViewById(R.id.btn_save_coupons);
        refreshLayout = findViewById(R.id.swiperefresh);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        editInputCode = findViewById(R.id.edit_coupons);
        if (forResult) editInputCode.setVisibility(View.VISIBLE);

        coupons = new ArrayList<>();
        adapter = new CouponAdapter(coupons);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvCoupons.setLayoutManager(layoutManager);
        rvCoupons.setAdapter(adapter);

        presenter = new CouponsPresenter(this);
        presenter.requestData(false);
    }

    @Override
    public void listener(Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (forResult) {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(EXTRA_DATA, editInputCode.getText().toString());
                    setResult(Activity.RESULT_OK, resultIntent);
                }
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestData(true);
                refreshLayout.setRefreshing(false);
            }
        });

        if (forResult)
            rvCoupons.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(),
                    rvCoupons, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    editInputCode.setText(coupons.get(position).getCode());
                }

                @Override
                public void onLongItemClick(View view, int position) {

                }
            }));
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
    public void setDataToView(List<Coupon> coupons) {
        this.coupons = coupons;
        adapter.changeData(this.coupons);
    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}