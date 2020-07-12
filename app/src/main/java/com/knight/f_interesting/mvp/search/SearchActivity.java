package com.knight.f_interesting.mvp.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.knight.f_interesting.R;
import com.knight.f_interesting.adapters.SearchAdapter;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.models.ProductSuggest;
import com.knight.f_interesting.utils.Router;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements BaseView.BaseActivity, SearchContract.View {

    private SearchAdapter adapter;
    private List<ProductSuggest> suggests;
    private RecyclerView rvSuggest;
    private SearchContract.Presenter presenter;
    private ImageButton ibSearch;
    private EditText editSearch;
    private ImageButton ibBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        listener(this);
    }

    @Override
    public void init() {
        ibBack = findViewById(R.id.ib_back);
        ibSearch = findViewById(R.id.ib_search_toolbar);
        editSearch = findViewById(R.id.edit_search);
        editSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        rvSuggest = findViewById(R.id.rv_search);

        presenter = new SearchPresenter(this, getApplicationContext());
        suggests = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rvSuggest.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(suggests, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.resultProducts(editSearch.getText().toString());
            }
        }, activity());
        rvSuggest.setAdapter(adapter);
        presenter.requestSuggest("");
    }

    @Override
    public void listener(final Activity activity) {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e("before", s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("onChange", s.toString());
                presenter.requestSuggest(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("after", s.toString());
            }
        });
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.clearFocus();
                Router.navigator(Router.RESULT_PRODUCTS, activity, new String[]{editSearch.getText().toString()});
            }
        });
        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.resultProducts(editSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public Activity activity() {
        return this;
    }

    @Override
    public void setDataToView(List<ProductSuggest> suggests) {
        if(suggests != null){
            this.suggests = suggests;
            adapter.refresh(suggests);
        }
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