package com.knight.f_interesting.mvp.person_information;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationActivity extends AppCompatActivity implements InformationContract.View {

    private LinearLayout llLoading;
    private RelativeLayout rlChangeAvatar;
    private EditText editName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editBirthday;
    private CircleImageView circleImageAvatar;
    private InformationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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
    public void setData(User user) {
        if(user != null && user.getName() != null){
            editName.setText(user.getName());
            editEmail.setText(user.getEmail());

            String url;
            if(user.getAvatar().startsWith("http"))
                url = user.getAvatar();
            else
                url = Client.url() + user.getAvatar();
            Glide.with(getApplicationContext()).load(url).into(circleImageAvatar);
        }
    }

    @Override
    public void init() {
        circleImageAvatar = findViewById(R.id.ci_circleAvatar);
        editEmail = findViewById(R.id.edit_info_email);
        editName = findViewById(R.id.edit_info_name);
        editBirthday = findViewById(R.id.edit_info_birthday);
        editPhone = findViewById(R.id.edit_info_phone);
        llLoading = findViewById(R.id.ll_load_info);
        presenter = new InformationPresenter(getApplicationContext(), this);
        presenter.requestData();
    }

    @Override
    public void listener() {

    }

    @Override
    public void onResponseFailure(Throwable throwable) {

    }
}