package com.knight.f_interesting.mvp.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding4.widget.RxTextView;
import com.knight.f_interesting.R;
import com.knight.f_interesting.models.Address;
import com.knight.f_interesting.utils.AppShared;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class AddressActivity extends AppCompatActivity implements AddressContract.View{

    public static final String EXTRA_DATA = "DATA-ADDRESS";

    private Button btnSave;
    private EditText editProvince;
    private EditText editDistrict;
    private EditText editWard;
    private EditText editPhone;
    private EditText editDetail;
    private ImageButton ibBack;
    private TextView txtAppbar;

    private LinearLayout llLoading;

    private Observable<Boolean> observable;
    private AddressContract.Presenter presenter;

    private void init(){
        btnSave = findViewById(R.id.btn_save_address);
        editProvince= findViewById(R.id.edit_province_address);
        editDistrict = findViewById(R.id.edit_district);
        editWard = findViewById(R.id.edit_ward);
        editPhone = findViewById(R.id.edit_phone_address);
        llLoading = findViewById(R.id.ll_load_address);
        editDetail = findViewById(R.id.edit_detail_address);
        ibBack = findViewById(R.id.ib_back);
        txtAppbar = findViewById(R.id.txt_title_toolbar);
        txtAppbar.setText(R.string.page_address_user);

        presenter = new AddressPresenter(this, getApplicationContext());
        presenter.requestData();
        Observable<String> phoneObservable
                = RxTextView.textChanges(editPhone).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Throwable {
                return charSequence.toString();
            }
        });

        Observable<String> provinceObservable
                = RxTextView.textChanges(editProvince).skip(1).map(new Function<CharSequence, String>() {
            @Override
            public String apply(CharSequence charSequence) throws Throwable {
                return charSequence.toString();
            }
        });

        observable = Observable.combineLatest(provinceObservable, phoneObservable, new BiFunction<String, String,Boolean>(){
            @Override
            public Boolean apply(String s, String s2) throws Throwable {
                return isValidForm(s, s2);
            }
        });

        observable.subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(@NonNull Boolean aBoolean) {
                updateButton(aBoolean);
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }

    public void updateButton(boolean valid) {
        if (valid){
            btnSave.setEnabled(true);
            btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
        }
        else{
            btnSave.setEnabled(false);
            btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_dispose));
        }

    }

    public boolean isValidForm(String province, String phone) {
        boolean validProvince = province.isEmpty();
        if (validProvince) {
            editProvince.setError("Please enter valid province!");
        }

        boolean validPhone = phone.length() != 10;
        if (validPhone) {
            editPhone.setError("Phone number format incorrect!");
        }
        return !validProvince && !validPhone;
    }

    private void listener(){
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnSave.isEnabled()){
                    String province = editProvince.getText().toString();
                    String phone = editPhone.getText().toString();
                    String district = editDistrict.getText().toString();
                    String ward = editWard.getText().toString();
                    String detail = editDetail.getText().toString();
                    AppShared.setAddress(getApplicationContext(), phone, province, district, ward, detail);
                    final Intent data = new Intent();
                    data.putExtra(EXTRA_DATA, new Address(phone, province, district, ward, detail));
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
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

    @Override
    public void showProgress() {
        llLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
llLoading.setVisibility(View.GONE);
    }

    @Override
    public void setData(Address address) {
        if(address != null){
            editPhone.setText(address.getPhone());
            editProvince.setText(address.getProvince());
            editDistrict.setText(address.getDistrict());
            editWard.setText(address.getWard());
            editDetail.setText(address.getAddress());
            btnSave.setEnabled(true);
            btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_continue));
        }
        else {
            btnSave.setEnabled(false);
            btnSave.setBackground(getResources().getDrawable(R.drawable.bg_button_dispose));
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}