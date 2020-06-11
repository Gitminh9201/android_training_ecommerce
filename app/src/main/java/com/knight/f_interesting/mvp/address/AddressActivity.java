package com.knight.f_interesting.mvp.address;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding4.widget.RxTextView;
import com.knight.f_interesting.R;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;

public class AddressActivity extends AppCompatActivity {

    public static final String EXTRA_DATA_TITLE = "DATA-ADDRESS:TITLE";
    public static final String EXTRA_DATA_ID = "DATA-ADDRESS:ID";

    private Button btnSave;
    private EditText editProvince;
    private EditText editDistrict;
    private EditText editWard;
    private EditText editPhone;

    private Observable<Boolean> observable;

    private void init(){
        btnSave = findViewById(R.id.btn_save_address);
        editProvince= findViewById(R.id.edit_province_address);
        editDistrict = findViewById(R.id.edit_district);
        editWard = findViewById(R.id.edit_ward);
        editPhone = findViewById(R.id.edit_phone_address);

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

    public boolean isValidForm(String name, String password) {
        boolean validName = !name.isEmpty();

        if (!validName) {
            editPhone.setError("Please enter valid Numberphone");
        }

        boolean validPass = !password.isEmpty();
        if (!validPass) {
            editProvince.setError("Please enter valid Province");
        }
        return validName && validPass;
    }

    private void listener(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String province = editProvince.getText().toString();
                String district = editDistrict.getText().toString();
                String ward = editWard.getText().toString();
                final Intent data = new Intent();
                data.putExtra(EXTRA_DATA_TITLE, district + " - " + province);
                data.putExtra(EXTRA_DATA_ID, 1);
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