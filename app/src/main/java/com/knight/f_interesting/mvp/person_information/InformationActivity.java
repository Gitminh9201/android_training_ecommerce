package com.knight.f_interesting.mvp.person_information;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.knight.f_interesting.R;
import com.knight.f_interesting.api.AppClient;
import com.knight.f_interesting.base.BaseView;
import com.knight.f_interesting.dialogs.ChooseImage;
import com.knight.f_interesting.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class InformationActivity extends AppCompatActivity implements InformationContract.View, BaseView.BaseActivity {

    private LinearLayout llLoading;
    private RelativeLayout rlChangeAvatar;
    private EditText editName;
    private EditText editPhone;
    private EditText editEmail;
    private EditText editBirthday;
    private CircleImageView circleImageAvatar;
    private InformationContract.Presenter presenter;
    private Button btnSave;
    private ImageButton ibBack;
    private TextView txtAppbar;

    private Calendar myCalendar;
    private DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        init();
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
    public void setData(User user) {
        if (user != null && user.getName() != null) {
            editName.setText(user.getName());
            editEmail.setText(user.getEmail());
            if (user.getNumberPhone() != null) editPhone.setText(user.getNumberPhone());
            if (user.getBirthday() != null) {
                String dtStart = user.getBirthday();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date date = format.parse(dtStart);
                    System.out.println(date);
                    myCalendar.set(date.getYear(), date.getMonth(), date.getDate());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String dateTime = dateFormat.format(date);
                    editBirthday.setText(dateTime);

//                    picker = new DatePickerDialog(InformationActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                            myCalendar.set(year, month, dayOfMonth);
//
//                            Date date = new Date(year, month, dayOfMonth);
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd / MM / yyyy");
//                            String dateTime = dateFormat.format(date);
//                            editBirthday.setText(dateTime);
//                        }
//                    }, myCalendar
//                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                            myCalendar.get(Calendar.DAY_OF_MONTH));
//                    picker.getDatePicker().setMaxDate(new Date().getTime());
//                    picker.getDatePicker().setMinDate(new Date(1920).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            String url;
            if (user.getAvatar().startsWith("http"))
                url = user.getAvatar();
            else
                url = AppClient.url() + user.getAvatar();
            Glide.with(getApplicationContext()).load(url).into(circleImageAvatar);
        }
    }

    @Override
    public void init() {
        myCalendar = Calendar.getInstance();
        ibBack = findViewById(R.id.ib_back);
        txtAppbar = findViewById(R.id.txt_title_toolbar);
        txtAppbar.setText(R.string.page_information_user);
        btnSave = findViewById(R.id.btn_save);
        circleImageAvatar = findViewById(R.id.ci_circleAvatar);
        rlChangeAvatar = findViewById(R.id.profile_image);
        editEmail = findViewById(R.id.edit_info_email);
        editName = findViewById(R.id.edit_info_name);
        editBirthday = findViewById(R.id.edit_info_birthday);
        editPhone = findViewById(R.id.edit_info_phone);
        llLoading = findViewById(R.id.ll_load_info);
        presenter = new InformationPresenter(this, this);
        presenter.requestData();
    }

    @Override
    public void listener(final Activity activity) {
        rlChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new ChooseImage(activity, presenter);
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                presenter.updateUser(new User(editName.getText().toString(),
//                        editEmail.getText().toString(),
//                        editBirthday.getText().toString(), editPhone.getText().toString()));

                presenter.updateUser(new User(editName.getText().toString(),
                        editEmail.getText().toString(), editPhone.getText().toString()));
            }
        });
        editBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(picker != null) picker.show();
                Toast.makeText(getApplicationContext(), "Coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
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