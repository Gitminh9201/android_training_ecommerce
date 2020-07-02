package com.knight.f_interesting.mvp.person;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.knight.f_interesting.R;
import com.knight.f_interesting.dialogs.ChooseImage;
import com.knight.f_interesting.models.User;
import com.knight.f_interesting.utils.Router;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonFragment extends Fragment implements PersonContract.View {

    private LinearLayout llLoading;
    private LinearLayout llInformation;
    private LinearLayout llHistory;
    private LinearLayout llAddress;
    private LinearLayout llTerms;
    private RelativeLayout rlAvatarProfile;
    private CircleImageView circleAvatar;
    private Button btnAuth;
    private TextView txtUserName;
    private View view;

    private PersonContract.Presenter presenter;

    private void init(View view) {
        this.view = view;
        llAddress = view.findViewById(R.id.ll_address_person);
        llInformation = view.findViewById(R.id.ll_information_person);
        llHistory = view.findViewById(R.id.ll_history_person);
        llTerms = view.findViewById(R.id.ll_terms_person);
        txtUserName = view.findViewById(R.id.txt_name_profile);
        llLoading = view.findViewById(R.id.ll_load_person);
        circleAvatar = view.findViewById(R.id.ci_circleAvatar);
        rlAvatarProfile = view.findViewById(R.id.rl_profile_image);
        btnAuth = view.findViewById(R.id.btn_auth_person);
        presenter = new PersonPresenter(getContext(), this);
        presenter.requestData();
    }

    private void listener(View view) {
        llAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.goToPersonAddress(getActivity());
            }
        });
        llInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.goToPersonInformation(getActivity());
            }
        });
        rlAvatarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new ChooseImage();
                dialogFragment.show(getFragmentManager() != null ? getFragmentManager() : null, "dialog");
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        init(view);
        listener(view);
        return view;
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
    public void changeButtonAuth(boolean login) {
        if (login) {
            btnAuth.setText(getResources().getString(R.string.login_vi));
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Login", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnAuth.setText(getResources().getString(R.string.logout_vi));
            btnAuth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Logout", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void setData(User user) {
        if (user != null && user.getName() != null) {
            changeButtonAuth(false);
            txtUserName.setText(user.getName());
            Glide.with(getContext()).load(user.getAvatar()).into(circleAvatar);
        } else {
            changeButtonAuth(true);
        }
    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Log.e("Err", throwable.getMessage());
        Snackbar.make(this.view.findViewById(R.id.fragment_person), getString(R.string.error_data),
                Snackbar.LENGTH_LONG).show();
    }
}