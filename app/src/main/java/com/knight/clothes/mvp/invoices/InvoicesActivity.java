package com.knight.clothes.mvp.invoices;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.clothes.R;
import com.knight.clothes.adapters.InvoicesAdapter;
import com.knight.clothes.adapters.loadmores.LoadMoreInvoices;
import com.knight.clothes.base.BaseView;
import com.knight.clothes.models.Invoice;
import com.knight.clothes.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class InvoicesActivity extends AppCompatActivity implements BaseView.BaseActivity,
        InvoicesContract.View {

    private LinearLayout llLoading;
    private RecyclerView rvOrders;
    private ImageButton ibBack;
    private TextView txtTitle;

    private List<Invoice> invoices;
//    private OrderHistoryAdapter adapter;
    private InvoicesAdapter adapter;

    private InvoicesContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        init();
        listener(this);
    }

    @Override
    public void init() {
        txtTitle = findViewById(R.id.txt_title_toolbar);
        txtTitle.setText(R.string.page_history_order);
        ibBack = findViewById(R.id.ib_back);
        llLoading = findViewById(R.id.ll_load_history);
        rvOrders = findViewById(R.id.rv_history_order);

        invoices = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvOrders.setLayoutManager(linearLayoutManager);
        adapter = new InvoicesAdapter(this, invoices, rvOrders, new LoadMoreInvoices() {
            @Override
            public void onLoadMore(int offset, int limit) {
                invoices.add(null);
                adapter.notifyItemInserted(invoices.size()-1);
                presenter.requestData(offset, limit);
            }
        });
        rvOrders.setAdapter(adapter);

        presenter = new InvoicesPresenter(this, getApplicationContext());
        presenter.requestData();
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

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setMoreData(final List<Invoice> invoices) {
        if(!invoices.isEmpty()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InvoicesActivity.this.invoices.remove(InvoicesActivity.this.invoices.size()-1);
                    adapter.notifyItemRemoved(InvoicesActivity.this.invoices.size());
                    adapter.setLoaded();
                    InvoicesActivity.this.invoices.addAll(invoices);
                    adapter.refresh(InvoicesActivity.this.invoices);
                }
            }, 1000);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InvoicesActivity.this.invoices.remove(InvoicesActivity.this.invoices.size()-1);
                    adapter.notifyItemRemoved(InvoicesActivity.this.invoices.size());
                    AppUtils.showToast(R.string.loadCompleted, getApplicationContext());
                }
            }, 1500);
        }
    }

    @Override
    public void setDataOriginal(List<Invoice> invoices) {
        if(invoices != null && invoices.size() > 0){
            adapter.refresh(invoices);
            this.invoices = invoices;
        }
        else {
            AppUtils.showToast(getResources().getString(R.string.empty_data), getApplicationContext());
        }
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